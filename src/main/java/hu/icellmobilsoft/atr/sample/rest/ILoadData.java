package hu.icellmobilsoft.atr.sample.rest;

public interface ILoadData {
    ParseXml loadFromXml(String xml);

    ParseJson loadFromJson(String json);


}
