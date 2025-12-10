package Controlador;  // <-- Ajusta según tu estructura de paquetes

import java.util.ArrayList;
import java.util.List;
import Modelo.Pasajero;

public class GestorPasajero {

    private List<Pasajero> listaPasajeros;

    // Constructor: inicializa la lista
    public GestorPasajero() {
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
        System.out.println("✔ Pasajero agregado exitosamente: " + pasajero.getNombre());
    }

    //busco al pasajero por el documento, tengo que validar el documento antes de mandarlo a buscar
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
        if (posicion >= listaPasajeros.size()) {
            return null;
        }
        if (pas.getpasaporte().equals(pasaporte)) {
            return pas;
        }
        return buscarPorPasaporte_recursivo(pasaporte, posicion + 1);
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
        if (posicion >= listaPasajeros.size()) {
            return pasajerosPorNombre;
        }
        if (pas.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
            pasajerosPorNombre.add(pas);
        }

        return buscarPorNombre_recursivo(nombre, posicion + 1, pasajerosPorNombre);
    }


    //retorno la lista de pasajeros
    public List<Pasajero> getListaPasajeros() {
        return listaPasajeros;
    }
}
