package ec.edu.utpl.carreras.computacion.s7.tasks;

import ec.edu.utpl.carreras.computacion.s7.model.ClimateRecord;
import ec.edu.utpl.carreras.computacion.s7.model.ClimateSummary;

import java.util.List;
import java.util.concurrent.Callable;

public class TaskSummarizeByYear implements Callable<ClimateSummary> {
    private final int year;
    private final List<ClimateRecord> records;

    public TaskSummarizeByYear(int year, List<ClimateRecord> records) {
        this.year = year;
        this.records = records;
    }

    @Override
    public ClimateSummary call() {
        double sumTemp = 0, sumHum = 0, sumWind = 0, sumVis = 0, sumPress = 0;
        int count = records.size();

        ClimateRecord maxTempR = null, minTempR = null;
        ClimateRecord maxHumR = null, minHumR = null;
        ClimateRecord maxWindR = null, minWindR = null;
        ClimateRecord maxVisR = null, minVisR = null;

        for (var r : records) {
            sumTemp += r.temp();
            sumHum += r.humidity();
            sumWind += r.windSpeed();
            sumVis += r.visibility();
            sumPress += r.pressure();

            if (maxTempR == null || r.temp() > maxTempR.temp()) maxTempR = r;
            if (minTempR == null || r.temp() < minTempR.temp()) minTempR = r;

            if (maxHumR == null || r.humidity() > maxHumR.humidity()) maxHumR = r;
            if (minHumR == null || r.humidity() < minHumR.humidity()) minHumR = r;

            if (maxWindR == null || r.windSpeed() > maxWindR.windSpeed()) maxWindR = r;
            if (minWindR == null || r.windSpeed() < minWindR.windSpeed()) minWindR = r;

            if (maxVisR == null || r.visibility() > maxVisR.visibility()) maxVisR = r;
            if (minVisR == null || r.visibility() < minVisR.visibility()) minVisR = r;
        }

        return new ClimateSummary(
                year,
                sumTemp / count,
                sumHum / count,
                sumWind / count,
                sumVis / count,
                sumPress / count,
                maxTempR.dateTime().toString(),
                minTempR.dateTime().toString(),
                maxHumR.dateTime().toString(),
                minHumR.dateTime().toString(),
                maxWindR.dateTime().toString(),
                minWindR.dateTime().toString(),
                maxVisR.dateTime().toString(),
                minVisR.dateTime().toString()
        );
    }
}
