package hu.icellmobilsoft.atr.sample.rest;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

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

    public static void main(String[] args) {
        parseXml fafaf = new parseXml();
        try {
            XMLEventReader reader = fafaf.parseV2("sample.xml");
            fafaf.readSamplev2(reader);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public XMLEventReader parseV2(String filename) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(in);
            return reader;
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void readSamplev2(XMLEventReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            switch (nextEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:

                    String elementName = nextEvent.asStartElement().getName().getLocalPart();
                    if (!elementName.equals("sample")) {
                        readSampleChilds(reader, elementName);
                    }

                    break;
                case XMLStreamConstants.END_ELEMENT:
                    break;
            }
        }
    }

    public void readSampleChilds(XMLEventReader reader, String tag) throws XMLStreamException {
        while (reader.hasNext()) {
            switch (tag) {
                case "departments":
                    System.out.println("-------------> " + tag + " <-> ");
                    depRepo = getDeps(reader, tag);
                    return;
                case "institutes":
                    System.out.println("-------------> " + tag + " <-> ");
                    instRepo = getInst(reader, tag);
                    return;
                case "patients":
                    System.out.println("-------------> " + tag + " <-> ");
                    patRepo = getPats(reader, tag);
                    return;
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Department parseDepartmentTags(XMLEventReader reader, String elementName, Department tempDep)
            throws XMLStreamException {
        switch (elementName) {
            case "department":
                tempDep = new Department();
                break;
            case "id":
                String id = reader.nextEvent().asCharacters().getData();
                if (!id.isEmpty()) {
                    if (tempDep != null)
                        tempDep.setId(id);
                }
                break;
            case "name":
                String name = reader.nextEvent().asCharacters().getData();
                if (!name.isEmpty()) {
                    if (tempDep != null)
                        tempDep.setName(name);
                }
                break;
        }
        return tempDep;
    }

    DepartmentRepository saveDepartmentToRepository(
            DepartmentRepository tempRep,
            Department temp,
            String endelementName,
            String tag) {
        if (endelementName.equals("department")) {
            System.out.println(" [" + tag + "] getDeps END -> " + endelementName);
            if (temp != null) {
                tempRep.saveDepartment(temp);
            }
        }
        return tempRep;
    }

    DepartmentRepository getDeps(XMLEventReader reader, String tag) throws XMLStreamException {

        Department tempDep = null;
        DepartmentRepository tempDepRep = new DepartmentRepository();

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            switch (nextEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:

                    String elementName = nextEvent.asStartElement().getName().getLocalPart();
                    System.out.println(" [" + tag + "] getDeps start -> " + elementName);
                    tempDep = parseDepartmentTags(reader, elementName, tempDep);

                    break;
                case XMLStreamConstants.END_ELEMENT:

                    String endelementName = nextEvent.asEndElement().getName().getLocalPart();
                    tempDepRep = saveDepartmentToRepository(tempDepRep, tempDep, endelementName, tag);
                    if (endelementName.equals(tag)) {
                        System.out.println("<------------- " + tag + " <-> ");
                        return tempDepRep;
                    }

                    break;
            }
        }
        return tempDepRep;

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Institute parseInstitutesTags(XMLEventReader reader, String elementName, Institute tempDep)
            throws XMLStreamException {
        switch (elementName) {
            case "institute":
                tempDep = new Institute();
                break;
            case "id":
                String id = reader.nextEvent().asCharacters().getData();
                if (!id.isEmpty() && tempDep != null) {
                    tempDep.setId(id);
                }
                break;
            case "name":
                String name = reader.nextEvent().asCharacters().getData();
                if (!name.isEmpty() && tempDep != null) {
                    tempDep.setName(name);
                }
                break;
            case "department":
                String depdep = reader.nextEvent().asCharacters().getData();
                if (!depdep.isEmpty() && tempDep != null) {
                    tempDep.addDepartments(depRepo.findDepartment(depdep));
                }
                break;
            default:
                break;
        }
        return tempDep;
    }

    InstituteRepository saveInstitutesToRepository(
            InstituteRepository tempRep,
            Institute temp,
            String endelementName,
            String tag) {
        if (endelementName.equals("department")) {
            System.out.println(" [" + tag + "] getInst END -> " + endelementName);
            if (temp != null) {
                tempRep.saveInstitute(temp);
            }
        }
        return tempRep;
    }

    InstituteRepository getInst(XMLEventReader reader, String tag) throws XMLStreamException {

        Institute tempInst = null;
        InstituteRepository tempInstRep = new InstituteRepository();

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            switch (nextEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:

                    String elementName = nextEvent.asStartElement().getName().getLocalPart();
                    System.out.println(" [" + tag + "] getInst start -> " + elementName);
                    tempInst = parseInstitutesTags(reader, elementName, tempInst);

                    break;
                case XMLStreamConstants.END_ELEMENT:

                    String endelementName = nextEvent.asEndElement().getName().getLocalPart();
                    tempInstRep = saveInstitutesToRepository(tempInstRep, tempInst, endelementName, tag);
                    if (endelementName.equals(tag)) {
                        System.out.println("<------------- " + tag + " <-> ");
                        return tempInstRep;
                    }

                    break;
            }
        }
        return tempInstRep;

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private PatientRepository getPats(XMLEventReader reader, String tag) throws XMLStreamException {
        Patient tempPat = null;
        PatientRepository tempPatRep = new PatientRepository();

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            switch (nextEvent.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:

                    String elementName = nextEvent.asStartElement().getName().getLocalPart();
                    System.out.println(" [" + tag + "] getDeps start -> " + elementName);
                    tempPat = parsePatientTags(reader, elementName, tempPat);

                    break;
                case XMLStreamConstants.END_ELEMENT:

                    String endelementName = nextEvent.asEndElement().getName().getLocalPart();
                    tempPatRep = savePatientToRepository(tempPatRep, tempPat, endelementName, tag);
                    if (endelementName.equals(tag)) {
                        System.out.println("<------------- " + tag + " <-> ");
                        return tempPatRep;
                    }

                    break;
            }
        }
        return tempPatRep;
    }

    private PatientRepository savePatientToRepository(PatientRepository tempPatRep, Patient tempPat,
            String endelementName, String tag) {
        if (endelementName.equals("patient")) {
            System.out.println(" [" + tag + "] getPat END -> " + endelementName);
            if (tempPat != null) {
                tempPatRep.savePatient(tempPat);
            }
        }
        return tempPatRep;
    }

    private Patient parsePatientTags(XMLEventReader reader, String elementName, Patient tempPat)
            throws XMLStreamException {
        switch (elementName) {
            case "patient":
                tempPat = new Patient();
                break;
            case "id":
                String id = reader.nextEvent().asCharacters().getData();
                if (!id.isEmpty()) {
                    if (tempPat != null)
                        tempPat.setId(id);
                }
                break;
            case "name":
                String name = reader.nextEvent().asCharacters().getData();
                if (!name.isEmpty()) {
                    if (tempPat != null)
                        tempPat.setName(name);
                }
                break;
            case "email":
                String email = reader.nextEvent().asCharacters().getData();
                if (!email.isEmpty()) {
                    if (tempPat != null)
                        tempPat.setEmail(email);
                }
                break;
            case "username":
                String username = reader.nextEvent().asCharacters().getData();
                if (!username.isEmpty()) {
                    if (tempPat != null)
                        tempPat.setUsername(username);
                }
                break;
            case "department":
                String departmentId = reader.nextEvent().asCharacters().getData();
                if (!departmentId.isEmpty()) {
                    if (tempPat != null)
                        tempPat.setDepartment(depRepo.findDepartment(departmentId));
                }
                break;
            case "institute":
                String instituteId = reader.nextEvent().asCharacters().getData();
                if (!instituteId.isEmpty()) {
                    if (tempPat != null)
                        tempPat.setInstitute(instRepo.findInstitute(instituteId));
                }
                break;
        }
        return tempPat;
    }

}
