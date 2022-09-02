package hu.icellmobilsoft.atr.sample.action;

import hu.icellmobilsoft.HealthCareContainer;
import hu.icellmobilsoft.atr.sample.model.Patient;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;
import hu.icellmobilsoft.atr.sample.rest.LoadDataImpl;
import hu.icellmobilsoft.atr.sample.rest.RequestDataImpl;

/**
 * @author juhaszkata
 * @version 1
 */
public class SamplePatientAction extends RequestDataImpl {
  HealthCareContainer oHCC;

  public HealthCareContainer getoHCC() {
    return oHCC;
  }

  private LoadDataImpl loadData;

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
    oHCC = loadData.loadFromXml(xml);
  }

  /**
   * Load from json.
   *
   * @param json the json
   */
  public void loadFromJson(String json) {
    oHCC = loadData.loadFromJson(json);
  }

  /**
   * Query patient data patient.
   *
   * @param userName   the user name
   * @param department the department
   * @return the patient
   */
  public Patient queryPatientData(String userName, String department) {
    return oHCC.getPatRepo().getAllPatient().stream().filter(x -> {
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
    oHCC.getPatRepo().getAllPatient().stream()
        .filter(x -> x.getId().equals(id))
        .forEach(y -> {
          tempPatRepo.savePatient(y);
        });
    oHCC.getPatRepo().getAllPatient().removeAll(tempPatRepo.getAllPatient());

  }
}