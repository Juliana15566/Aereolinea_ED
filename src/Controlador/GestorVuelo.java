package Controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Modelo.Vuelo;

public class GestorVuelo {

    private List<Vuelo> listaVuelos;

    //Constructor de la clase
    public GestorVuelo() {
        listaVuelos = new ArrayList<>();
    }

    //Agregar vuelo
    public void agregarVuelo(Vuelo vuelo) {
 
        // Validar que no exista un vuelo con el codigo ingresado
        if (buscarPorCodigo(vuelo.getCodigo()) != null) {
            System.out.println("Ya existe un vuelo con este código: " + vuelo.getCodigo() + ". Asignar otro codigo al vuelo a ingresar");
            return;
        }

        listaVuelos.add(vuelo);
        System.out.println("Vuelo agregado exitosamente");
    }

    //Buscar vuelo por codigo, recordar hacer un upper en el parametro enviado a buscar
    public Vuelo buscarPorCodigo(String codigo) {
        for (Vuelo v : listaVuelos) {
            if (v.getCodigo().equalsIgnoreCase(codigo)) {
                return v;
            }
        }
        return null;
    }

    //buscar por origen
    public List<Vuelo> buscarPorOrigen(String origen) {
        List<Vuelo> resultados = new ArrayList<>();

        for (Vuelo v : listaVuelos) {
            if (v.getOrigen().equalsIgnoreCase(origen)) {
                resultados.add(v);
            }
        }

        return resultados;
    }

    //Buscar por destino
    public List<Vuelo> buscarPorDestino(String destino) {
        List<Vuelo> resultados = new ArrayList<>();

        for (Vuelo v : listaVuelos) {
            if (v.getDestino().equalsIgnoreCase(destino)) {
                resultados.add(v);
            }
        }

        return resultados;
    }

    //Buscar por fecha de salida
    public List<Vuelo> buscarPorFechaSalida(LocalDate fecha) {
        List<Vuelo> resultados = new ArrayList<>();

        for (Vuelo v : listaVuelos) {
            if (v.getFechaSalida().equals(fecha)) {
                resultados.add(v);
            }
        }

        return resultados;
    }

    //Mostrar vuelos
    public void mostrarTodos() {
        if (listaVuelos.isEmpty()) {
            System.out.println("No hay vuelos registrados.");
            return;
        }

        for (Vuelo v : listaVuelos) {
            System.out.println(v);
        }
    }
}
