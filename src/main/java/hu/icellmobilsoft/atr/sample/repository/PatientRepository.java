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
        return patients.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        patients.stream().forEach(x -> {
            System.out.println(x.getId());
            System.out.println(x.getName());
            System.out.println(x.getEmail());
            System.out.println(x.getUsername());
            System.out.println(x.getDepartment().getName());
            System.out.println(x.getInstitute().getName());
        });

        return super.toString();
    }

}
