package hu.icellmobilsoft.atr.sample.model;

public class Department {

    private String id;
    private String name;

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
}
