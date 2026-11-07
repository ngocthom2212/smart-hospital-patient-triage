/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author buidi
 */
public class PatientStatusNode {
    private final PatientNode patient;
    private StatusEnum patientStatus;
    private PatientStatusNode prev = null;
    private PatientStatusNode next = null;

    public PatientStatusNode(PatientNode patient, StatusEnum patientStatus) {
        this.patient = patient;
        this.patientStatus = patientStatus;
    }

    public void setPrev(PatientStatusNode prev) {
        this.prev = prev;
    }

    public void setNext(PatientStatusNode next) {
        this.next = next;
    }

    public void setPatientStatus(StatusEnum status) {
        this.patientStatus = status;
    }

    public PatientNode getPatientNode() {
        return patient;
    }

    public StatusEnum getPatientStatus() {
        return patientStatus;
    }

    public PatientStatusNode getPrev() {
        return prev;
    }

    public PatientStatusNode getNext() {
        return next;
    }
    
    
}
