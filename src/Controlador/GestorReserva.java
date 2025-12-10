package Controlador;

import Modelo.Pasajero;
import Modelo.Reserva;
import Modelo.Vuelo;
import java.util.ArrayList;
import java.util.List;

public class GestorReserva {

    private List<Reserva> listaReservas = new ArrayList<>();


    public boolean agregarReserva(Pasajero pasajero, Vuelo vuelo, int estado) {

        
        for (Reserva r : listaReservas) { 
            if (r.getPasajero().getpasaporte().equals(pasajero.getpasaporte())
                    && r.getVuelo().getCodigo().equalsIgnoreCase(vuelo.getCodigo())) { // verificar si el pasajero ya tiene una reserva en vuelo

                System.out.println("El pasajero ya tiene una reserva en este vuelo.");
                return false;
            }
        }
        
        if (vuelo.hayCupo()) {// Si el vuelo cuenta con capacidad

            vuelo.agregarPasajero(pasajero);  // agrega a la lista confirmada
            Reserva reserva = new Reserva(pasajero, vuelo, estado);

            listaReservas.add(reserva);

            System.out.println("Reserva realizada con código: " + reserva.getCodReserva());
            return true;

        } else {//Si el vuelo lleno su cupo enviarlo a la lista de espera            
            vuelo.agregarALaListaDeEspera(pasajero);
            System.out.println("No hay cupo en el vuelo, se agrega a la lista de espera");
            return false;
        }
    }

    
    public boolean cancelarReserva(String codigoReserva) {

        for (Reserva r : listaReservas) {
            if (r.getCodReserva().equalsIgnoreCase(codigoReserva)) {

                Vuelo vuelo = r.getVuelo();
                Pasajero pasajero = r.getPasajero();

                
                r.setEstado(0); // Ponerlo como cancelado

                
                vuelo.getListaPasajeros().remove(pasajero);// Quitar de la lista de pasajeros

                System.out.println("Reserva cancelada");

                
                Pasajero enEspera = vuelo.sacarDeListaEspera(); // Traer al siguiente en la lista de espera

                if (enEspera != null) {
                    System.out.println("Asignando cupo al pasajero en espera: " + enEspera.getNombre());
                    agregarReserva(enEspera, vuelo, 1);
                }

                return true;
            }
        }

        System.out.println("No existe el código de la reserva");
        return false;
    }

    public void listarReservas() {
        if (listaReservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }

        for (Reserva r : listaReservas) {
            System.out.println(r);
        }
    }

}