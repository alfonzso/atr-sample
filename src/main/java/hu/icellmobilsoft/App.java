package hu.icellmobilsoft;

import hu.icellmobilsoft.atr.sample.action.SamplePatientAction;
import hu.icellmobilsoft.atr.sample.model.Patient;
import hu.icellmobilsoft.atr.sample.rest.ParseXml;

public class App {
    public static void main(String[] args) {
        // parseXml opx = new parseXml();
        // patientTest(opx);
        SamplePatientAction osp = new SamplePatientAction();
        osp.loadFromJson("example.json");

        patientJsonTest(osp);

        Patient patient = osp.queryPatientData("kv", "000008");
        osp.deletePatient("PATIENT8");

    }

    public static void patientXmlTest(ParseXml opx) {
        System.out.println("######################## allDepartment");
        opx.getDepRepo().toString();
        System.out.println("######################## allDepartment End \n");

        System.out.println("######################## allInstitute");
        opx.getInstRepo().toString();
        System.out.println("######################## allInstitute End \n");

        System.out.println("######################## allPatient");
        opx.getPatRepo().toString();
        System.out.println("######################## allPatient End \n");
    }

    public static void patientJsonTest(SamplePatientAction spa) {
        System.out.println("######################## allDepartment");
        spa.getoHCC().getDepRepo().toString();
        System.out.println("######################## allDepartment End \n");

        System.out.println("######################## allInstitute");
        spa.getoHCC().getInstRepo().toString();
        System.out.println("######################## allInstitute End \n");

        System.out.println("######################## allPatient");
        spa.getoHCC().getPatRepo().toString();
        System.out.println("######################## allPatient End \n");
    }

}
