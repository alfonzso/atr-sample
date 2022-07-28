package hu.icellmobilsoft.atr.sample.repository;

import java.util.ArrayList;

import hu.icellmobilsoft.atr.sample.model.Patient;

public class PatientRepository {

    ArrayList<Patient> patients = new ArrayList<Patient>();

    public void savePatient(Patient patient) {
        Patient existPatient = findPatient(patient.getId());

        if (existPatient == null) {
            patients.add(patient);
        } else {
            Integer idx = patients.indexOf(existPatient);
            patients.set(idx, new Patient(
                    patient.getId(),
                    patient.getName(),
                    patient.getEmail(),
                    patient.getUsername(),
                    patient.getInstitute(),
                    patient.getDepartment()));
        }
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

    @Override
    public String toString() {
        for (int i = 0; i < patients.size(); i++) {
            System.out.println(patients.get(i).getId());
            System.out.println(patients.get(i).getName());
            System.out.println(patients.get(i).getUsername());
            System.out.println(patients.get(i).getEmail());
            System.out.println(
                patients.get(i).getDepartment().getName().toString()
            );
            System.out.println(
                patients.get(i).getInstitute().getName().toString()
            );

        }
        return super.toString();
    }

}
