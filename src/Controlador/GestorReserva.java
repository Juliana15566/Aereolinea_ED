package Controlador;

import Modelo.Accion;
import Modelo.Historial;
import Modelo.Pasajero;
import Modelo.Registro;
import Modelo.Reserva;
import Modelo.Vuelo;
import java.util.ArrayList;
import java.util.List;

public class GestorReserva {

    private List<Reserva> listaReservas = new ArrayList<>();
    private Historial historial;

    public GestorReserva() { //Constructor vacio para la vista
        listaReservas = new ArrayList<>();
    }
    
    // Constructor: en el que requerimos la clase historial para los registros
    public GestorReserva(Historial historial) {
        this.historial = historial;
        listaReservas = new ArrayList<>();
    }

    public boolean agregarReserva(Pasajero pasajero, Vuelo vuelo, int estado) {

        // Verificar que el pasajero no tenga reserva en el vuelo
        if (existeReserva(pasajero, vuelo)) {
            System.out.println("El pasajero ya tiene una reserva en este vuelo.");
            return false;
        }

        Reserva reserva = new Reserva(pasajero, vuelo, 1);

        if (vuelo.hayCupo()) {
            agregarReservaConfirmada(reserva);
            System.out.println("Reserva agregada correctamente para " + pasajero.getNombre());
            return true;
        } else {
            agregarListaEspera(reserva);
            System.out.println("Vuelo lleno. Pasajero agregado a lista de espera: " + pasajero.getNombre());
            return false;
        }
    }

    public boolean cancelarReserva(String codigoReserva) {
        Reserva reservaCancelada = buscarReservaPorCodigo(codigoReserva);
        if (reservaCancelada == null || reservaCancelada.getEstado() == 0) { //No se encuentra la r4eserva o no esta activa
            System.out.println("No existe la reserva o ya está cancelada.");
            return false;
        }

        reservaCancelada.setEstado(0);//cambio el estado de la reserva
        reservaCancelada.getVuelo().getListaPasajeros().remove(reservaCancelada.getPasajero());//remuevo de la lista de pasajeros a quien cancela
        System.out.println("Reserva cancelada: " + codigoReserva);
        historial.registrar(new Registro(Accion.cancelarReserva, reservaCancelada)); //registro en el historico

        // Promover primer pasajero de lista de espera
        Reserva reservaPromovida = promoverListaEspera(reservaCancelada.getVuelo());
        if (reservaPromovida != null) {
            historial.registrar(new Registro(Accion.sacarDeListaEspera, reservaPromovida, 0));
        }

        return true;
    }

    public void deshacer() {
        if (!historial.hayAcciones()) { // Si hay acciones para deshacer
            System.out.println("No hay acciones para deshacer.");
            return;
        }

        Registro reg = historial.deshacer(); //deshacer la ultima accion registrada

        switch (reg.tipo) { //revisar cual fue la ultima accion que se realizo
            case Accion.agregarReserva:
                retirarReserva(reg.objeto);
                break;

            case Accion.cancelarReserva:
                volverAgregarListaEspera(reg.objeto);
                deshacerCancelarReserva(reg.objeto);
                break;
        }
    }

    private boolean existeReserva(Pasajero pasajero, Vuelo vuelo) {
        for (Reserva r : listaReservas) {
            
            //variables para buscar si existe el vuelo y pasajero asignado en una reserva
            boolean mismoPasajero = r.getPasajero().getpasaporte().equals(pasajero.getpasaporte());
            boolean mismoVuelo = r.getVuelo().equals(vuelo);
            boolean activa = r.getEstado() == 1;

            if (mismoPasajero && mismoVuelo && activa) {
                return true; // Ya existe una reserva activa
            }
        }
        return false; // No hay reserva activa para este pasajero y vuelo
    }
    
