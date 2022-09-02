package hu.icellmobilsoft.atr.sample.rest;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

import hu.icellmobilsoft.atr.sample.util.XSDValidator;

public class LoadDataImpl implements ILoadData {

    @Override
    public parseXml loadFromXml(String xmlFileName) {
        parseXml oParseXml = new parseXml();
        try {
            XSDValidator validator = new XSDValidator();
            if (validator.Validate(xmlFileName, "samplePatient.xsd")) {
                oParseXml.readSample(oParseXml.parse(xmlFileName));
            } else {
                throw new Error("invalid xml");
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return oParseXml;
    }

    @Override
    public parseJson loadFromJson(String jsonFileName) {
        parseJson jParser = new parseJson();
        JsonParser parser;
        try {
            parser = jParser.parse(jsonFileName);
            jParser.readSample(parser);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jParser;

    }
}
