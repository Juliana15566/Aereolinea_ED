package Controlador;  // <-- Ajusta según tu estructura de paquetes

import java.util.ArrayList;
import java.util.List;

import Modelo.Accion;
import Modelo.Historial;
import Modelo.Pasajero;
import Modelo.Registro;

public class GestorPasajero {

    private List<Pasajero> listaPasajeros; //realizar la lista de todos los pasajeros
    private Historial historial; //Dejar registro en el log y la pila


    public GestorPasajero() { //Constructor vacio para la vista
        listaPasajeros = new ArrayList<>();
    }
    
    // Constructor: en el que requerimos la clase historial para los registros
    public GestorPasajero(Historial historial) {
        this.historial = historial;
        listaPasajeros = new ArrayList<>();
    }

    
    //Agregar un pasajero a la lista de pasajeros
    public void agregarPasajero(Pasajero pasajero) {

        // Validar que no exista otro pasajero con el mismo documento
        if (buscarPorPasaporte(pasajero.getpasaporte()) != null) {
            System.out.println("Ya existe un pasajero con el pasaporte: " + pasajero.getpasaporte());
            return;
        }

        listaPasajeros.add(pasajero); //mando el objeto pasajero desde el llamado
        System.out.println("Pasajero agregado exitosamente: " + pasajero.getNombre());

        historial.registrar(new Registro(Accion.agregarPasajero, pasajero)); //Se registra en el historico
    }

    // public void retirarPasajero(Pasajero pasajero) { // Retirar pasajero, se usa el undo de la pila
    //     listaPasajeros.remove(pasajero);
    //     System.out.println("Pasajero retirado correctamente: " + pasajero.getNombre());

    //     // Registrar en historial
    //     historial.registrarUndo(new Registro(Accion.retirarPasajero, pasajero));
    // }

    public void retirarPasajero() {

        Registro ultimo = historial.obtenerUltimo();

        if (ultimo == null) {
            System.out.println("No hay acciones para deshacer.");
            return;
        }

        // SOLO se permite undo si la acción fue agregarPasajero
        if (ultimo.tipo != Accion.agregarPasajero) {
            System.out.println("La última acción no es de pasajero. No se puede deshacer aquí.");
            return;
        }

        Registro reserva = historial.deshacer(); // Retirr de los registros la insercion del pasajero
        Pasajero pas = (Pasajero) reserva.objeto; //traer al pasajero que se retiro
        listaPasajeros.remove(pas); //REtirara al pasajero de la lista de pasajeros

        System.out.println("deshacer: Se retiró un pasajero " + pas.getNombre());

        // Registrar SOLO en el log
        historial.registrarUndo(new Registro(Accion.retirarPasajero, pas));
    }

    

    //busco al pasajero por el pasaporte, tengo que validar el documento antes de mandarlo a buscar
    public Pasajero buscarPorPasaporte(String pasaporte) {
        for (Pasajero p : listaPasajeros) {
            if (p.getpasaporte().equals(pasaporte)) {
                return p;
            }
        }
        return null; // No encontrado

        
    }

    public Pasajero buscarPorPasaporte_recursivo(String pasaporte, int posicion) {
        Pasajero pas = listaPasajeros.get(posicion);
        if (posicion >= listaPasajeros.size()) { //Si se sale del tamaño de la lista de pasajeros
            return null;
        }
        if (pas.getpasaporte().equals(pasaporte)) { //si lo encuentra
            return pas;
        }
        return buscarPorPasaporte_recursivo(pasaporte, posicion + 1); //buscar en una nueva posicion
    }

    //busco al pasajero por el nombre, tengo que estandarizar el nombre antes de mandarlo a buscar
    public Pasajero buscarPorNombre(String nombre) {
        for (Pasajero p : listaPasajeros) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null; // No encontrado
    }

    public List<Pasajero> buscarPorNombre_recursivo(String nombre, int posicion, List<Pasajero> pasajerosPorNombre) {
        Pasajero pas = listaPasajeros.get(posicion);
        if (posicion >= listaPasajeros.size()) { //Si se sale del tamaño de la lista de pasajeros
            return pasajerosPorNombre;
        }
        if (pas.getNombre().toLowerCase().contains(nombre.toLowerCase())) { //si lo encuentra
            pasajerosPorNombre.add(pas);
        }

        return buscarPorNombre_recursivo(nombre, posicion + 1, pasajerosPorNombre); //buscar en una nueva posicions
    }


    //retorno la lista de pasajeros
    public List<Pasajero> getListaPasajeros() {
        return listaPasajeros;
    }
}
