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

    public Vuelo buscarPorCodigo_recursivo(String codigo, int posicion) {
        Vuelo vue = listaVuelos.get(posicion);
        if (posicion >= listaVuelos.size()) {
            return null;
        }
        if (vue.getCodigo().equalsIgnoreCase(codigo)) {
            return vue;
        }
        return buscarPorCodigo_recursivo(codigo, posicion + 1);
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

    public List<Vuelo> buscarPorOrigen_recursivo(String origen, int posicion, List<Vuelo> vuelosPorOrigen) {
        if (posicion >= listaVuelos.size()) {
            return vuelosPorOrigen;
        }

        Vuelo vue = listaVuelos.get(posicion);

        if (vue.getOrigen().equals(origen)) {
            vuelosPorOrigen.add(vue);
        }

        return buscarPorOrigen_recursivo(origen, posicion + 1, vuelosPorOrigen);
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

    public List<Vuelo> buscarPorDestino_recursivo(String origen, int posicion, List<Vuelo> vuelosPorDestino) {
        if (posicion >= listaVuelos.size()) {
            return vuelosPorDestino;
        }

        Vuelo vue = listaVuelos.get(posicion);

        if (vue.getOrigen().equals(origen)) {
            vuelosPorDestino.add(vue);
        }

        return buscarPorOrigen_recursivo(origen, posicion + 1, vuelosPorDestino);
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

    public List<Vuelo> buscarPorFecha_recursivo(LocalDate fecha, int posicion, List<Vuelo> vuelosPorFecha) {
    if (posicion >= listaVuelos.size()) {
        return vuelosPorFecha;
    }

    Vuelo vue = listaVuelos.get(posicion);

    if (vue.getFechaSalida().equals(fecha)) {
        vuelosPorFecha.add(vue);
    }

    return buscarPorFecha_recursivo(fecha, posicion + 1, vuelosPorFecha);
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
