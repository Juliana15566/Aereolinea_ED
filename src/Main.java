import Modelo.Historial;
import Vista.GestionReserva;
import Vista.GestionPasajero;
import Vista.GestionVuelo;
import Vista.GestionHistorico;
import Controlador.GestorPasajero;
import Controlador.GestorReserva;
import Controlador.GestorVuelo;
import java.util.Scanner;

public class Main {

    private Historial historial;
    private GestorPasajero gestorPasajero;
    private GestorVuelo gestorVuelo;
    private GestorReserva gestorReserva;

    private GestionPasajero gestionPasajero;
    private GestionVuelo gestionVuelo;
    private GestionReserva gestionReserva;
    private GestionHistorico gestionHistorico;

    private Scanner scanner = new Scanner(System.in);

    // constructor: inicializa gestores y vistas
    public Main() {
        historial = new Historial();
        gestorPasajero = new GestorPasajero(historial);
        gestorVuelo = new GestorVuelo(historial);
        gestorReserva = new GestorReserva(historial);

        gestionPasajero = new GestionPasajero(gestorPasajero, historial);
        gestionVuelo = new GestionVuelo(gestorVuelo, historial);
        gestionReserva = new GestionReserva(gestorPasajero, gestorVuelo, gestorReserva, historial);
        gestionHistorico = new GestionHistorico(historial);
    }

    public void menu() {
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
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Intente nuevamente.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.menu();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n\nSISTEMA DE GESTIÓN DE AEROLÍNEA");
        System.out.println("1. Gestión de Pasajeros");
        System.out.println("2. Gestión de Vuelos");
        System.out.println("3. Gestión de Reservas");
        System.out.println("4. Historial");
        System.out.println("0. Salir");
    }

    private  void menuPasajeros() {
        gestionPasajero.menuPasajeros();
        mostrarMenuPrincipal();
    }

    private  void menuVuelos() {
        gestionVuelo.menuVuelos();
        mostrarMenuPrincipal();
    }

    private  void menuReservas() {
        gestionReserva.menuReservas();
        mostrarMenuPrincipal();
    }


    private  void menuHistorial() {
        gestionHistorico.mostrarMenu();
        mostrarMenuPrincipal();
    }

    private  int leerOpcion(String mensaje) {
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