package hu.icellmobilsoft.atr.sample.model;

public class Department {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
}
