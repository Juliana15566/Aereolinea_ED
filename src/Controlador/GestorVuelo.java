package Controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Modelo.Accion;
import Modelo.Historial;
import Modelo.Registro;
import Modelo.Vuelo;

public class GestorVuelo {

    private List<Vuelo> listaVuelos;
    private Historial historial;

    //Constructor de la clase
    public GestorVuelo() {
        listaVuelos = new ArrayList<>();
    }
    public GestorVuelo(Historial historial) {
        listaVuelos = new ArrayList<>();
        this.historial = historial;
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

        historial.registrar(new Registro(Accion.agregarVuelo, vuelo)); //Se registra en el historico
    }

    // public void retirarVuelo(Vuelo vuelo) {
    //     listaVuelos.remove(vuelo);
    //     System.out.println("Vuelo retirado correctamente: " + vuelo.getCodigo());

    //     historial.registrarUndo(new Registro(Accion.retirarVuelo, vuelo));// Registrar en historial
    // }

    public void retirarVuelo() {

        Registro ultimo = historial.obtenerUltimo();

        if (ultimo == null) {
            System.out.println("No hay acciones para deshacer.");
            return;
        }

        // SOLO se permite undo si la acción fue agregarVuelo
        if (ultimo.tipo != Accion.agregarVuelo) {
            System.out.println("La última acción no es de vuelo. No se puede deshacer aquí.");
            return;
        }

        // Sacar el registro real de la pila
        Registro reserva = historial.deshacer();  
        Vuelo vuelo = (Vuelo) reserva.objeto;

        // Retirar vuelo de la lista
        listaVuelos.remove(vuelo);

        System.out.println("deshacer: Se retiró el vuelo " + vuelo.getCodigo());

        // Registrar SOLO en el log (no en la pila)
        historial.registrarUndo(new Registro(Accion.retirarVuelo, vuelo));
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
        if (posicion >= listaVuelos.size()) {//Si se sale del tamaño de la lista de vuelos
            return null;
        }
        if (vue.getCodigo().equalsIgnoreCase(codigo)) { //si lo encuentra
            return vue;
        }
        return buscarPorCodigo_recursivo(codigo, posicion + 1); //buscar en una nueva posicion
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
        if (posicion >= listaVuelos.size()) {//Si se sale del tamaño de la lista de vuelos
            return vuelosPorOrigen; //retornar la lista final de las coincidencias
        }
        Vuelo vue = listaVuelos.get(posicion); //buscar en la posicion en especifico (lista de vuelos)
        if (vue.getOrigen().equals(origen)) { //Si lo encuentra
            vuelosPorOrigen.add(vue); //agregar en la lista de vuelos que coinciden con el origen
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
        if (posicion >= listaVuelos.size()) {//Si se sale del tamaño de la lista de vuelos
            return vuelosPorDestino; //retornar la lista final de las coincidencias
        }
        Vuelo vue = listaVuelos.get(posicion);//buscar en la posicion en especifico (lista de vuelos)
        if (vue.getOrigen().equals(origen)) {//Si lo encuentra
            vuelosPorDestino.add(vue);//agregar en la lista de vuelos que coinciden con el origen
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
    if (posicion >= listaVuelos.size()) {//Si se sale del tamaño de la lista de vuelos
        return vuelosPorFecha;//retornar la lista final de las coincidencias
    }
    Vuelo vue = listaVuelos.get(posicion);//buscar en la posicion en especifico (lista de vuelos)
    if (vue.getFechaSalida().equals(fecha)) {//Si lo encuentra
        vuelosPorFecha.add(vue);//agregar en la lista de vuelos que coinciden con el origen
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
