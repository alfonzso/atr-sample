package hu.icellmobilsoft;

import hu.icellmobilsoft.atr.sample.parseXml;
import hu.icellmobilsoft.atr.sample.action.SamplePatientAction;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        parseXml opx = new parseXml();
        SamplePatientAction osp = new SamplePatientAction();
        osp.loadFromJson("example.json");
    }
}
