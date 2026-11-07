/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author buidi
 */
public class PatientNode {
    private Patient patient;
    private PatientNode prev = null;
    private PatientNode next = null;

    public PatientNode(Patient patient) {
        this.patient = patient;
    }

    public void setPrev(PatientNode prev) {
        this.prev = prev;
    }

    public void setNext(PatientNode next) {
        this.next = next;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public PatientNode getPrev() {
        return prev;
    }

    public PatientNode getNext() {
        return next;
    }
}
