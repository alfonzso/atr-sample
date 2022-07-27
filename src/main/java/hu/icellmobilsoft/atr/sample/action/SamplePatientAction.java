package hu.icellmobilsoft.atr.sample.action;

import hu.icellmobilsoft.atr.sample.parseXml;
import hu.icellmobilsoft.atr.sample.model.Department;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;

public class SamplePatientAction {

    private DepartmentRepository depRep;
    private PatientRepository patRep;
    private InstituteRepository instRep;
    // patient
    // institute

    public void loadFromXml(String xml) {
        parseXml oParseXml = new parseXml();
        oParseXml.parse(xml);

        depRep = oParseXml.getoDepRepo();
        patRep = oParseXml.getoPatRepo();
        instRep = oParseXml.getoInstRepo();


    }

    // getPatientData(String id)
    // getAllUsersData()

}
