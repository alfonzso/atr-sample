package hu.icellmobilsoft;

import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;

public class HealthCareContainer{
  private DepartmentRepository depRepo;
  private PatientRepository patRepo;
  private InstituteRepository instRepo;

  public DepartmentRepository getDepRepo() {
    return depRepo;
  }
  public PatientRepository getPatRepo() {
    return patRepo;
  }
  public InstituteRepository getInstRepo() {
    return instRepo;
  }
  
  public void setField(DepartmentRepository dep){
    this.depRepo = dep;
  }
  public void setField(PatientRepository dep){
    this.patRepo = dep;
  }
  public void setField(InstituteRepository dep){
    this.instRepo = dep;
  }

}