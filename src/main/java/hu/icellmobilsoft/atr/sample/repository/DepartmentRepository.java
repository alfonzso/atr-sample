package hu.icellmobilsoft.atr.sample.repository;

import java.util.ArrayList;
import java.util.stream.Stream;

import hu.icellmobilsoft.atr.sample.model.Department;

public class DepartmentRepository {

    ArrayList<Department> departments = new ArrayList<Department>();

    public DepartmentRepository() {
    }

    public void saveDepartment(Department department) {
        Department existingDep = findDepartment(department.getId());

        if (existingDep == null) {
            departments.add(department);
        } else {
            Integer idx = departments.indexOf(existingDep);
            departments.set(idx, new Department(department.getId(), department.getName()));
        }

    }

    public Department findDepartment(String id) {
        Stream<Department> findDepId = departments.stream().filter(x -> x.getId().equals(id));
        return findDepId.findFirst().orElse(null);
    }

    public ArrayList<Department> getAllDepartment() {
        return departments;
    }

    @Override
    public String toString() {
      departments.stream().forEach(x -> {
            System.out.println(x.getId());
            System.out.println(x.getName());
        });
        return super.toString();
    }

}
