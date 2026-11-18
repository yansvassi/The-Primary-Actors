package domain;

import java.util.UUID;

public abstract class ClinicStaff {

    private final String id;
    private final String name;

    protected ClinicStaff(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
