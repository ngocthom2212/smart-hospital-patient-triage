/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Record;

import Model.DischargeNode;
import Model.PatientStatusNode;

/**
 *
 * @author buidi
 */
public class DischargeQueue {
    private DischargeNode head = null;
    private DischargeNode tail = null;
    
    private boolean isEmpty() {
        return this.head == null;
    }
    
    public void enqueue(PatientStatusNode patient) {
        DischargeNode newDischargeNode = new DischargeNode(patient); 
        
        if(this.isEmpty()) {
            this.head = this.tail = newDischargeNode;
            return;
        }
        
        this.tail.setNext(newDischargeNode);
        this.tail = newDischargeNode;
    }
    
    public DischargeNode dequeue() {
        DischargeNode dequeueNode = this.head;
        this.head = this.head.getNext();
        
        return dequeueNode;
    }
}
