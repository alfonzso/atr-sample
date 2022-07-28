package hu.icellmobilsoft.atr.sample.repository;

import java.util.ArrayList;

import hu.icellmobilsoft.atr.sample.model.Institute;

public class InstituteRepository {
    ArrayList<Institute> institutes = new ArrayList<Institute>();

    public void saveInstitute(Institute institute) {
        Institute existingInstitute = findInstitute(institute.getId());

        if (existingInstitute == null) {
            institutes.add(institute);
        } else {
            Integer idx = institutes.indexOf(existingInstitute);
            institutes.set(idx, new Institute(institute.getId(), institute.getName(), institute.getDepartments()));
        }

    }

    public Institute findInstitute(String id) {
        for (int index = 0; index < institutes.size(); index++) {
            if (institutes.get(index).getId().equals(id)) {
                return institutes.get(index);
            }
        }
        return null;
    }

    public ArrayList<Institute> getAllInstitute() {
        return institutes;
    }

    @Override
    public String toString() {
        for (int i = 0; i < institutes.size(); i++) {
            System.out.println(institutes.get(i).getId());
            System.out.println(institutes.get(i).getName());
            institutes.get(i).getDepartments().toString();
        }
        return super.toString();
    }

}
