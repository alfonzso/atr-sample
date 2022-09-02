package hu.icellmobilsoft;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

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

public class ParseHelper {

  public static class ParseXml {
    HealthCareContainer oHCC;

    public HealthCareContainer getoHCC() {
      return oHCC;
    }

    public ParseXml(String xmlFileName) {
      try {
        readSample(parse(xmlFileName));
      } catch (XMLStreamException e) {
        e.printStackTrace();
      }
    }

    public XMLEventReader parse(String filename) {
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

    public void readSample(XMLEventReader reader) throws XMLStreamException {
      while (reader.hasNext()) {
        XMLEvent nextEvent = reader.nextEvent();
        switch (nextEvent.getEventType()) {
          case XMLStreamConstants.START_ELEMENT:

            String elementName = nextEvent.asStartElement().getName().getLocalPart();
            if (!elementName.equals("sample")) {
              this.readSampleChilds(reader, elementName);
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
            oHCC.setField(getDeps(reader, tag));
          case "institutes":
            oHCC.setField(getInst(reader, tag));
          case "patients":
            oHCC.setField(getPats(reader, tag));
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
            tempDep.addDepartments(oHCC.getDepRepo().findDepartment(depdep));
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
              tempPat.setDepartment(oHCC.getDepRepo().findDepartment(departmentId));
          }
          break;
        case "institute":
          String instituteId = reader.nextEvent().asCharacters().getData();
          if (!instituteId.isEmpty()) {
            if (tempPat != null)
              tempPat.setInstitute(oHCC.getInstRepo().findInstitute(instituteId));
          }
          break;
      }
      return tempPat;
    }

  }

  public static class ParseJson {
    HealthCareContainer oHCC;

    public HealthCareContainer getoHCC() {
      return oHCC;
    }

    public ParseJson(String jsonFileName){
       try {
        readSample(parse(jsonFileName));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public JsonParser parse(String filename) throws JsonParseException, IOException {

      InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
      JsonFactory jfactory = new JsonFactory();
      JsonParser jParser = jfactory.createParser(in);

      return jParser;
    }

    void readSample(JsonParser jParser) throws IOException {
      while (jParser.nextToken() != JsonToken.END_OBJECT) {
        String fieldname = jParser.getCurrentName();

        if ("departments".equals(fieldname)) {
          jParser.nextToken();
          oHCC.setField(readDepartments(jParser));
        }
        if ("institutes".equals(fieldname)) {
          jParser.nextToken();
          oHCC.setField(readInstitute(jParser));
        }
        if ("patients".equals(fieldname)) {
          jParser.nextToken();
          oHCC.setField(readPatients(jParser));
        }
      }
      jParser.close();
    }

    DepartmentRepository readDepartments(JsonParser jParser) throws IOException {
      DepartmentRepository tempDepRep = new DepartmentRepository();
      Department tempDep = null;

      while (jParser.nextToken() != JsonToken.END_OBJECT) {
        String fieldname = jParser.getCurrentName();

        if ("department".equals(fieldname)) {
          jParser.nextToken();
          JsonToken nextToken;

          while ((nextToken = jParser.nextToken()) != JsonToken.END_ARRAY) {
            switch (jParser.getText()) {
              case "id":
                tempDep = new Department();
                jParser.nextToken();
                tempDep.setId(jParser.getValueAsString());

                break;
              case "name":
                jParser.nextToken();
                tempDep.setName(jParser.getValueAsString());

                break;
              default:
                if (nextToken == JsonToken.END_OBJECT) {
                  tempDepRep.saveDepartment(tempDep);
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
      Institute tempInst = null;

      while (jParser.nextToken() != JsonToken.END_OBJECT) {
        String fieldname = jParser.getCurrentName();

        if ("institute".equals(fieldname)) {
          JsonToken nextToken;

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
                  tempInst.addDepartments(oHCC.getDepRepo().findDepartment(depId));
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
      Patient tempPat = null;

      while (jParser.nextToken() != JsonToken.END_OBJECT) {
        String fieldname = jParser.getCurrentName();

        if ("patient".equals(fieldname)) {
          jParser.nextToken();

          while (jParser.nextToken() != JsonToken.END_ARRAY) {

            switch (jParser.getText()) {
              case "id":
                jParser.nextToken();
                tempPat = new Patient();
                tempPat.setId(jParser.getValueAsString());

                break;
              case "name":
                jParser.nextToken();
                tempPat.setName(jParser.getValueAsString());

                break;
              case "email":
                jParser.nextToken();
                tempPat.setEmail(jParser.getValueAsString());

                break;
              case "username":
                jParser.nextToken();
                tempPat.setUsername(jParser.getValueAsString());

                break;
              case "department":
                jParser.nextToken();
                tempPat.setDepartment(oHCC.getDepRepo().findDepartment(jParser.getValueAsString()));

                break;
              case "institute":
                jParser.nextToken();
                tempPat.setInstitute(oHCC.getInstRepo().findInstitute(jParser.getValueAsString()));
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

}
