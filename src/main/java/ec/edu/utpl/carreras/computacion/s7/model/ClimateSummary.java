package ec.edu.utpl.carreras.computacion.s7.model;

public record ClimateSummary(
        int year,
        double avgTemp,
        double avgHumidity,
        double avgWind,
        double avgVis,
        double avgPress,
        String hottestTime,
        String coldestTime,
        String highestHumidityTime,
        String lowestHumidityTime,
        String highestWindTime,
        String lowestWindTime,
        String highestVisTime,
        String lowestVisTime
) {
    @Override
    public String toString() {
        return String.format("Resumen %d:\nTemp: %.2f°C, Hum: %.2f%%, Viento: %.2f km/h, Vis: %.2f km, Pres: %.2f mb\n" +
                        "Más calor: %s | Más frío: %s\n" +
                        "Mayor Hum: %s | Menor Hum: %s\n" +
                        "Mayor Viento: %s | Menor Viento: %s\n" +
                        "Mayor Vis: %s | Menor Vis: %s\n",
                year, avgTemp, avgHumidity, avgWind, avgVis, avgPress,
                hottestTime, coldestTime,
                highestHumidityTime, lowestHumidityTime,
                highestWindTime, lowestWindTime,
                highestVisTime, lowestVisTime);
    }
}
