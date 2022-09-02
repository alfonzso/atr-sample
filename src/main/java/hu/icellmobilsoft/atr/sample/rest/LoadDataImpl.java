package hu.icellmobilsoft.atr.sample.rest;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

import hu.icellmobilsoft.atr.sample.util.SimpleTicketConstans;
import hu.icellmobilsoft.atr.sample.util.XSDValidator;

public class LoadDataImpl implements ILoadData {

    @Override
    public ParseXml loadFromXml(String xmlFileName) {
        if(StringUtils.isBlank(xmlFileName)){
            throw new IllegalArgumentException(SimpleTicketConstans.PARAMETER_CANNOT_NULL_MSG);
        }
        ParseXml oParseXml = new ParseXml();
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
    public ParseJson loadFromJson(String jsonFileName) {
        ParseJson jParser = new ParseJson();
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
