package hu.icellmobilsoft.atr.sample.rest;

import java.util.ArrayList;

import hu.icellmobilsoft.atr.sample.model.Patient;

public interface IRequestData {
    public Patient getPatientData(String id);

    public ArrayList<Patient> getAllUsersData();

}

