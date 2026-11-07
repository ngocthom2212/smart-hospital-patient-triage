import java.util.*;

public class TriageBenchmark {
    enum PriorityEnum { LOW, MEDIUM, HIGH }

    static class PatientStatusNode {
        final int id;
        long effectiveArrival;
        PatientStatusNode(int id) { this.id = id; }
    }

    static class TriageNode {
        PatientStatusNode patient;
        PriorityEnum priority;
        TriageNode next;
        long arrivalTime;
        TriageNode(PatientStatusNode patient, PriorityEnum priority, long arrivalTime) {
            this.patient = patient;
            this.priority = priority;
            this.arrivalTime = arrivalTime;
        }
    }

    // Algorithm A: same design as the submitted TriagePriorityQueue:
    // sorted linked-list priority queue + arrival-time tie-breaking.
    static class SortedLinkedTriageQueue {
        private long insertionCounter = 0;
        private TriageNode head = null;
        private boolean isEmpty() { return head == null; }

        private boolean hasHigherPriority(TriageNode a, TriageNode b) {
            if (a.priority.compareTo(b.priority) > 0) return true;
            if (a.priority.compareTo(b.priority) < 0) return false;
            return a.arrivalTime < b.arrivalTime;
        }

        public void enqueue(PatientStatusNode patient, PriorityEnum priority) {
            insertionCounter++;
            patient.effectiveArrival = insertionCounter;
            TriageNode newNode = new TriageNode(patient, priority, insertionCounter);
            if (isEmpty() || hasHigherPriority(newNode, head)) {
                newNode.next = head;
                head = newNode;
                return;
            }
            TriageNode iterator = head;
            while (iterator.next != null && !hasHigherPriority(newNode, iterator.next)) {
                iterator = iterator.next;
            }
            newNode.next = iterator.next;
            iterator.next = newNode;
        }

        public TriageNode dequeue() {
            if (isEmpty()) return null;
            TriageNode result = head;
            head = head.next;
            return result;
        }

        public TriageNode searchTriage(PatientStatusNode patient) {
            TriageNode iterator = head;
            while (iterator != null) {
                if (iterator.patient == patient) break;
                iterator = iterator.next;
            }
            return iterator;
        }

        private void removeTriageNode(TriageNode node) {
            if (isEmpty() || node == null) return;
            if (head == node) {
                head = head.next;
                return;
            }
            TriageNode iterator = head;
            while (iterator.next != null) {
                if (iterator.next == node) {
                    iterator.next = node.next;
                    return;
                }
                iterator = iterator.next;
            }
        }

        public void updatePriority(PatientStatusNode patient, PriorityEnum priority) {
            TriageNode oldNode = searchTriage(patient);
            removeTriageNode(oldNode);
            enqueue(patient, priority);
        }
    }

    // Algorithm B: Tree<Priority, Queue<Patient>> represented by TreeMap<PriorityEnum, LinkedList<PatientStatusNode>>.
    static class TreePriorityQueue {
        private long insertionCounter = 0;
        private final TreeMap<PriorityEnum, LinkedList<PatientStatusNode>> tree = new TreeMap<PriorityEnum, LinkedList<PatientStatusNode>>();

        TreePriorityQueue() {
            for (PriorityEnum p : PriorityEnum.values()) tree.put(p, new LinkedList<PatientStatusNode>());
        }

        public void enqueue(PatientStatusNode patient, PriorityEnum priority) {
            insertionCounter++;
            patient.effectiveArrival = insertionCounter;
            tree.get(priority).addLast(patient);
        }

        public PatientStatusNode dequeue() {
            for (PriorityEnum p : tree.descendingKeySet()) {
                LinkedList<PatientStatusNode> q = tree.get(p);
                if (!q.isEmpty()) return q.removeFirst();
            }
            return null;
        }

        public void updatePriority(PatientStatusNode patient, PriorityEnum newPriority) {
            // No direct node index is used, matching the Tree<Priority, Queue<Patient>> concept.
            for (PriorityEnum p : tree.keySet()) {
                Iterator<PatientStatusNode> it = tree.get(p).iterator();
                while (it.hasNext()) {
                    if (it.next() == patient) {
                        it.remove();
                        enqueue(patient, newPriority);
                        return;
                    }
                }
            }
        }
    }

    static class WorkloadData {
        PatientStatusNode[] patients;
        PriorityEnum[] initialPriorities;
        int[] updateIndexes;
        PriorityEnum[] updatePriorities;
    }

    static WorkloadData generateData(int n, int trial) {
        Random random = new Random(2026L + trial);
        PriorityEnum[] values = PriorityEnum.values();
        WorkloadData data = new WorkloadData();
        data.patients = new PatientStatusNode[n];
        data.initialPriorities = new PriorityEnum[n];
        for (int i = 0; i < n; i++) {
            data.patients[i] = new PatientStatusNode(i);
            data.initialPriorities[i] = values[random.nextInt(values.length)];
        }
        int updateCount = Math.max(1, n / 10); // 10% re-triage workload
        data.updateIndexes = new int[updateCount];
        data.updatePriorities = new PriorityEnum[updateCount];
        for (int i = 0; i < updateCount; i++) {
            int index = (i * 9973 + trial * 17) % n;
            data.updateIndexes[i] = index;
            PriorityEnum oldP = data.initialPriorities[index];
            data.updatePriorities[i] = values[(oldP.ordinal() + 1) % values.length];
        }
        return data;
    }

    static long runAlgorithmA(WorkloadData data) {
        SortedLinkedTriageQueue q = new SortedLinkedTriageQueue();
        long start = System.nanoTime();
        for (int i = 0; i < data.patients.length; i++) q.enqueue(data.patients[i], data.initialPriorities[i]);
        for (int i = 0; i < data.updateIndexes.length; i++) q.updatePriority(data.patients[data.updateIndexes[i]], data.updatePriorities[i]);
        while (q.dequeue() != null) { /* consume */ }
        return System.nanoTime() - start;
    }

    static long runAlgorithmB(WorkloadData data) {
        TreePriorityQueue q = new TreePriorityQueue();
        long start = System.nanoTime();
        for (int i = 0; i < data.patients.length; i++) q.enqueue(data.patients[i], data.initialPriorities[i]);
        for (int i = 0; i < data.updateIndexes.length; i++) q.updatePriority(data.patients[data.updateIndexes[i]], data.updatePriorities[i]);
        while (q.dequeue() != null) { /* consume */ }
        return System.nanoTime() - start;
    }

    static String fmt(double ms) {
        return String.format(Locale.US, "%.3f", ms);
    }

    public static void main(String[] args) {
        int[] sizes = {100, 500, 1000, 5000, 10000};
        int trials = 5;

        // Warm-up to reduce first-run JIT effects.
        for (int i = 0; i < 3; i++) {
            WorkloadData warm = generateData(5000, 100 + i);
            runAlgorithmA(warm);
            warm = generateData(5000, 100 + i);
            runAlgorithmB(warm);
        }

        System.out.println("Input Size (n),Algorithm A Time (ms),Algorithm B Time (ms)");
        for (int n : sizes) {
            double totalA = 0.0;
            double totalB = 0.0;
            for (int t = 1; t <= trials; t++) {
                WorkloadData dataA = generateData(n, t);
                totalA += runAlgorithmA(dataA) / 1_000_000.0;
                WorkloadData dataB = generateData(n, t);
                totalB += runAlgorithmB(dataB) / 1_000_000.0;
            }
            System.out.println(n + "," + fmt(totalA / trials) + "," + fmt(totalB / trials));
        }
    }
}
