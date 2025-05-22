package ec.edu.utpl.carreras.computacion.s7;

import ec.edu.utpl.carreras.computacion.s7.model.ClimateSummary;
import ec.edu.utpl.carreras.computacion.s7.tasks.TaskSummarize;

import java.util.concurrent.*;

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Pool de hilos (puede ampliarse si se procesan más archivos)
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Ejecutar la tarea
        Future<ClimateSummary> future = executor.submit(
                new TaskSummarize("C:\\Users\\utpl\\Downloads\\weather\\weatherHistory.csv"));

        // Obtener los resultados cuando estén listos
        ClimateSummary result = future.get();

        // Mostrar promedios
        System.out.println("PROMEDIOS:");
        System.out.printf("Temperatura: %.2f°C, Humedad: %.2f%%, Viento: %.2f km/h, Visibilidad: %.2f km, Presión: %.2f mb\n",
                result.tempAvg(), result.humAvg(), result.windSpAvg(), result.visibilityAvg(), result.pressureAvg());

        // Mostrar extremos con su fecha/hora
        System.out.println("\nEXTREMOS:");
        System.out.println("Día más caluroso: " + result.hottestTime());
        System.out.println("Día más frío: " + result.coldestTime());
        System.out.println("Mayor humedad: " + result.highestHumidityTime());
        System.out.println("Menor humedad: " + result.lowestHumidityTime());
        System.out.println("Mayor velocidad del viento: " + result.highestWindTime());
        System.out.println("Menor velocidad del viento: " + result.lowestWindTime());
        System.out.println("Mayor visibilidad: " + result.highestVisibilityTime());
        System.out.println("Menor visibilidad: " + result.lowestVisibilityTime());

        executor.shutdown();
    }
}
