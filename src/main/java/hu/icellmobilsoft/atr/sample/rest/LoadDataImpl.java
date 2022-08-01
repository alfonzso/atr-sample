package hu.icellmobilsoft.atr.sample.rest;

public class LoadDataImpl implements ILoadData {

    @Override
    public parseXml loadFromXml(String xml) {
        parseXml oParseXml = new parseXml();
        oParseXml.parse(xml);

        return oParseXml;
    }

    @Override
    public parseJson loadFromJson(String json) {
        parseJson oParseJson = new parseJson();
        oParseJson.parse(json);

        return oParseJson;

    }

}
