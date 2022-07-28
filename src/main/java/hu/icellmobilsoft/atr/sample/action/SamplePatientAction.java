package hu.icellmobilsoft.atr.sample.action;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// import org.json.simple.*;

// import org.json.simple.parser.*;
// import org.json.*;
// import org.json.JSONArray;
// import org.json.JSONException;
// import org.json.JSONObject;
// import org.json.JSONString;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

    public DepartmentRepository getDepartments(JSONObject jsonObject) {
        DepartmentRepository tempDepRepFor = new DepartmentRepository();

        JSONObject sample = (JSONObject) jsonObject.get("sample");
        JSONObject departments = (JSONObject) sample.get("departments");
        JSONArray department = (JSONArray) departments.get("department");

        for (int i = 0; i < department.size(); i++) {
            String id = ((JSONObject) department.get(i)).get("id").toString();
            String name = ((JSONObject) department.get(i)).get("name").toString();
            tempDepRepFor.saveDepartment(new Department(id, name));
        }

        System.out.println(depRep.toString());
        return tempDepRepFor;
    }

    public void loadFromJson(String fileName) {
        JSONParser parser = new JSONParser();
        DepartmentRepository depRepForJson = new DepartmentRepository();

        try {
            // Object obj = parser.parse(new FileReader("/Users/User/Desktop/course.json"));
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
            String text = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            Object obj = parser.parse(text);

            JSONObject jsonObject = (JSONObject) obj;
            depRepForJson = getDepartments(jsonObject);
            // System.out.println(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}