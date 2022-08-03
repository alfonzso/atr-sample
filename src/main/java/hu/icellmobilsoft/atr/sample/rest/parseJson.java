package hu.icellmobilsoft.atr.sample.rest;

import java.io.IOException;
import java.io.InputStream;

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

    public JsonParser parse(String filename) throws JsonParseException, IOException {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
        JsonFactory jfactory = new JsonFactory();
        JsonParser jParser = jfactory.createParser(in);

        return jParser;
    }

    // public static void main(String[] args) throws JsonParseException, IOException {
    //     parseJson jParser = new parseJson();
    //     JsonParser parser = jParser.parse("example.json");
    //     jParser.readSample(parser);
    //     if (jParser.patRepo.getAllPatient().size() != 5) {
    //         throw new Error("refactor failed");
    //     }
    // }

    void readSample(JsonParser jParser) throws IOException {
        while (jParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jParser.getCurrentName();

            if ("departments".equals(fieldname)) {
                jParser.nextToken();
                depRepo = readDepartments(jParser);
            }
            if ("institutes".equals(fieldname)) {
                jParser.nextToken();
                instRepo = readInstitute(jParser);
            }
            if ("patients".equals(fieldname)) {
                jParser.nextToken();
                patRepo = readPatients(jParser);
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
                JsonToken nextToken;
                String id = "", name = "";

                while ((nextToken = jParser.nextToken()) != JsonToken.END_ARRAY) {
                    switch (jParser.getText()) {
                        case "id":
                            jParser.nextToken();
                            id = jParser.getText();

                            break;
                        case "name":
                            jParser.nextToken();
                            name = jParser.getText();

                            break;
                        default:
                            if (nextToken == JsonToken.END_OBJECT) {
                                tempDepRep.saveDepartment(new Department(id, name));
                            }
                            break;
                    }
                }
            }
        }
        return tempDepRep;
    }

    InstituteRepository readInstitute(JsonParser jParser) throws IOException {
        InstituteRepository tempInstRep = new InstituteRepository();
        while (jParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jParser.getCurrentName();

            if ("institute".equals(fieldname)) {
                JsonToken nextToken;
                Institute tempInst = new Institute();

                while ((nextToken = jParser.nextToken()) != JsonToken.END_ARRAY) {
                    if ("id".equals(jParser.getValueAsString())) {
                        jParser.nextToken();
                        tempInst = new Institute();
                        tempInst.setId(jParser.getValueAsString());
                    }
                    if ("name".equals(jParser.getValueAsString())) {
                        jParser.nextToken();
                        tempInst.setName(jParser.getValueAsString());
                    }
                    if ("departments".equals(jParser.getValueAsString())) {
                        nextToken = jParser.nextToken();
                        while (nextToken != JsonToken.END_ARRAY && nextToken != JsonToken.END_OBJECT) {
                            String depId = jParser.getValueAsString();
                            if (depId != null && depId != "department") {
                                tempInst.addDepartments(depRepo.findDepartment(depId));
                            }
                            nextToken = jParser.nextToken();
                        }
                        tempInstRep.saveInstitute(tempInst);
                    }
                }
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

                Patient tempPat = new Patient();
                while (jParser.nextToken() != JsonToken.END_ARRAY) {

                    switch (jParser.getText()) {
                        case "id":
                            jParser.nextToken();
                            tempPat = new Patient();
                            tempPat.setId(jParser.getText());

                            break;
                        case "name":
                            jParser.nextToken();
                            tempPat.setName(jParser.getText());

                            break;
                        case "email":
                            jParser.nextToken();
                            tempPat.setEmail(jParser.getText());

                            break;
                        case "username":
                            jParser.nextToken();
                            tempPat.setUsername(jParser.getText());

                            break;
                        case "department":
                            jParser.nextToken();
                            tempPat.setDepartment(depRepo.findDepartment(jParser.getText()));

                            break;
                        case "institute":
                            jParser.nextToken();
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
}