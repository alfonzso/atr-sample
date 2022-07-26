package hu.icellmobilsoft.atr.sample.model;

import java.util.ArrayList;

public class Department {

    // private Department departments[];
    private String id;
    private String name;
    // ArrayList<Department> departmentList = new ArrayList<Department>();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Department() {
    }

    public Department(String id, String name) {
        this.id = id.trim();
        this.name = name.trim();
    }

    public Department getInstance() {
        return this;
    }

    // public void AddNewDepartment(String id, String name) {
    //     departmentList.add(new Department(id, name));
    // }

    // public String getDepartmentById(String id) {
    //     for (int index = 0; index < departmentList.size(); index++) {
    //         if (departmentList.get(index).id.equals(id)) {
    //             return departmentList.get(index).name;
    //         }
    //     }
    //     return "";
    // }

    // public ArrayList<Department> getDepartmenList() {
    //     return departmentList;
    // }

    // public static void main(String[] args) {
    // Department oDep = new Department();
    // oDep.AddNewDepartment("1", "buci");
    // System.out.println(oDep.getDepartmenList());
    // }
}
