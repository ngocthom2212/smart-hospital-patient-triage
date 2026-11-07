/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Record;

import Model.Patient;
import Model.PatientNode;
import java.util.HashMap;

/**
 *
 * @author buidi
 */
public class PatientRecord {
    private final HashMap<String, PatientNode> lookupTable = new HashMap<>();
    private PatientNode head = null;
    
    private boolean isEmpty() {
        return this.head == null;
    }
    
    public void addPatient(Patient patient) {
        PatientNode newPatientNode = new PatientNode(patient);
        
        if(this.isEmpty()) {
            this.head = newPatientNode;
        } else {
            newPatientNode.setNext(this.head);
            this.head.setPrev(newPatientNode);
            this.head = newPatientNode;
        }
        
        this.lookupTable.put(patient.getId(), newPatientNode);
    }
    
    public void updatePatient(String id, Patient patient) {
        PatientNode patientNode = this.searchPatientbyId(id);
        
        if(patientNode == null) return;
        
        patientNode.setPatient(patient);
    }
    
    public PatientNode searchPatientbyId(String id) {
        return this.lookupTable.get(id);
    }
    
    public PatientNode searchPatientbyName(String name) {
        PatientNode iterater = this.head;
        
        while(iterater != null && !iterater.getPatient().getName().equals(name)) {
            iterater = iterater.getNext();
        }
        
        return iterater;
    }
    
}
