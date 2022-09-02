package hu.icellmobilsoft.atr.sample.rest;

import org.apache.commons.lang3.StringUtils;

import hu.icellmobilsoft.HealthCareContainer;
import hu.icellmobilsoft.ParseHelper;
import hu.icellmobilsoft.atr.sample.util.SimpleTicketConstans;
import hu.icellmobilsoft.atr.sample.util.XSDValidator;

public class LoadDataImpl implements ILoadData {

  @Override
  public HealthCareContainer loadFromXml(String xmlFileName) {
    if (StringUtils.isBlank(xmlFileName)) {
      throw new IllegalArgumentException(SimpleTicketConstans.PARAMETER_CANNOT_NULL_MSG);
    }

    XSDValidator validator = new XSDValidator();
    if (validator.Validate(xmlFileName, "samplePatient.xsd")) {
      return new ParseHelper.ParseXml(xmlFileName).getoHCC();
    } else {
      throw new Error("invalid xml");
    }

  }

  @Override
  public HealthCareContainer loadFromJson(String jsonFileName) {
    return new ParseHelper.ParseJson(jsonFileName).getoHCC();
  }
}
