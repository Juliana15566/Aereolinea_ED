package Modelo;

public class Accion {// se crean las variables para hacer referencia a la accion
    
         // Pasajeros
        public static final int agregarPasajero = 1;
        public static final int retirarPasajero = 2;

        // Reservas
        public static final int agregarReserva = 3;
        public static final int retirarReserva = 5;
        public static final int cancelarReserva = 4;
        public static final int retirarCancelacionReserva = 5;

        // Lista de espera (cola)
        public static final int agregarListaEspera = 6;
        public static final int sacarDeListaEspera = 7;
        public static final int volverAgregarListaEspera = 8;

        // Vuelos
        public static final int agregarVuelo = 9;
        public static final int retirarVuelo = 10;
    
}
