package hu.icellmobilsoft.atr.sample.repository;

import java.util.ArrayList;
import java.util.stream.Stream;

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
        Stream<Institute> findInstId = institutes.stream().filter(x -> x.getId().equals(id));
        return findInstId.findFirst().orElse(null);
    }

    public ArrayList<Institute> getAllInstitute() {
        return institutes;
    }

    @Override
    public String toString() {
        institutes.stream().forEach(x -> {
            System.out.printf("[ %s ] => id: %s name: %s\n", this.getClass().getSimpleName().toString(), x.getId(),
                    x.getName());
            x.getDepartments().toString();
            System.out.println();
        });
        return super.toString();
    }

}
