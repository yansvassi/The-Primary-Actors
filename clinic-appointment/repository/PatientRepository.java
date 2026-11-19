package repository;

import domain.Patient;

import java.util.HashMap;
import java.util.Map;

public class PatientRepository {

    private final Map<String, Patient> patientsById = new HashMap<>();

    public Patient save(Patient patient) {
        patientsById.put(patient.getId(), patient);
        return patient;
    }
}
