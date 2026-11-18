package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Availability {

    private final String id;
    private final HealthcareProfessional professional;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private AvailabilityStatus status;

    public Availability(HealthcareProfessional professional, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.id = UUID.randomUUID().toString();
        this.professional = professional;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = AvailabilityStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public HealthcareProfessional getProfessional() {
        return professional;
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

    public AvailabilityStatus getStatus() {
        return status;
    }

    public void markBooked() {
        this.status = AvailabilityStatus.BOOKED;
    }
}
