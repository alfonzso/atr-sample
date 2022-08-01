package hu.icellmobilsoft.atr.sample.rest;

import java.util.ArrayList;

import hu.icellmobilsoft.atr.sample.model.Patient;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;

public class RequestDataImpl implements IRequestData {

    private PatientRepository patRep;

    @Override
    public Patient getPatientData(String id) {

        ArrayList<Patient> patientData = patRep.getAllPatient();
        for (int i = 0; i < patientData.size(); i++) {
            if (patientData.get(i).getId().equals(id)) {
                return patientData.get(i);
            }
        }
        return null;
    }

    @Override
    public ArrayList<Patient> getAllUsersData() {
        return patRep.getAllPatient();
    }

}
