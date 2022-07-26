package hu.icellmobilsoft.atr.sample;

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
import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;

public class parseXML {
    private DepartmentRepository oDepRepo;

    public parseXML() {
        System.out.println("In simplePrintOut constructor");
        this.oDepRepo = new DepartmentRepository();
        parse();
        oDepRepo.toString();
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

                    this.oDepRepo.saveDepartment(new Department(id, name));
                }
            }
        }

    }

    public void parse() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("sample.xml");
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;

        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = (Document) docBuilder.parse(in);
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            getDepartments(nodeList);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
