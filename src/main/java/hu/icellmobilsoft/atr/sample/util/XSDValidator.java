package hu.icellmobilsoft.atr.sample.util;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/**
 * The type Xsd validator.
 * @author juhaszkata
 * @version 1
 */
public class XSDValidator {
    /**
     * Validate boolean.
     *
     * @param xmlFileName the xml file name
     * @param xsdFileName the xsd file name
     * @return the boolean
     */
    public boolean Validate(String xmlFileName, String xsdFileName) {
        String xml = this.getClass().getClassLoader().getResource(xmlFileName).getFile().toString();
        String xsd = this.getClass().getClassLoader().getResource(xsdFileName).getFile().toString();
        boolean isValid = validateXMLSchema(xml, xsd);

        return isValid;
    }

    /**
     * Validate xml schema boolean.
     *
     * @param xmlPath the xml path
     * @param xsdPath the xsd path
     * @return the boolean
     */
    public static boolean validateXMLSchema(String xmlPath, String xsdPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        } catch (SAXException e1) {
            System.out.println("SAX Exception: " + e1.getMessage());
            return false;
        }

        return true;

    }
}
