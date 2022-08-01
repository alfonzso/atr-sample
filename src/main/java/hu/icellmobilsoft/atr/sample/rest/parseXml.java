package hu.icellmobilsoft.atr.sample.rest;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import hu.icellmobilsoft.atr.sample.model.Department;
import hu.icellmobilsoft.atr.sample.model.Institute;
import hu.icellmobilsoft.atr.sample.model.Patient;
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;
import hu.icellmobilsoft.atr.sample.repository.InstituteRepository;
import hu.icellmobilsoft.atr.sample.repository.PatientRepository;

public class parseXml {
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

    public parseXml() {
        this.depRepo = new DepartmentRepository();
        this.patRepo = new PatientRepository();
        this.instRepo = new InstituteRepository();
        // parse("sample.xml");
    }

    public void getDepartments(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeName() == "departments") {
                Element elem = (Element) node;
                NodeList departmentList = elem.getElementsByTagName("department");
                for (int j = 0; j < departmentList.getLength(); j++) {
                    Node depart = departmentList.item(j);
                    String id = ((Element) depart).getElementsByTagName("id").item(0).getTextContent();
                    String name = ((Element) depart).getElementsByTagName("name").item(0).getTextContent();

                    this.depRepo.saveDepartment(new Department(id, name));
                }
            }
        }
    }

    public String getData(NodeList nodeList) {
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    public void getPatient(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName() == "patients") {
                Element nodeE = (Element) nodeList.item(i);

                NodeList patients = nodeE.getElementsByTagName("patient");

                for (int j = 0; j < patients.getLength(); j++) {
                    Patient tempPatient = new Patient();

                    Element patient = (Element) patients.item(j);
                    NodeList ids = patient.getElementsByTagName("id");
                    NodeList names = patient.getElementsByTagName("name");
                    NodeList emails = patient.getElementsByTagName("email");
                    NodeList usernames = patient.getElementsByTagName("username");
                    NodeList departments = patient.getElementsByTagName("department");
                    NodeList institutes = patient.getElementsByTagName("institute");

                    tempPatient.setId(getData(ids));
                    tempPatient.setName(getData(names));
                    tempPatient.setEmail(getData(emails));
                    tempPatient.setUsername(getData(usernames));
                    tempPatient.setDepartment(depRepo.findDepartment(getData(departments)));
                    tempPatient.setInstitute(instRepo.findInstitute(getData(institutes)));

                    patRepo.savePatient(tempPatient);
                }
            }
        }
    }

    public void getInstitutes(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName() == "institutes") {
                Element nodeE = (Element) nodeList.item(i);

                NodeList institutes = nodeE.getElementsByTagName("institute");

                for (int j = 0; j < institutes.getLength(); j++) {
                    Institute tempInst = new Institute();

                    Element institute = (Element) institutes.item(j);
                    NodeList ids = institute.getElementsByTagName("id");
                    NodeList names = institute.getElementsByTagName("name");
                    NodeList department = institute.getElementsByTagName("department");
                    if (ids.getLength() > 0) {
                        tempInst.setId(ids.item(0).getTextContent());
                    }
                    if (names.getLength() > 0) {
                        tempInst.setName(names.item(0).getTextContent());
                    }
                    for (int k = 0; k < department.getLength(); k++) {
                        String idValue = department.item(k).getTextContent();
                        tempInst.addDepartments(new Department(idValue, depRepo.findDepartment(idValue).getName()));
                    }
                    instRepo.saveInstitute(tempInst);
                }
            }
        }
    }

    public void parse(String xmlFileName) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(xmlFileName);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;

        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = (Document) docBuilder.parse(in);
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            getDepartments(nodeList);
            getInstitutes(nodeList);
            getPatient(nodeList);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
