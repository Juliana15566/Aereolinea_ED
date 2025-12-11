package Vista;

import java.util.Scanner;

import Modelo.Accion;
import Modelo.Historial;
import Modelo.Pasajero;
import Modelo.Registro;
import Modelo.Reserva;
import Modelo.Vuelo;
import Controlador.GestorPasajero;
import Controlador.GestorReserva;
import Controlador.GestorVuelo;

public class MenuReserva {

    private GestorPasajero gestorPasajero;
    private  GestorVuelo gestorVuelo;
    private  GestorReserva gestorReserva;
    private  Historial historial;

    private static Scanner scanner = new Scanner(System.in);

    public MenuReserva (GestorPasajero gp, GestorVuelo gv, GestorReserva gr, Historial h) {
        this.gestorPasajero = gp;
        this.gestorVuelo = gv;
        this.gestorReserva = gr;
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

    public void menuReservas() {
        int opcion;
        do {
            System.out.println("\nGESTIÓN DE RESERVAS");
            System.out.println("1. Crear Reserva");
            System.out.println("2. Cancelar Reserva");
            System.out.println("3. Deshacer");
            System.out.println("4. Listar Todas las Reservas");
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
                    deshacerAccionReserva();
                    break;
                case 4:
                    listarReservas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private void crearReserva() {
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

        Reserva reserva = new Reserva(pasajero, vuelo, 1);
        boolean exito = gestorReserva.agregarReserva(pasajero, vuelo, 1);
        if (exito) {
            historial.registrar(new Registro(Accion.agregarReserva, reserva));
        } else {
            historial.registrar(new Registro(Accion.agregarListaEspera, reserva));
        }
    }
    private void cancelarReserva() {
        System.out.println("\n--- cancelar reserva ---");
        System.out.print("Código de la reserva: ");
        String codigoReserva = scanner.nextLine();

        Reserva reserva = gestorReserva.buscarReservaPorCodigo(codigoReserva);
        boolean exito = gestorReserva.cancelarReserva(codigoReserva);
        
        if (exito) {
            historial.registrar(new Registro(Accion.cancelarReserva, reserva));
        }
    }
    private  void deshacerAccionReserva() {
        System.out.println("\n--- DESHACER ACCION EN RESERVA ---");
        gestorReserva.deshacer();
    }
    private void listarReservas() {
        System.out.println("\n--- reservas ---");
        gestorReserva.listarReservas();
    }
    
}