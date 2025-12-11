package Vista;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


import Controlador.GestorVuelo;
import Modelo.Vuelo;

public class MenuVuelo {


    private  GestorVuelo gestorVuelo;

    private static Scanner scanner = new Scanner(System.in);

    public MenuVuelo (GestorVuelo gv) {
        this.gestorVuelo = gv;
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

    public  void menuVuelos() {
        int opcion;
        do {
            System.out.println("\n\nGESTIÓN DE VUELOS");
            System.out.println("1. Agregar Vuelo");
            System.out.println("2. Deshacer");
            System.out.println("3. Buscar Vuelo por Código");
            System.out.println("4. Buscar Vuelos por Origen");
            System.out.println("5. Buscar Vuelos por Destino");
            System.out.println("6. Buscar Vuelos por Fecha de Salida");
            System.out.println("7. Mostrar Todos los Vuelos");
            System.out.println("0. Volver al Menú Principal");

            opcion = leerOpcion("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    agregarVuelo();
                    break;
                case 2:
                    retirarVuelo();
                    break;
                case 3:
                    buscarVueloPorCodigo();
                    break;
                case 4:
                    buscarVuelosPorOrigen();
                    break;
                case 5:
                    buscarVuelosPorDestino();
                    break;
                case 6:
                    buscarVuelosPorFecha();
                    break;
                case 7:
                    mostrarTodosLosVuelos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private  void agregarVuelo() {
        System.out.println("\n--- agregar vuelo ---");
        System.out.print("Código del vuelo: ");
        String codigo = scanner.nextLine();
        System.out.print("Origen: ");
        String origen = scanner.nextLine();
        System.out.print("Destino: ");
        String destino = scanner.nextLine();
        int limite = leerOpcion("Límite de pasajeros: ");
        System.out.print("Horario de salida (formato militar 14:30 o 08:15) ");
        String horario = scanner.nextLine();
        System.out.print("Fecha de salida (YYYY-MM-DD: ");
        String fecha = scanner.nextLine();

        try {
            Vuelo vuelo = new Vuelo(codigo, origen, destino, limite, horario, fecha);
            gestorVuelo.agregarVuelo(vuelo);
            //historial.registrar(new Registro(Accion.agregarVuelo, vuelo));
        } catch (IllegalArgumentException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
    private  void retirarVuelo() {
        System.out.println("\n--- DESHACER REGISTRO VUELO ---");
        gestorVuelo.retirarVuelo();
    }

    private  void buscarVueloPorCodigo() {
        System.out.println("\n--- BUSCAR VUELO POR CÓDIGO ---");
        System.out.print("Ingrese el código del vuelo: ");
        String codigo = scanner.nextLine();

        Vuelo vuelo = gestorVuelo.buscarPorCodigo_recursivo(codigo,0);
        if (vuelo != null) {
            System.out.println("Vuelo encontrado:");
            System.out.println(vuelo);
            System.out.println("Espacios disponibles: " + vuelo.espaciosDisponibles());
        } else {
            System.out.println("No se encontró ningún vuelo con ese código.");
        }
    }

    private  void buscarVuelosPorOrigen() {
        System.out.println("\n--- buscar vuelos por origen ---");
        System.out.print("Ingrese el origen: ");
        String origen = scanner.nextLine();

        List<Vuelo> vuelos = gestorVuelo.buscarPorOrigen(origen);
        mostrarListaVuelos(vuelos, "origen " + origen);
    }

    private  void buscarVuelosPorDestino() {
        System.out.println("\n--- buscar vuelos por destino---");
        System.out.print("Ingrese el destino: ");
        String destino = scanner.nextLine();

        List<Vuelo> vuelos = gestorVuelo.buscarPorDestino(destino);
        mostrarListaVuelos(vuelos, "destino " + destino);
    }

    private  void buscarVuelosPorFecha() {
        System.out.println("\n--- buscar vuelos por fecha ---");
        System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
        String fechaTexto = scanner.nextLine();

        try {
            LocalDate fecha = LocalDate.parse(fechaTexto);
            List<Vuelo> vuelos = gestorVuelo.buscarPorFechaSalida(fecha);
            mostrarListaVuelos(vuelos, "fecha " + fecha);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use YYYY-MM-DD");
        }
    }

    private  void mostrarTodosLosVuelos() {
        System.out.println("\n--- todos los vuelos ---");
        gestorVuelo.mostrarTodos();
    }

    private static void mostrarListaVuelos(List<Vuelo> vuelos, String criterio) {
        if (vuelos.isEmpty()) {
            System.out.println("No se encontraron vuelos con " + criterio);
        } else {
            System.out.println("Se encontraron " + vuelos.size() + " vuelo(s):");
            for (Vuelo v : vuelos) {
                System.out.println("\n" + v);
                System.out.println("Espacios disponibles: " + v.espaciosDisponibles());
            }
        }
    }

}
