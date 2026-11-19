package domain;

public class HealthcareProfessional extends ClinicStaff {

    private final String title;
    private final MedicalSpecialty specialty;

    public HealthcareProfessional(String name, String title, MedicalSpecialty specialty) {
        super(name);
        this.title = title;
        this.specialty = specialty;
    }

    public String getTitle() {
        return title;
    }

    public MedicalSpecialty getSpecialty() {
        return specialty;
    }
}
