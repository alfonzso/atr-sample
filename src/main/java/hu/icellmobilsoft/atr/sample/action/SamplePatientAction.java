package hu.icellmobilsoft.atr.sample.action;

import hu.icellmobilsoft.atr.sample.model.Patient;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;
import hu.icellmobilsoft.atr.sample.rest.LoadDataImpl;
import hu.icellmobilsoft.atr.sample.rest.RequestDataImpl;
import hu.icellmobilsoft.atr.sample.rest.ParseJson;
import hu.icellmobilsoft.atr.sample.rest.ParseXml;
import hu.icellmobilsoft.atr.sample.util.SimpleTicketConstans;
import org.apache.commons.lang3.StringUtils;

/**
 * @author juhaszkata
 * @version 1
 */
public class SamplePatientAction extends RequestDataImpl {
     if(StringUtils.isBlank(xmlFileName)){
        throw new IllegalArgumentException(SimpleTicketConstans.PARAMETER_CANNOT_NULL_MSG);
    }

    private DepartmentRepository depRep;
    private PatientRepository patRep;
    private InstituteRepository instRep;

    private LoadDataImpl loadData;

    /**
     * Gets dep rep.
     *
     * @return the dep rep
     */
    public DepartmentRepository getDepRep() {
        return depRep;
    }

    /**
     * Gets pat rep.
     *
     * @return the pat rep
     */
    public PatientRepository getPatRep() {
        return patRep;
    }

    /**
     * Gets inst rep.
     *
     * @return the inst rep
     */
    public InstituteRepository getInstRep() {
        return instRep;
    }

    /**
     * Instantiates a new Sample patient action.
     */
    public SamplePatientAction() {
        loadData = new LoadDataImpl();
    }

    /**
     * Load from xml.
     *
     * @param xml the xml
     */
    public void loadFromXml(String xml) {
        ParseXml oParseXml = loadData.loadFromXml(xml);

        depRep = oParseXml.getDepRepo();
        patRep = oParseXml.getPatRepo();
        instRep = oParseXml.getInstRepo();
    }

    /**
     * Load from json.
     *
     * @param json the json
     */
    public void loadFromJson(String json) {
        ParseJson oParseJson = loadData.loadFromJson(json);

        depRep = oParseJson.getDepRepo();
        patRep = oParseJson.getPatRepo();
        instRep = oParseJson.getInstRepo();
    }

    /**
     * Query patient data patient.
     *
     * @param userName   the user name
     * @param department the department
     * @return the patient
     */
    public Patient queryPatientData(String userName, String department) {
        return patRep.getAllPatient().stream().filter(x -> {
            return x.getUsername().equals(userName) && x.getDepartment().getId().equals(department);
        }).findFirst().orElse(null);
    }

    /**
     * Delete patient.
     *
     * @param id the id
     */
    public void deletePatient(String id) {
        PatientRepository tempPatRepo = new PatientRepository();
        patRep.getAllPatient().stream()
                .filter(x -> x.getId().equals(id))
                .forEach(y -> {
                    tempPatRepo.savePatient(y);
                });
        patRep.getAllPatient().removeAll(tempPatRepo.getAllPatient());

    }
}