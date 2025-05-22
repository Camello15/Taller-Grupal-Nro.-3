package ec.edu.utpl.carreras.computacion.s7;

import ec.edu.utpl.carreras.computacion.s7.model.ClimateSummary;
import ec.edu.utpl.carreras.computacion.s7.model.ClimateRecord;
import ec.edu.utpl.carreras.computacion.s7.tasks.TaskSummarizeByYear;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileReader;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

public class App {
    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\utpl\\Downloads\\weather\\weatherHistory.csv"; // Cambiar por la ruta real

        Map<Integer, List<ClimateRecord>> recordsByYear = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z");

        try (CSVParser parser = CSVParser.parse(
                new FileReader(path),
                CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build())) {

            for (var record : parser) {
                LocalDateTime dateTime = LocalDateTime.parse(record.get("Formatted Date"), formatter);
                int year = dateTime.getYear();

                ClimateRecord climateRecord = new ClimateRecord(
                        dateTime,
                        Double.parseDouble(record.get("Temperature (C)")),
                        Double.parseDouble(record.get("Humidity")),
                        Double.parseDouble(record.get("Wind Speed (km/h)")),
                        Double.parseDouble(record.get("Visibility (km)")),
                        Double.parseDouble(record.get("Pressure (millibars)"))
                );

                recordsByYear.computeIfAbsent(year, k -> new ArrayList<>()).add(climateRecord);
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<ClimateSummary>> results = new ArrayList<>();

        for (var entry : recordsByYear.entrySet()) {
            int year = entry.getKey();
            List<ClimateRecord> records = entry.getValue();
            results.add(executor.submit(new TaskSummarizeByYear(year, records)));
        }

        for (Future<ClimateSummary> future : results) {
            ClimateSummary summary = future.get();
            System.out.println(summary);
        }

        executor.shutdown();
    }
}
