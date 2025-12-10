//import Modelo.Historial;
import Vista.GestionReserva;
import Vista.GestionPasajero;
import Vista.GestionVuelo;
import Vista.GestionHistorico;
import java.util.Scanner;

public class Main {

    private static GestionPasajero gestionPasajero = new GestionPasajero();
    private static GestionVuelo gestionVuelo = new GestionVuelo();
    private static GestionReserva gestionReserva = new GestionReserva();
    private static GestionHistorico gestionHistorico = new GestionHistorico();

    //private static Historial historial = new Historial();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    menuPasajeros();
                    break;
                case 2:
                    menuVuelos();
                    break;
                case 3:
                    menuReservas();
                    break;
                case 4:
                    menuHistorial();
                    break;
                default:
                    System.out.println("Intente nuevamente.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n\nSISTEMA DE GESTIÓN DE AEROLÍNEA");
        System.out.println("1. Gestión de Pasajeros");
        System.out.println("2. Gestión de Vuelos");
        System.out.println("3. Gestión de Reservas");
        System.out.println("4. Historial");
        System.out.println("0. Salir");
    }

    private static void menuPasajeros() {
        gestionPasajero.menuPasajeros();
        mostrarMenuPrincipal();
    }

    private static void menuVuelos() {
        gestionVuelo.menuVuelos();
        mostrarMenuPrincipal();
    }

    private static void menuReservas() {
        gestionReserva.menuReservas();
        mostrarMenuPrincipal();
    }


    private static void menuHistorial() {
        gestionHistorico.mostrarMenu();
        mostrarMenuPrincipal();
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
}