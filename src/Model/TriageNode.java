/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author buidi
 */
public class TriageNode {
    private PatientStatusNode patient;
    private PriorityEnum priority;
    private TriageNode next;
    private long arrivalTime;

    public TriageNode(PatientStatusNode patient, PriorityEnum priority, long arrivalTime) {
        this.patient = patient;
        this.priority = priority;
        this.next = next;
        this.arrivalTime = arrivalTime;
    }

    public void setNext(TriageNode next) {
        this.next = next;
    }

    public PatientStatusNode getPatientStatusNode() {
        return this.patient;
    }

    public PriorityEnum getPriority() {
        return this.priority;
    }

    public TriageNode getNext() {
        return this.next;
    }

    public long getArrivalTime() {
        return this.arrivalTime;
    }
}
