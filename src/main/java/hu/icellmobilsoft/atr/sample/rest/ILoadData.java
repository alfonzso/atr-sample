package hu.icellmobilsoft.atr.sample.rest;

public interface ILoadData {
    parseXml loadFromXml(String xml);

    parseJson loadFromJson(String json);


}
