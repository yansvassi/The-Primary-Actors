package repository;

import domain.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AppointmentRepository {

    private final Map<String, Appointment> appointmentsById = new HashMap<>();

    public Appointment save(Appointment appointment) {
        appointmentsById.put(appointment.getId(), appointment);
        return appointment;
    }

    public boolean existsForPatientAt(String patientId, LocalDate date, LocalTime startTime) {
        return appointmentsById.values().stream()
                .anyMatch(a -> a.getPatient().getId().equals(patientId)
                        && a.getDate().equals(date)
                        && a.getStartTime().equals(startTime));
    }
} 
