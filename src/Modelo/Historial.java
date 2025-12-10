package Modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Historial {

    private Stack<Registro> pila = new Stack<>(); //permite hacer el undo
    private List<String> log = new ArrayList<>(); //se registra el log

    private String obtenerHora() { //Es un metodo donde se tomara la fecha y hora para dejarlo en el registro del log
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + LocalDateTime.now().format(format) + "] ";
    }

    public void registrar(Registro registro) { //Registrar las acciones en el historico y en el log
        pila.push(registro);
        agregarLog(registro);
    }
    public void registrarUndo(Registro registro) { //Registrar las acciones en el historico y en el log
        pila.pop();
        agregarLog(registro);
    }

    
    private void agregarLog(Registro registro) {//se agrega en el log todas las acciones realizadas
        String tiempo = obtenerHora();

        switch (registro.tipo) {

            case Accion.agregarPasajero:
                log.add(tiempo + "Se agregó un pasajero: " + registro.objeto);
                break;

            case Accion.retirarPasajero:
                log.add(tiempo + "Se retiró un pasajero: " + registro.objeto);
                break;

            case Accion.agregarReserva:
                log.add(tiempo + "Se agregó una reserva: " + registro.objeto);
                break;

            case Accion.cancelarReserva:
                log.add(tiempo + "Se canceló una reserva: " + registro.objeto);
                break;

            case Accion.retirarReserva:
                log.add(tiempo + "Se retiró una reserva: " + registro.objeto);
                break;

            case Accion.agregarListaEspera:
                log.add(tiempo + "Se agregó a lista de espera: " + registro.objeto);
                break;

            case Accion.sacarDeListaEspera:
                log.add(tiempo + "Se sacó de la lista de espera: " + registro.objeto +
                        " (posición original " + registro.posicion + ")");
                break;

            case Accion.volverAgregarListaEspera:
                log.add(tiempo + "Se volvió a agregar a la lista de espera: " + registro.objeto +
                        " (posición " + registro.posicion + ")");
                break;

            case Accion.agregarVuelo:
                log.add(tiempo + "Se agregó un vuelo: " + registro.objeto);
                break;

            case Accion.retirarVuelo:
                log.add(tiempo + "Se retiró un vuelo: " + registro.objeto);
                break;
        }
    }

    public List<String> getLog() {// Obtener el log como lista
        return log;
    }
    
    public boolean hayAcciones() { // Verificar si hay acciones
        return !pila.isEmpty();
    }

    
    public Registro deshacer() { // Devuelve la accion a deshacer
        if (!pila.isEmpty()) {
            return pila.pop();
        }
        return null;
    }

    public List<Registro> obtenerHistorico() {
        return new ArrayList<>(pila); // devolvemos la pila
    }
}
