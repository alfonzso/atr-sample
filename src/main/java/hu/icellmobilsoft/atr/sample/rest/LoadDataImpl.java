package hu.icellmobilsoft.atr.sample.rest;

import javax.xml.stream.XMLStreamException;

public class LoadDataImpl implements ILoadData {

    @Override
    public parseXml loadFromXml(String xml) {
        parseXml oParseXml = new parseXml();
        try {
            oParseXml.readSample(oParseXml.parse(xml));
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
