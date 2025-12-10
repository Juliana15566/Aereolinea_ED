package Vista;

import java.util.List;
import java.util.Scanner;

import Modelo.Accion;
import Modelo.Historial;
import Modelo.Pasajero;
import Modelo.Registro;
import Controlador.GestorPasajero;

public class GestionPasajero {

    private static GestorPasajero gestorPasajero = new GestorPasajero();
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

    public static void menuPasajeros() {
        int opcion;
        do {
            System.out.println("GESTIÓN DE PASAJEROS");
            System.out.println("1. Agregar Pasajero");
            System.out.println("2. Buscar Pasajero por Pasaporte");
            System.out.println("3. Buscar Pasajero por Nombre");
            System.out.println("4. Listar Todos los Pasajeros");
            System.out.println("0. Volver al Menú Principal");

            opcion = leerOpcion("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    agregarPasajero();
                    break;
                case 2:
                    buscarPasajeroPorPasaporte();
                    break;
                case 3:
                    buscarPasajeroPorNombre();
                    break;
                case 4:
                    listaPasajeros();
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }
    private static void agregarPasajero() {
        System.out.println("\n--- AGREGAR PASAJERO ---");
        System.out.print("Pasaporte: ");
        String pasaporte = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        Pasajero pasajero = new Pasajero(pasaporte, nombre);
        gestorPasajero.agregarPasajero(pasajero);
        historial.registrar(new Registro(Accion.agregarPasajero, pasajero));
    }

    private static void buscarPasajeroPorPasaporte() {
        System.out.println("\n--- buscar pasajero por pasaporte ---");
        System.out.print("Ingrese el pasaporte: ");
        String pasaporte = scanner.nextLine();

        Pasajero pasajero = gestorPasajero.buscarPorPasaporte_recursivo(pasaporte,0);
        if (pasajero != null) {
            System.out.println("Pasajero encontrado:");
            System.out.println(pasajero);
        } else {
            System.out.println("No se encontró ningún pasajero.");
        }
    }

    private static void buscarPasajeroPorNombre() {
        System.out.println("\n--- buscar pasajero por nombre ---");
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();

        Pasajero pasajero = (Pasajero) gestorPasajero.buscarPorNombre_recursivo(nombre, 0,null);
        if (pasajero != null) {
            System.out.println("Pasajero encontrado:");
            System.out.println(pasajero);
        } else {
            System.out.println("No se encontró ningún pasajero.");
        }
    }

    private static void listaPasajeros() {
        System.out.println("\n--- lista de pasajeros ---");
        List<Pasajero> pasajeros = gestorPasajero.getListaPasajeros();
        if (pasajeros.isEmpty()) {
            System.out.println("No hay pasajeros registrados.");
        } else {
            for (Pasajero p : pasajeros) {
                System.out.println("- " + p.getNombre() + " (Pasaporte: " + p.getpasaporte() + ")");
            }
            System.out.println("\nTotal de pasajeros: " + pasajeros.size());
        }
    }
}
