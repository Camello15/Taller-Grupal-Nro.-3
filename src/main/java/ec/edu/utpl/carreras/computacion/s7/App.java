package ec.edu.utpl.carreras.computacion.s7;

import ec.edu.utpl.carreras.computacion.s7.model.ClimateSummary;
import ec.edu.utpl.carreras.computacion.s7.tasks.TaskSummarize;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Clase principal que usa hilos para calcular el promedio climático de un archivo.
 */
public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Crear un pool de un solo hilo para ejecutar tareas concurrentes
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Crear una tarea que calcula los promedios a partir del archivo CSV
        var task = new TaskSummarize("C:\\Users\\utpl\\Downloads\\weather\\weatherHistory.csv");

        // Enviar la tarea al pool y obtener un Future para recuperar el resultado más tarde
        Future<ClimateSummary> future = executorService.submit(task);

        // Obtener el resultado de la tarea cuando esté listo
        var result = future.get();

        // Imprimir el resumen climático (promedios de temperatura, humedad, etc.)
        System.out.println(result);

        // Cerrar el pool de hilos
        executorService.shutdown();
    }
}
