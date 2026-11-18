package service;

import domain.Appointment;
import domain.Availability;
import domain.AvailabilityStatus;
import domain.Patient;
import repository.AppointmentRepository;
import repository.AvailabilityRepository;
import repository.PatientRepository;

import java.util.Objects;

public class BookingService {

    private final AvailabilityRepository availabilityRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public BookingService(AvailabilityRepository availabilityRepository, AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.availabilityRepository = availabilityRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public Appointment bookAppointment(Patient patient, String availabilityId) {
        Objects.requireNonNull(patient, "patient is required");

        Availability availability = availabilityRepository.findById(availabilityId)
                .filter(a -> a.getStatus() == AvailabilityStatus.AVAILABLE)
                .orElseThrow(() -> new SlotUnavailableException(availabilityId));

        if (appointmentRepository.existsForPatientAt(patient.getId(), availability.getDate(), availability.getStartTime())) {
            throw new DuplicateAppointmentException(patient.getId());
        }

        patientRepository.save(patient);
        availability.markBooked();
        availabilityRepository.save(availability);

        Appointment appointment = new Appointment(
                availability.getProfessional(),
                patient,
                availability.getDate(),
                availability.getStartTime(),
                availability.getEndTime());

        return appointmentRepository.save(appointment);
    }
}
