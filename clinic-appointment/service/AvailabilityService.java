package service;

import domain.Availability;
import domain.AvailabilityStatus;
import domain.HealthcareProfessional;
import domain.RecurrenceFrequency;
import repository.AvailabilityRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public List<Availability> makeAvailability(HealthcareProfessional professional, LocalDate startDate, LocalDate endDate,
            RecurrenceFrequency frequency, Set<DayOfWeek> daysOfWeek, Integer dayOfMonth,
            LocalTime startTime, LocalTime endTime) {

        List<Availability> created = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            boolean matchesPattern = frequency == RecurrenceFrequency.WEEKLY
                    ? daysOfWeek.contains(date.getDayOfWeek())
                    : date.getDayOfMonth() == dayOfMonth;
            if (matchesPattern) {
                Availability availability = new Availability(professional, date, startTime, endTime);
                availabilityRepository.save(availability);
                created.add(availability);
            }
        }
        return created;
    }

    public List<Availability> searchAvailability(String professionalId, String specialtyName, LocalDate fromDate, LocalDate toDate) {
        return availabilityRepository.findAll().stream()
                .filter(a -> a.getStatus() == AvailabilityStatus.AVAILABLE)
                .filter(a -> professionalId == null || a.getProfessional().getId().equals(professionalId))
                .filter(a -> specialtyName == null || a.getProfessional().getSpecialty().getName().equalsIgnoreCase(specialtyName))
                .filter(a -> fromDate == null || !a.getDate().isBefore(fromDate))
                .filter(a -> toDate == null || !a.getDate().isAfter(toDate))
                .toList();
    }
}
