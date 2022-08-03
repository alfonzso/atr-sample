package hu.icellmobilsoft.atr.sample.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import hu.icellmobilsoft.atr.sample.model.Department;
import hu.icellmobilsoft.atr.sample.model.Institute;
import hu.icellmobilsoft.atr.sample.model.Patient;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;

public class parseJson {

    private DepartmentRepository depRepo;
    private PatientRepository patRepo;
    private InstituteRepository instRepo;

    public PatientRepository getPatRepo() {
        return patRepo;
    }

    public InstituteRepository getInstRepo() {
        return instRepo;
    }

    public DepartmentRepository getDepRepo() {
        return depRepo;
    }

    public parseJson() {

    }

    public JsonParser parseV2(String filename) throws JsonParseException, IOException {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
        JsonFactory jfactory = new JsonFactory();
        JsonParser jParser = jfactory.createParser(in);

        return jParser;
    }

    public void parse(String fileName) {
        JSONParser parser = new JSONParser();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);

        try {
            String text = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            Object obj = parser.parse(text);

            JSONObject jsonObject = (JSONObject) obj;
            this.depRepo = getDepartments(jsonObject);
            this.instRepo = getInstitute(jsonObject);
            this.patRepo = getPatients(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws JsonParseException, IOException {
        parseJson fafa = new parseJson();
        JsonParser parser = fafa.parseV2("example.json");
        fafa.readSample(parser);
        if (fafa.patRepo.getAllPatient().size() != 5) {
            throw new Error("refactor failed");
        }

    }

    void readSample(JsonParser jParser) throws IOException {
        while (jParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jParser.getCurrentName();

            if ("departments".equals(fieldname)) {
                jParser.nextToken();
                // System.out.println(jParser.getValueAsString());
                depRepo = readDepartments(jParser);
                // return;
            }
            if ("institutes".equals(fieldname)) {
                jParser.nextToken();
                // System.out.println(jParser.getValueAsString());
                instRepo = readInstitute(jParser);
                // return;
            }
            if ("patients".equals(fieldname)) {
                jParser.nextToken();
                // System.out.println(jParser.getValueAsString());
                // readInstitute(jParser);
                patRepo = readPatients(jParser);
                // return;
            }

        }
        jParser.close();
    }

    DepartmentRepository readDepartments(JsonParser jParser) throws IOException {
        DepartmentRepository tempDepRep = new DepartmentRepository();

        while (jParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jParser.getCurrentName();

            if ("department".equals(fieldname)) {
                jParser.nextToken();
                // System.out.println(jParser.getValueAsString());
                JsonToken a;
                // readDepartmentIDName(jParser);
                String id = "", name = "";
                while ((a = jParser.nextToken()) != JsonToken.END_ARRAY) {
                    // addresses.add(jParser.getText());
                    switch (jParser.getText()) {
                        case "id":
                            jParser.nextToken();
                            // System.out.println("-id--" + jParser.getText());
                            id = jParser.getText();
                            break;
                        case "name":
                            jParser.nextToken();
                            // System.out.println("-name--" + jParser.getText());

                            name = jParser.getText();
                            break;

                        default:
                            // System.out.println("-break--" + jParser.getText() + "-----" + a);
                            if (a == JsonToken.END_OBJECT) {
                                tempDepRep.saveDepartment(new Department(id, name));
                            }
                            break;
                    }
                }
                // jParser.getIntValue();
                // return;
            }

        }
        // jParser.close();
        return tempDepRep;
    }

    InstituteRepository readInstitute(JsonParser jParser) throws IOException {
        InstituteRepository tempInstRep = new InstituteRepository();
        while (jParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jParser.getCurrentName();

            if ("institute".equals(fieldname)) {
                // System.out.println("xxxxxxxxxxxx " + jParser.getValueAsString());
                JsonToken a;
                String id = "", name = "", department = "";
                Institute tempInst = new Institute();
                while ((a = jParser.nextToken()) != JsonToken.END_ARRAY) {
                    if ("id".equals(jParser.getValueAsString())) {
                        jParser.nextToken();
                        tempInst = new Institute();
                        // System.out.println("-id--" + jParser.getText());
                        id = jParser.getText();
                        tempInst.setId(id);
                        // System.out.println("====InstId====== " + jParser.getValueAsString() + " ><
                        // ");
                    }
                    if ("name".equals(jParser.getValueAsString())) {
                        jParser.nextToken();
                        // System.out.println("-name--" + jParser.getText());
                        name = jParser.getText();
                        tempInst.setName(name);
                        // System.out.println("====NameId====== " + jParser.getValueAsString() + " ><
                        // ");
                    }
                    if ("departments".equals(jParser.getValueAsString())) {
                        a = jParser.nextToken();
                        while (a != JsonToken.END_ARRAY && a != JsonToken.END_OBJECT) {
                            String depId = jParser.getValueAsString();
                            if (depId != null && depId != "department") {
                                // System.out.println("-id--" + jParser.getText());
                                department = jParser.getText();
                                tempInst.addDepartments(depRepo.findDepartment(department));
                                // System.out.println(" ======DepId==== " + jParser.getValueAsString() + " >< "
                                // + a);
                            }
                            a = jParser.nextToken();
                        }
                        tempInstRep.saveInstitute(tempInst);
                    }
                }
                // return;
            }

        }
        return tempInstRep;
    }

    PatientRepository readPatients(JsonParser jParser) throws IOException {
        PatientRepository tempPatRep = new PatientRepository();
        while (jParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jParser.getCurrentName();

            if ("patient".equals(fieldname)) {
                jParser.nextToken();
                // System.out.println(jParser.getValueAsString());
                // readDepartmentIDName(jParser);
                Patient tempPat = new Patient();
                while (jParser.nextToken() != JsonToken.END_ARRAY) {
                    // addresses.add(jParser.getText());
                    switch (jParser.getText()) {
                        case "id":
                            jParser.nextToken();
                            tempPat = new Patient();
                            // System.out.println();
                            // System.out.println("-id--" + jParser.getText());
                            tempPat.setId(jParser.getText());
                            break;
                        case "name":
                            jParser.nextToken();
                            // System.out.println("-name--" + jParser.getText());
                            tempPat.setName(jParser.getText());

                            break;
                        case "email":
                            jParser.nextToken();
                            // System.out.println("-email--" + jParser.getText());
                            tempPat.setEmail(jParser.getText());

                            break;
                        case "username":
                            jParser.nextToken();
                            // System.out.println("-username--" + jParser.getText());
                            tempPat.setUsername(jParser.getText());

                            break;
                        case "department":
                            jParser.nextToken();
                            // System.out.println("-department--" + jParser.getText());
                            tempPat.setDepartment(depRepo.findDepartment(jParser.getText()));

                            break;
                        case "institute":
                            jParser.nextToken();
                            // System.out.println("-institute--" + jParser.getText());
                            tempPat.setInstitute(instRepo.findInstitute(jParser.getText()));

                            tempPatRep.savePatient(tempPat);
                            break;

                        default:
                            break;
                    }
                }
            }

        }
        return tempPatRep;
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
                    tempInst.addDepartments(new Department(depId, this.depRepo.findDepartment(depId).getName()));
                }
            } else {
                String depId = departments.get("department").toString();
                tempInst.addDepartments(new Department(depId, this.depRepo.findDepartment(depId).getName()));
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
            tempPatient.setDepartment(this.depRepo.findDepartment(department));
            tempPatient.setInstitute(this.instRepo.findInstitute(institute));
            tempPatRep.savePatient(tempPatient);
        }

        tempPatRep.toString();
        return tempPatRep;
    }
}