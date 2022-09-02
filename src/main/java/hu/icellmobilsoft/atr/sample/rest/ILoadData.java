package hu.icellmobilsoft.atr.sample.rest;

import hu.icellmobilsoft.HealthCareContainer;

public interface ILoadData {
    HealthCareContainer loadFromXml(String xml);

    HealthCareContainer loadFromJson(String json);


}
