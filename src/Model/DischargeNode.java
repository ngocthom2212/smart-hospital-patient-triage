/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author buidi
 */
public class DischargeNode {
    private final PatientStatusNode patient;
    private DischargeNode next = null;

    public DischargeNode(PatientStatusNode patient) {
        this.patient = patient;
    }

    public void setNext(DischargeNode next) {
        this.next = next;
    }

    public PatientStatusNode getPatientStatusNode() {
        return patient;
    }

    public DischargeNode getNext() {
        return next;
    }
}