    private void agregarReservaConfirmada(Reserva reserva) {
        listaReservas.add(reserva);
        reserva.getVuelo().agregarPasajero(reserva.getPasajero()); //En el vuelo se le asiga un pasajero
        System.out.println("Reserva confirmada: " + reserva.getCodReserva());
        historial.registrar(new Registro(Accion.agregarReserva, reserva)); //Registrar al historial
    }

     private void agregarListaEspera(Reserva reserva) {
        listaReservas.add(reserva);
        reserva.getVuelo().agregarALaListaDeEspera(reserva.getPasajero());
        System.out.println("Vuelo lleno. Pasajero agregado a lista de espera: " + reserva.getPasajero().getNombre());
        historial.registrar(new Registro(Accion.agregarListaEspera, reserva));
    }

    public Reserva buscarReservaPorCodigo(String codigo) {
        for (Reserva r : listaReservas) {
            if (r.getCodReserva().equalsIgnoreCase(codigo)) { //Si la encuentra
                return r; 
            }
        }
        return null;
    }

    private Reserva promoverListaEspera(Vuelo vuelo) {
        Pasajero pasajero = vuelo.sacarDeListaEspera(); //Traer de la lista de espera al pasajero
        if (pasajero != null) { //Si hay personas en la lista de espera
            Reserva r = buscarReservaPorPasajeroYVuelo(pasajero, vuelo); //vuscar la reserva del vuelo y el pasajero
            if (r != null) { //Si existe la reserva del pasajero en el vuelo
                vuelo.agregarPasajero(pasajero); //Agregarlo a la lista de pasajeros
                System.out.println("Promovido de lista de espera a pasajero confirmado: " + pasajero.getNombre());
                return r;
            }
        }
        return null;
    }

    private Reserva buscarReservaPorPasajeroYVuelo(Pasajero p, Vuelo v) {
        for (Reserva r : listaReservas) {
            // Comprobar si la reserva corresponde a este pasajero y vuelo
            boolean mismoPasajero = r.getPasajero().equals(p);
            boolean mismoVuelo = r.getVuelo().equals(v);

            if (mismoPasajero && mismoVuelo) {
                return r; // Encontramos la reserva
            }
        }
        // No se encontró ninguna reserva para este pasajero y vuelo
        return null;
    }

    private void retirarReserva(Object objeto) {//trae el objeto del ultimo registro
        Reserva r = (Reserva) objeto;
        listaReservas.remove(objeto);
        r.getVuelo().getListaPasajeros().remove(r.getPasajero());//Se retira el pasajero del vuelo y se deshace la reserva
        System.out.println("Deshacer: reserva retirada: " + r.getCodReserva());
        historial.registrarUndo(new Registro(Accion.retirarReserva, objeto)); //Registrarlo en el historico
    }



    private void deshacerCancelarReserva(Object obj) {//objeto del ultimo registro
        Reserva cancelada = (Reserva) obj;
        cancelada.setEstado(1);//Cambiar el estado de al reserva
        cancelada.getVuelo().agregarPasajero(cancelada.getPasajero());//agregar nuevamente el pasajero a la lista de pasajeros
        System.out.println("Deshacer: reserva cancelada retirada: " + cancelada.getCodReserva());
        historial.registrarUndo(new Registro(Accion.retirarCancelacionReserva, cancelada)); //Registrar la accion
    }

    private void volverAgregarListaEspera(Object obj) {
        Reserva r = (Reserva) obj;
        r.getVuelo().volverAgregarALaListaDeEspera(r.getPasajero(), 0);
        System.out.println("Deshacer: pasajero devuelto a la lista de espera: " + r.getPasajero().getNombre());
        historial.registrarUndo(new Registro(Accion.volverAgregarListaEspera, r, 0));
    }

    public void listarReservas() {
        if (listaReservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        boolean hayActivas = false;
        for (Reserva r : listaReservas) {
            if (r.getEstado() == 1) {
                System.out.println(r);
                hayActivas = true;
            }
        }
        if (!hayActivas) {
            System.out.println("No hay reservas activas registradas.");
        }
    }

}