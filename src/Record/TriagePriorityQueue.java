/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Record;

import Model.PatientStatusNode;
import Model.PriorityEnum;
import Model.TriageNode;

/**
 *
 * @author buidi
 */
public class TriagePriorityQueue {
    private long insertionCounter = 0;
    private TriageNode head = null;
    
    private boolean isEmpty() {
        return this.head == null;
    }
        
    private boolean hasHigherPriority(TriageNode a, TriageNode b) {
        if (a.getPriority().compareTo(b.getPriority()) > 0) {
            return true;
        }

        if (a.getPriority().compareTo(b.getPriority()) < 0) {
            return false;
        }

        return a.getArrivalTime() < b.getArrivalTime();
    }
    
    public TriageNode peek() {
        if(this.isEmpty()) return null;
        return new TriageNode(this.head.getPatientStatusNode(), 
                              this.head.getPriority(), 
                              this.head.getArrivalTime()
        );
    }
    
    private TriageNode enqueueIterater(TriageNode newTriageNode) {
        TriageNode iterater = this.head;
        
        while (iterater.getNext() != null &&
                    !hasHigherPriority(newTriageNode, iterater.getNext())) {
                iterater = iterater.getNext();
     
       }
        
        return iterater;
    }
    
    public void enqueue(PatientStatusNode patient, PriorityEnum priority) {
        this.insertionCounter++;
        TriageNode newTriageNode = new TriageNode(patient, priority, this.insertionCounter);
        
        if(this.isEmpty() || hasHigherPriority(newTriageNode, this.head)) {
            newTriageNode.setNext(this.head);
            this.head = newTriageNode;
            
            return;
        }
        
        TriageNode iterater = this.enqueueIterater(newTriageNode);
        newTriageNode.setNext(iterater.getNext());
        iterater.setNext(newTriageNode);
    }
    
    public TriageNode dequeue() {
        if (isEmpty()) return null;

        TriageNode result = this.head;
        this.head = head.getNext();

        return result;
    }
    
    public TriageNode searchTriage(PatientStatusNode patient) {
        TriageNode iterater = this.head;
        
        while (iterater != null) {
            if(iterater.getPatientStatusNode().equals(patient))
                break;
            iterater = iterater.getNext();
        }
        
        return iterater;
    }
    
    private void removeTriageNode(TriageNode node) {
        if (isEmpty()) return;
        
        TriageNode iterater = this.head;
        while (iterater != null) {
            if(iterater.getNext().equals(node))
                break;
            iterater = iterater.getNext();
        }

        iterater.setNext(node.getNext());
    }
    
    public void updatePriority(PatientStatusNode patient, PriorityEnum priority) {
        TriageNode oldNode = this.searchTriage(patient);
        this.removeTriageNode(oldNode);
        this.enqueue(patient, priority);
    }
}
