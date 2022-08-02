package hu.icellmobilsoft.atr.sample.rest;

import java.util.ArrayList;
import java.util.stream.Stream;

import hu.icellmobilsoft.atr.sample.model.Patient;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;

public class RequestDataImpl implements IRequestData {

    private PatientRepository patRep;

    @Override
    public Patient getPatientData(String id) {
        Stream<Patient> patientStream = patRep.getAllPatient().stream().filter(predicate -> predicate.getId().equals(id));
        return patientStream.findFirst().orElse(null);
    }

    @Override
    public ArrayList<Patient> getAllUsersData() {
        return patRep.getAllPatient();
    }

}
