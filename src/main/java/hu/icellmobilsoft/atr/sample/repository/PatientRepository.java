package hu.icellmobilsoft.atr.sample.repository;

import java.util.ArrayList;

import hu.icellmobilsoft.atr.sample.model.Patient;

public class PatientRepository {

    ArrayList<Patient> patients = new ArrayList<Patient>();

    public void savePatient(Patient patient) {
        patients.add(patient);
    }

    public ArrayList<Patient> getAllPatient() {
        return patients;
    }

    public Patient findPatient(String id) {
        for (int index = 0; index < patients.size(); index++) {
            if (patients.get(index).getId().equals(id)) {
                return patients.get(index);
            }
        }
        return null;
    }

}
