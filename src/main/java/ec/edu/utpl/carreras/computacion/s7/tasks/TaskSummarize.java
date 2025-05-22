package ec.edu.utpl.carreras.computacion.s7.tasks;

import ec.edu.utpl.carreras.computacion.s7.model.ClimateRecord;
import ec.edu.utpl.carreras.computacion.s7.model.ClimateSummary;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class TaskSummarize implements Callable<ClimateSummary> {
    private final String path2Data;

    public TaskSummarize(String path2Data) {
        this.path2Data = path2Data;
    }

    @Override
    public ClimateSummary call() throws IOException {
        // Variables acumuladoras para promedios
        double sumTemp = 0, sumHum = 0, sumWind = 0, sumVis = 0, sumPress = 0;
        int count = 0;

        // Variables para extremos (máximos y mínimos)
        double maxTemp = Double.NEGATIVE_INFINITY, minTemp = Double.POSITIVE_INFINITY;
        double maxHum = Double.NEGATIVE_INFINITY, minHum = Double.POSITIVE_INFINITY;
        double maxWind = Double.NEGATIVE_INFINITY, minWind = Double.POSITIVE_INFINITY;
        double maxVis = Double.NEGATIVE_INFINITY, minVis = Double.POSITIVE_INFINITY;

        // Variables para guardar la fecha/hora en que ocurrieron los extremos
        String hottestTime = "", coldestTime = "";
        String highestHumidityTime = "", lowestHumidityTime = "";
        String highestWindTime = "", lowestWindTime = "";
        String highestVisibilityTime = "", lowestVisibilityTime = "";

        // Lectura del CSV
        var csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        try (Reader reader = Files.newBufferedReader(Paths.get(path2Data));
             CSVParser parser = CSVParser.parse(reader, csvFormat)) {

            for (var record : parser) {
                // Leer los datos del CSV
                String dateTime = record.get("Formatted Date");
                double temp = Double.parseDouble(record.get("Temperature (C)"));
                double hum = Double.parseDouble(record.get("Humidity"));
                double wind = Double.parseDouble(record.get("Wind Speed (km/h)"));
                double vis = Double.parseDouble(record.get("Visibility (km)"));
                double press = Double.parseDouble(record.get("Pressure (millibars)"));

                // Acumular para promedios
                sumTemp += temp;
                sumHum += hum;
                sumWind += wind;
                sumVis += vis;
                sumPress += press;
                count++;

                // Evaluar extremos y guardar la fecha/hora
                if (temp > maxTemp) { maxTemp = temp; hottestTime = dateTime; }
                if (temp < minTemp) { minTemp = temp; coldestTime = dateTime; }

                if (hum > maxHum) { maxHum = hum; highestHumidityTime = dateTime; }
                if (hum < minHum) { minHum = hum; lowestHumidityTime = dateTime; }

                if (wind > maxWind) { maxWind = wind; highestWindTime = dateTime; }
                if (wind < minWind) { minWind = wind; lowestWindTime = dateTime; }

                if (vis > maxVis) { maxVis = vis; highestVisibilityTime = dateTime; }
                if (vis < minVis) { minVis = vis; lowestVisibilityTime = dateTime; }
            }
        }

        // Retornar todos los resultados en un solo objeto (promedios + extremos)
        return new ClimateSummary(
                sumTemp / count,
                sumHum / count,
                sumWind / count,
                sumVis / count,
                sumPress / count,
                hottestTime, coldestTime,
                highestHumidityTime, lowestHumidityTime,
                highestWindTime, lowestWindTime,
                highestVisibilityTime, lowestVisibilityTime
        );
    }
}
