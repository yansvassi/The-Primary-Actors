package domain;

import java.util.UUID;

public class Patient {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String ramqNumber;

    public Patient(String firstName, String lastName, String phoneNumber, String ramqNumber) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.ramqNumber = ramqNumber;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRamqNumber() {
        return ramqNumber;
    }
}
