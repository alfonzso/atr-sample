package hu.icellmobilsoft.atr.sample.model;

public class Patient {

    private String id;
    private String name;
    private String email;
    private String username;
    private Institute institute;
    private Department department;
    

    public Patient(String id, String name, String email, String username, Institute institute, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.institute = institute;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }





}
