package ec.edu.utpl.carreras.computacion.s7.model;

//se añade fecha y hora
public record ClimateRecord(String datetime, double temp, double humidity, double windSpeed, double visibility, double pressure) {
}
