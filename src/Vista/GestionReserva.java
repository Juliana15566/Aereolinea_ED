package Vista;

import java.util.Scanner;

import Modelo.Historial;
import Modelo.Pasajero;
import Modelo.Vuelo;
import Controlador.GestorPasajero;
import Controlador.GestorReserva;
import Controlador.GestorVuelo;

public class GestionReserva {

    private static GestorPasajero gestorPasajero = new GestorPasajero();
    private static GestorVuelo gestorVuelo = new GestorVuelo();
    private static GestorReserva gestorReserva = new GestorReserva();
    private static Historial historial = new Historial();

    private static Scanner scanner = new Scanner(System.in);

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

    public static void menuReservas() {
        int opcion;
        do {
            System.out.println("\nGESTIÓN DE RESERVAS");
            System.out.println("1. Crear Reserva");
            System.out.println("2. Cancelar Reserva");
            System.out.println("3. Listar Todas las Reservas");
            System.out.println("0. Volver al Menú Principal");

            opcion = leerOpcion("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    crearReserva();
                    break;
                case 2:
                    cancelarReserva();
                    break;
                case 3:
                    listarReservas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private static void crearReserva() {
        System.out.println("\n--- crear reserva ---");
        System.out.print("Pasaporte del pasajero: ");
        String pasaporte = scanner.nextLine();

        Pasajero pasajero = gestorPasajero.buscarPorPasaporte(pasaporte);
        if (pasajero == null) {
            System.out.println("No existe un pasajero con ese pasaporte.");
            return;
        }

        System.out.print("Código del vuelo: ");
        String codigoVuelo = scanner.nextLine();

        Vuelo vuelo = gestorVuelo.buscarPorCodigo(codigoVuelo);
        if (vuelo == null) {
            System.out.println("No existe un vuelo con ese código.");
            return;
        }

        boolean exito = gestorReserva.agregarReserva(pasajero, vuelo, 1);
        if (exito) {
            historial.registrar("Reserva creada: " + pasajero.getNombre() + " en vuelo " + vuelo.getCodigo());
        } else {
            historial.registrar("Pasajero en lista de espera: " + pasajero.getNombre() + " para vuelo " + vuelo.getCodigo());
        }
    }
    private static void cancelarReserva() {
        System.out.println("\n--- cancelar reserva ---");
        System.out.print("Código de la reserva: ");
        String codigoReserva = scanner.nextLine();

        boolean exito = gestorReserva.cancelarReserva(codigoReserva);
        if (exito) {
            historial.registrar("Reserva cancelada: " + codigoReserva);
        }
    }
    private static void listarReservas() {
        System.out.println("\n--- reservas ---");
        gestorReserva.listarReservas();
    }
    
}