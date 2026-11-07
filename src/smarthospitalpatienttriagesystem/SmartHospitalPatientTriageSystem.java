/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package smarthospitalpatienttriagesystem;

import Model.Patient;
import Record.PatientRecord;
import java.util.Date;

/**
 *
 * @author buidi
 */
public class SmartHospitalPatientTriageSystem {

    public static void main(String[] args) {
        PatientRecord record = new PatientRecord();
        
        record.addPatient(new Patient("001","Man",false,new Date(1,1,2000)));
        record.updatePatient("001", new Patient("001","Khoa",false,new Date(1,1,2000)));
        return;
    }
    
}
