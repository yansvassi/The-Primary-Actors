import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import domain.Availability;
import domain.HealthcareProfessional;
import domain.MedicalSpecialty;

public class AvailabilitiesCSV {

    public static List<Availability> importCSV(String filePath) throws IOException, DateTimeParseException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<Availability> availabilities = new ArrayList<Availability>();
        String line;

        while((line = reader.readLine()) != null){
            String[] values = line.split(",");
            String name = values[0].trim();
            String title = values[1].trim();
            MedicalSpecialty specialty = new MedicalSpecialty(values[2].trim());

            LocalDate date = LocalDate.parse(values[3].trim());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
            LocalTime startTime = LocalTime.parse(values[4].trim(), formatter);
            LocalTime endTime = LocalTime.parse(values[5].trim(), formatter);

            HealthcareProfessional professional = new HealthcareProfessional(name, title, specialty);
            Availability availability = new Availability(professional, date, startTime, endTime);
            availabilities.add(availability);
        }

        reader.close();
        return availabilities;
    }
    
}
