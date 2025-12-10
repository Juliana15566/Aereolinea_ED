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
    public Pasajero buscarPorPasaporte(String documento) {
        for (Pasajero p : listaPasajeros) {
            if (p.getpasaporte().equals(documento)) {
                return p;
            }
        }
        return null; // No encontrado
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

    //retorno la lista de pasajeros
    public List<Pasajero> getListaPasajeros() {
        return listaPasajeros;
    }
}
