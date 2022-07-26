package hu.icellmobilsoft.atr.sample.model;

import java.util.ArrayList;

public class Institute {

    private String id;
    private String name;
    private ArrayList<Department> departments = new ArrayList<Department>();

    public Institute() {
    }

    public Institute(String id, String name, ArrayList<Department> dep) {
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
        this.departments.add(department);
    }

    public String getId() {
        return id;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public String getName() {
        return name;
    }
}
