import domain.*;
import repository.*;
import service.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

/** PoC test driver: exercises each feature from the Iteration II requirements. Run from clinic-appointment/. */
public class Main {

    static int failures = 0;

    public static void main(String[] args) throws Exception {
        AvailabilityRepository availabilities = new AvailabilityRepository();
        AppointmentRepository appointments = new AppointmentRepository();
        PatientRepository patients = new PatientRepository();
        HealthcareProfessionalRepository professionals = new HealthcareProfessionalRepository();
        AvailabilityService availabilityService = new AvailabilityService(availabilities);
        BookingService bookingService = new BookingService(availabilities, appointments, patients);

        LocalDate nov1 = LocalDate.of(2026, 11, 1), nov30 = LocalDate.of(2026, 11, 30);

        System.out.println("== Import from CSV (system starts fresh) ==");
        List<Availability> imported = AvailabilitiesCSV.importCSV("availabilities.csv", professionals);
        imported.forEach(availabilities::save);
        check("imports all 7 rows", availabilities.findAll().size() == 7);

        System.out.println("\n== Making availability ==");
        // Requirement example: Dr X available every Monday in November, 9:00-12:00
        HealthcareProfessional drX = professionals.save(
                new HealthcareProfessional("Dr X", "Dr", new MedicalSpecialty("Family Medecine")));
        List<Availability> mondays = availabilityService.makeAvailability(drX, nov1, nov30,
                RecurrenceFrequency.WEEKLY, Set.of(DayOfWeek.MONDAY), null,
                LocalTime.of(9, 0), LocalTime.of(12, 0));
        check("weekly pattern creates 5 Mondays in November", mondays.size() == 5);

        // Monthly pattern: Marc Bouchard on the 15th, 14:00-15:00
        HealthcareProfessional marc = professionals.findByNameAndSpecialty("Marc Bouchard", "Cardiology").orElseThrow();
        List<Availability> monthly = availabilityService.makeAvailability(marc, nov1, nov30,
                RecurrenceFrequency.MONTHLY, null, 15,
                LocalTime.of(14, 0), LocalTime.of(15, 0));
        check("monthly pattern creates 1 slot on the 15th", monthly.size() == 1);

        System.out.println("\n== Searching availability ==");
        check("search by professional returns Dr X's 5 slots",
                availabilityService.searchAvailability(drX.getId(), null, nov1, nov30).size() == 5);
        check("search by specialty (case-insensitive) finds Cardiology slots",
                availabilityService.searchAvailability(null, "cardiology", nov1, nov30).size() == 2);
        check("search by date range returns only Nov 3-4 slots (Nadia x2, David x1)",
                availabilityService.searchAvailability(null, null, LocalDate.of(2026, 11, 3), LocalDate.of(2026, 11, 4)).size() == 3);

        System.out.println("\n== Booking an appointment ==");
        Availability amelie9 = imported.stream()
                .filter(a -> a.getProfessional().getName().equals("Amelie Tremblay") && a.getStartTime().equals(LocalTime.of(9, 0)))
                .findFirst().orElseThrow();
        // Patient arrived at the clinic
        Patient emilie = new Patient("Emilie", "Lavoie", "514-555-0101", "LAVE99010101");
        Appointment arrived = bookingService.bookAppointment(emilie, amelie9.getId());
        check("arrived patient books a slot", arrived != null && arrived.getPatient() == emilie);
        check("booked slot is marked BOOKED",
                availabilities.findById(amelie9.getId()).orElseThrow().getStatus() == AvailabilityStatus.BOOKED);

        // Patient over the phone
        Patient olivier = new Patient("Olivier", "Cote", "514-555-0102", "COTO85061502");
        Availability marc10 = imported.stream()
                .filter(a -> a.getProfessional().getName().equals("Marc Bouchard")).findFirst().orElseThrow();
        check("phone patient books a slot", bookingService.bookAppointment(olivier, marc10.getId()) != null);

        expectError("error: booking an already-booked slot", SlotUnavailableException.class,
                () -> bookingService.bookAppointment(new Patient("Fatima", "Benali", "438-555-0103", null), amelie9.getId()));
        expectError("error: same patient, same date and start time", DuplicateAppointmentException.class,
                () -> bookingService.bookAppointment(emilie, mondays.get(0).getId())); // Dr X, Mon 2026-11-02 09:00
        check("booked slots drop out of search",
                availabilityService.searchAvailability(null, "Family Medecine", LocalDate.of(2026, 11, 2), LocalDate.of(2026, 11, 2))
                        .stream().noneMatch(a -> a.getId().equals(amelie9.getId())));

        
        System.out.println("\n" + (failures == 0 ? "ALL CHECKS PASSED" : failures + " CHECK(S) FAILED"));
    }

    static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + name);
        if (!ok) failures++;
    }

    static void expectError(String name, Class<? extends Exception> type, Runnable action) {
        try {
            action.run();
            check(name, false);
        } catch (Exception e) {
            check(name, type.isInstance(e));
        }
    }
}
