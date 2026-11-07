/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Record;

import Model.PatientNode;
import Model.PatientStatusNode;
import Model.StatusEnum;

/**
 *
 * @author buidi
 */
public class PatientStatusList {
    private PatientStatusNode head = null;
    
    private boolean isEmpty() {
        return this.head == null;
    }
    
    public void addPatientStatus(PatientNode patient, StatusEnum status) {
        PatientStatusNode newPatientStatusNode = new PatientStatusNode(patient, status);
        
        if(this.isEmpty()) {
            this.head = newPatientStatusNode;
            return;
        } 
        
        newPatientStatusNode.setNext(this.head);
        this.head.setPrev(newPatientStatusNode);
        this.head = newPatientStatusNode;
    }
    
    public PatientStatusNode searchPatientStatus(PatientNode patient) {
        PatientStatusNode iterater = this.head;
        
        while(iterater != null && !iterater.getPatientNode().equals(patient)) {
            iterater = iterater.getNext();
        }
        
        return iterater;
    }
    
    public void removePatientStatus(PatientStatusNode patient) {
        patient.getPrev().setNext(patient.getNext());
        patient.getNext().setPrev(patient.getPrev());
    }
    
    public StatusEnum getPatientStatus(PatientStatusNode patient) {
        return patient.getPatientStatus();
    }
}
