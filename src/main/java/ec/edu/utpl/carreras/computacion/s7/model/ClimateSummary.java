package ec.edu.utpl.carreras.computacion.s7.model;

/**
 * Se agregaron campos para guardar las fechas y horas de los valores extremos:
 * - Mayor y menor temperatura
 * - Mayor y menor humedad
 * - Mayor y menor velocidad del viento
 * - Mayor y menor visibilidad
 *
 * Esto permite mostrar cuándo ocurrieron estos eventos sin volver a recorrer los datos.
 */
public record ClimateSummary(
        double tempAvg, double humAvg, double windSpAvg, double visibilityAvg, double pressureAvg,
        String hottestTime, String coldestTime,
        String highestHumidityTime, String lowestHumidityTime,
        String highestWindTime, String lowestWindTime,
        String highestVisibilityTime, String lowestVisibilityTime
) {
}
