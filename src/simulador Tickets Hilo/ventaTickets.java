package simuladorTickets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ventaTickets extends Thread {
    // Nombre del archivo que almacena el número de entradas disponibles

    private static final String DIRECCION = "C:\\Users\\Carlos\\Documents\\NetBeansProjects\\EjerciciosHiloIA\\src\\simuladorTickets\\ventas.txt";

    public static void main(String[] args) {
        // Crea 5 hilos que representan a los clientes
        for (int i = 0; i < 50; i++) {

            Thread t = new Thread(() -> {
                // Intenta comprar una entrada
                comparTicket();
            });
            // Inicia el hilo
            t.start();
        }
    }
    private static final Object bloquear = new Object();

// Método que simula la compra de una entrada
    private static void comparTicket() {
        // Abre el archivo para leer el número de entradas disponibles
        int tickets;
        synchronized (bloquear) {
            try (BufferedReader br = new BufferedReader(new FileReader(DIRECCION))) {
                tickets = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                System.out.println("Error en: " + e.getMessage());
                return;
            }
        }
        // Si hay entradas disponibles, se procede a la compra
        if (tickets > 0) {
            synchronized (bloquear) {
                // Abre el archivo para escribir el nuevo número de entradas disponibles
                try (BufferedWriter bf = new BufferedWriter(new FileWriter(DIRECCION))) {
                    tickets--;
                    bf.write(String.valueOf(tickets));
                } catch (IOException e) {
                    System.out.println("Error en: " + e.getMessage());
                    return;
                }
            }
            System.out.println("Entrada comprada. Quedan " + tickets + " entradas disponibles.");
        } else {
            System.out.println("Lo siento, no quedan entradas disponibles.");
        }
    }
}
