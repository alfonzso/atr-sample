package hu.icellmobilsoft.atr.sample.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import hu.icellmobilsoft.atr.sample.parseXml;
import hu.icellmobilsoft.atr.sample.model.Department;
import hu.icellmobilsoft.atr.sample.model.Institute;
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

    public DepartmentRepository getDepartments(JSONObject jsonObject) {
        DepartmentRepository tempDepRep = new DepartmentRepository();

        JSONObject sample = (JSONObject) jsonObject.get("sample");
        JSONObject departments = (JSONObject) sample.get("departments");
        JSONArray department = (JSONArray) departments.get("department");

        for (int i = 0; i < department.size(); i++) {
            String id = ((JSONObject) department.get(i)).get("id").toString();
            String name = ((JSONObject) department.get(i)).get("name").toString();
            tempDepRep.saveDepartment(new Department(id, name));
        }

        tempDepRep.toString();
        return tempDepRep;
    }

    public InstituteRepository getInstitute(JSONObject jsonObject) {
        InstituteRepository tempInstRep = new InstituteRepository();

        JSONObject sample = (JSONObject) jsonObject.get("sample");
        JSONObject institutes = (JSONObject) sample.get("institutes");
        JSONArray institute = (JSONArray) institutes.get("institute");

        for (int i = 0; i < institute.size(); i++) {
            Institute tempInst = new Institute();
            tempInst.setId(
                    ((JSONObject) institute.get(i)).get("id").toString());
            tempInst.setName(
                    ((JSONObject) institute.get(i)).get("name").toString());
            JSONObject departments = (JSONObject) ((JSONObject) institute.get(i)).get("departments");
            if (departments.get("department") instanceof java.util.ArrayList) {
                JSONArray department = (JSONArray) departments.get("department");
                for (int j = 0; j < department.size(); j++) {
                    String depId = department.get(j).toString();
                    tempInst.addDepartments(new Department(depId, this.depRep.findDepartment(depId).getName()));
                }
            } else {
                String depId = departments.get("department").toString();
                tempInst.addDepartments(new Department(depId, this.depRep.findDepartment(depId).getName()));
            }

            tempInstRep.saveInstitute(tempInst);
        }

        tempInstRep.toString();
        return tempInstRep;
    }

    public PatientRepository getPatients(JSONObject jsonObject) {
        PatientRepository tempPatRep = new PatientRepository();

        JSONObject sample = (JSONObject) jsonObject.get("sample");
        JSONObject patients = (JSONObject) sample.get("patients");
        JSONArray patient = (JSONArray) patients.get("patient");

        for (int i = 0; i < patient.size(); i++) {
            Patient tempPatient = new Patient();

            String id = ((JSONObject) patient.get(i)).get("id").toString();
            String name = ((JSONObject) patient.get(i)).get("name").toString();
            String email = ((JSONObject) patient.get(i)).get("email").toString();
            String username = ((JSONObject) patient.get(i)).get("username").toString();
            String department = ((JSONObject) patient.get(i)).get("department").toString();
            String institute = ((JSONObject) patient.get(i)).get("institute").toString();

            tempPatient.setId(id);
            tempPatient.setName(name);
            tempPatient.setEmail(email);
            tempPatient.setUsername(username);
            tempPatient.setDepartment(this.depRep.findDepartment(department));
            tempPatient.setInstitute(this.instRep.findInstitute(institute));
            tempPatRep.savePatient(tempPatient);
        }

        tempPatRep.toString();
        return tempPatRep;
    }

    public void loadFromJson(String fileName) {
        JSONParser parser = new JSONParser();

        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
            String text = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            Object obj = parser.parse(text);

            JSONObject jsonObject = (JSONObject) obj;
            this.depRep = getDepartments(jsonObject);
            this.instRep = getInstitute(jsonObject);
            this.patRep = getPatients(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}