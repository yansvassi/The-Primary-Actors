package repository;

import domain.HealthcareProfessional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HealthcareProfessionalRepository {

    private final Map<String, HealthcareProfessional> professionalsById = new HashMap<>();

    public HealthcareProfessional save(HealthcareProfessional professional) {
        professionalsById.put(professional.getId(), professional);
        return professional;
    }

    public Optional<HealthcareProfessional> findByNameAndSpecialty(String fullName, String specialtyName) {
        return professionalsById.values().stream()
                .filter(p -> p.getName().equalsIgnoreCase(fullName)
                        && p.getSpecialty().getName().equalsIgnoreCase(specialtyName))
                .findFirst();
    }
}
 