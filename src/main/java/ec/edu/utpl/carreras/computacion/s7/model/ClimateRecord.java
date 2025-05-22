package ec.edu.utpl.carreras.computacion.s7.model;

import java.time.LocalDateTime;

public record ClimateRecord(
        LocalDateTime dateTime,
        double temp,
        double humidity,
        double windSpeed,
        double visibility,
        double pressure
) {}
