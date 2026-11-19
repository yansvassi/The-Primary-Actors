# SOEN 342 – Team "The-Primary-Actors"

Fall 2026 · Concordia University

## Team

| Name | Student ID | Role |
|------|-----------|------|
| Yana Vassilyev | 40270779 |  Team Leader  |
| Mia Matsumoto-Depatie | 40314818 | |
| Paulina Aguayo Dupin | 40299470 ||

## Project

Clinic appointment system, Iteration II proof of concept: booking, availability search, making availability, and CSV import/export. Data is held in memory only.

## Running the prototype

Requires JDK 17 or newer.

```bash
cd clinic-appointment
javac -d out Main.java domain/*.java repository/*.java service/*.java
java -cp out Main
```

Run from `clinic-appointment/` so the relative paths to `availabilities.csv` and the export file resolve.
