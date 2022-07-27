package hu.icellmobilsoft.atr.sample.action;

import java.util.ArrayList;

import hu.icellmobilsoft.atr.sample.parseXml;
import hu.icellmobilsoft.atr.sample.model.Department;
import hu.icellmobilsoft.atr.sample.model.Patient;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;

public class SamplePatientAction {

    private DepartmentRepository depRep;
    private PatientRepository patRep;
    private InstituteRepository instRep;

    public void loadFromXml(String xml) {
        parseXml oParseXml = new parseXml();
        oParseXml.parse(xml);

        depRep = oParseXml.getoDepRepo();
        patRep = oParseXml.getoPatRepo();
        instRep = oParseXml.getoInstRepo();
    }

    public ArrayList<Patient> getAllUsersData() {
        return patRep.getAllPatient();
    }

    public Patient getPatientData(String id) {
        ArrayList<Patient> patientData = patRep.getAllPatient();
        for (int i = 0; i < patientData.size(); i++) {
            if (patientData.get(i).getId().equals(id)) {
                return patientData.get(i);
            }
        }
        return null;
    }
}
