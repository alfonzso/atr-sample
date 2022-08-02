package hu.icellmobilsoft.atr.sample.rest;

import javax.xml.stream.XMLStreamException;

import hu.icellmobilsoft.atr.sample.util.XSDValidator;

public class LoadDataImpl implements ILoadData {

    @Override
    public parseXml loadFromXml(String xml) {
        parseXml oParseXml = new parseXml();
        try {
            XSDValidator validator = new XSDValidator();
            if (validator.Validate(xml, "samplePatient.xsd")) {
                oParseXml.readSample(oParseXml.parse(xml));
            } else {
                throw new Error("invalid xml");
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return oParseXml;
    }

    @Override
    public parseJson loadFromJson(String json) {
        parseJson oParseJson = new parseJson();
        oParseJson.parse(json);

        return oParseJson;

    }

}
