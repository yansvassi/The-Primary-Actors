package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import domain.Appointment;
import domain.HealthcareProfessional;
import domain.MedicalSpecialty;
import domain.Patient;
import repository.HealthcareProfessionalRepository;

public class AppointmentsCSV {
    public static List<Appointment> importCSV(String filePath, HealthcareProfessionalRepository professionalRepository) throws IOException, DateTimeParseException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<Appointment> appointments = new ArrayList<Appointment>();
        String line;

        while((line = reader.readLine()) != null){
            if(line.trim().isEmpty()) {
                continue;
            }
            String[] values = line.split(",");

            String professionalName = values[0].trim();
            MedicalSpecialty professionalSpecialty = new MedicalSpecialty(values[1].trim());

            HealthcareProfessional professional = professionalRepository
                .findByNameAndSpecialty(professionalName, professionalSpecialty.getName())
                .orElse(null);

            // Ignore this appointment
            if(professional == null) {
                continue;
            }
            
            LocalDate date = LocalDate.parse(values[2].trim());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
            LocalTime startTime = LocalTime.parse(values[3].trim(), formatter);
            LocalTime endTime = LocalTime.parse(values[4].trim(), formatter);
            
            String patientFirstName = values[5].trim();
            String patientLastName = values[6].trim();
            String phoneNumber = values[7].trim();
            String ramqNum  = "";

            if(values.length < 9) {
                ramqNum = null;
            }
            else {
                ramqNum = values[8].trim();
            }

            Patient patient = new Patient(patientFirstName, patientLastName, phoneNumber, ramqNum);

            Appointment appointment = new Appointment(professional, patient, date, startTime, endTime);
            appointments.add(appointment);
        }

        reader.close();
        return appointments;
    }

    // The filePath should include the .csv extension when passing as a parameter
    public static void exportCSV(String filePath, List<Appointment> appointments) throws IOException {
        FileWriter writer = new FileWriter(filePath);

        for(Appointment appointment: appointments) {
            writer.write(appointment + "\n");
        }
        writer.close();
    }
    
}
