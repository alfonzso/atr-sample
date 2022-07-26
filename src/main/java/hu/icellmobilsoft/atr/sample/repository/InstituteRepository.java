package hu.icellmobilsoft.atr.sample.repository;

import java.util.ArrayList;

import hu.icellmobilsoft.atr.sample.model.Institute;

public class InstituteRepository {
    ArrayList<Institute> institutes = new ArrayList<Institute>();

    public void saveInstitute(Institute institute) {
        institutes.add(institute);
    }

    public Institute findInstitute(String id) {
        for (int index = 0; index < institutes.size(); index++) {
            if (institutes.get(index).getId().equals(id)) {
                return institutes.get(index);
            }
        }
        return null;
    }

    // findInstitute(String id)
}
