package Vista;

import java.util.List;
import java.util.Scanner;

import Controlador.GestorPasajero;
import Controlador.GestorReserva;
import Controlador.GestorVuelo;
import Modelo.Historial;
import Modelo.Registro;

public class GestionHistorico {

    
    private static Historial historial = new Historial();

    private static Scanner scanner = new Scanner(System.in);

    public GestionHistorico (Historial h) {
        this.historial = h;
    }

    private static int leerOpcion(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ HISTORIAL ---");
            System.out.println("1. Mostrar histórico sin registrar los deshacer");
            System.out.println("2. Mostrar log completo");
            System.out.println("3. Volver al menú principal");
            System.out.println("0. Salir");

            
            System.out.print("\nIngrese opción: ");
            opcion = leerOpcion("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    mostrarHistorico();
                    break;
                case 2:
                    mostrarLog();
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    return; // sale del menú de historial
                case 0:
                    System.out.println("Saliendo del programa...");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }

        } while (true);
    }

    private void mostrarHistorico() {
        System.out.println("\n--- Histórico retirando el deshacer ---");

        List<Registro> pila = historial.obtenerHistorico();

        if (pila.isEmpty()) {
            System.out.println("No hay acciones registradas.");
            return;
        }

        for (Registro r : pila) {  //Mostrar la lista por separado       
            System.out.println("Acción: " + r.tipo + " | Objeto: " + r.objeto + (r.posicion >= 0 ? " | Posición: " + r.posicion : ""));
            
        }
    }

    private void mostrarLog() {
        System.out.println("\n--- Log completo ---");

        List<String> log = historial.getLog();

        if (log.isEmpty()) {
            System.out.println("El log está vacío.");
            return;
        }

        for (String entrada : log) {
            System.out.println(entrada);
        }
    }
}
