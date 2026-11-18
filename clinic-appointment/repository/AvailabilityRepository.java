package repository;

import domain.Availability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AvailabilityRepository {

    private final Map<String, Availability> availabilitiesById = new HashMap<>();

    public Availability save(Availability availability) {
        availabilitiesById.put(availability.getId(), availability);
        return availability;
    }

    public Optional<Availability> findById(String id) {
        return Optional.ofNullable(availabilitiesById.get(id));
    }

    public List<Availability> findAll() {
        return List.copyOf(availabilitiesById.values());
    }
}
