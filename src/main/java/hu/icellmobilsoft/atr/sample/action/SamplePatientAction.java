package hu.icellmobilsoft.atr.sample.action;

import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;
import hu.icellmobilsoft.atr.sample.rest.LoadDataImpl;
import hu.icellmobilsoft.atr.sample.rest.RequestDataImpl;
import hu.icellmobilsoft.atr.sample.rest.parseJson;
import hu.icellmobilsoft.atr.sample.rest.parseXml;

public class SamplePatientAction extends RequestDataImpl {

    private DepartmentRepository depRep;
    private PatientRepository patRep;
    private InstituteRepository instRep;

    private LoadDataImpl loadData;

    public void loadFromXml(String xml) {
        parseXml oParseXml = loadData.loadFromXml(xml);

        depRep = oParseXml.getDepRepo();
        patRep = oParseXml.getPatRepo();
        instRep = oParseXml.getInstRepo();
    }

    public void loadFromJson(String json) {
        parseJson oParseJson = loadData.loadFromJson(json);

        depRep = oParseJson.getDepRepo();
        patRep = oParseJson.getPatRepo();
        instRep = oParseJson.getInstRepo();
    }
}