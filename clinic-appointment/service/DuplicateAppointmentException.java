package service;

public class DuplicateAppointmentException extends RuntimeException {

    public DuplicateAppointmentException(String patientId) {
        super("Patient " + patientId + " already has an appointment at that time slot");
    }
}
