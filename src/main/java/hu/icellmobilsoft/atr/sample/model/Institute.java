package hu.icellmobilsoft.atr.sample.model;

import java.util.ArrayList;

import hu.icellmobilsoft.atr.sample.repository.DepartmentRepository;

public class Institute {

    private String id;
    private String name;
    DepartmentRepository departments = new DepartmentRepository();

    public Institute() {
    }

    // public Institute(String id, String name, ArrayList<Department> dep) {
    public Institute(String id, String name, DepartmentRepository dep) {
        this.id = id.trim();
        this.name = name.trim();
        this.departments = dep;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addDepartments(Department department) {
        this.departments.saveDepartment(department);
    }

    public String getId() {
        return id;
    }

    public DepartmentRepository getDepartments() {
        return departments;
    }

    public String getName() {
        return name;
    }
}
