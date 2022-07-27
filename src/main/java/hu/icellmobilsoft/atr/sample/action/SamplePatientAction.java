package hu.icellmobilsoft.atr.sample.action;

import hu.icellmobilsoft.atr.sample.parseXml;
import hu.icellmobilsoft.atr.sample.model.Department;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;

public class SamplePatientAction {

    private DepartmentRepository depRep;
    // patient
    // institute

    public void loadFromXml(String xml) {
        parseXml pocos = new parseXml();
        pocos.parse(xml);

        depRep = pocos.getoDepRepo();
        


    }

    // getPatientData(String id)
    // getAllUsersData()

}
