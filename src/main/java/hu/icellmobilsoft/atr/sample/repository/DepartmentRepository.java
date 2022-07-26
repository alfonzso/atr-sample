package hu.icellmobilsoft.atr.sample.repository;

import java.util.ArrayList;

import hu.icellmobilsoft.atr.sample.model.Department;

public class DepartmentRepository {

    ArrayList<Department> departments = new ArrayList<Department>();

    public DepartmentRepository() {
    }

    public void saveDepartment(Department department) {
        departments.add(department);
    }

    public Department findDepartment(String id) {
        for (int index = 0; index < departments.size(); index++) {
            if (departments.get(index).getId().equals(id)) {
                return departments.get(index);
            }
        }
        return null;
    }

    public ArrayList<Department> getAllDepartment() {
        return departments;
    }

    @Override
    public String toString() {
        for (int i = 0; i < departments.size(); i++) {
            System.out.println(departments.get(i).getId() + "--> " + departments.get(i).getName());
        }
        return super.toString();
    }
}
