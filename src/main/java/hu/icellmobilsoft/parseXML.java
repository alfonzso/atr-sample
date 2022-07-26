package hu.icellmobilsoft;

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

public class parseXML {
    public parseXML() {
        System.out.println("In simplePrintOut constructor");
        parse();
    }

    public void parse() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("sample.xml");
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = (Document) docBuilder.parse(in);
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
