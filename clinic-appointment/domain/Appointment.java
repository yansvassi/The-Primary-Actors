package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Appointment {

    private final String id;
    private final HealthcareProfessional professional;
    private final Patient patient;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Appointment(HealthcareProfessional professional, Patient patient, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.id = UUID.randomUUID().toString();
        this.professional = professional;
        this.patient = patient;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public HealthcareProfessional getProfessional() {
        return professional;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
