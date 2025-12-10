package Modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Vuelo {

    private String codigo;
    private String origen;
    private String destino;
    private int limitePasajeros;
    private LocalTime horarioSalida;   
    private LocalDate fechaSalida;  
    
    private List<Pasajero> listaPasajeros = new ArrayList<>();   // Confirmados
    private Queue<Pasajero> listaEspera = new LinkedList<>();    // Lista de espera FIFO

    //Constructor de la clase
    public Vuelo(String codigo, String origen, String destino,
                 int limitePasajeros, String horarioSalida, String fechaSalida) {

        this.codigo = formatearCodigo(codigo);
        this.origen = origen;
        this.destino = destino;
        this.limitePasajeros = limitePasajeros;
        this.horarioSalida = validarHora(horarioSalida);
        this.fechaSalida = validarFecha(fechaSalida);
    }

    // Getters (mostrar el dato)
    public String getCodigo() { 
        return codigo; 
    }

    public String getOrigen() {
         return origen; 
    }

    public String getDestino() 
    { 
        return destino; 
    }

    public int getLimitePasajeros() { 
        return limitePasajeros; 
    }

    public LocalTime getHorarioSalida() { 
        return horarioSalida; 
    }

    public LocalDate getFechaSalida() { 
        return fechaSalida; 
    }

    public List<Pasajero> getListaPasajeros() { 
        return listaPasajeros; 
    }

    public Queue<Pasajero> getListaEspera() { 
        return listaEspera; 
    }

    // Setters (modificar el dato)
    public void setCodigo(String codigo) { 
        this.codigo = formatearCodigo(codigo); 
    }

    public void setOrigen(String origen) { 
        this.origen = origen; 
    }

    public void setDestino(String destino) { 
        this.destino = destino; 
    }

    public void setLimitePasajeros(int limitePasajeros) {
        this.limitePasajeros = limitePasajeros;
    }

    public void setHorarioSalida(String horarioSalida) {
        this.horarioSalida = validarHora(horarioSalida);
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = validarFecha(fechaSalida);
    }

    private String formatearCodigo(String codigo) {
    return codigo.toUpperCase();
    }

    //Validar que la fecha ingresada sea en un formato valido para el tipo de dato.
    private LocalDate validarFecha(String fechaTexto) {
        try {
            return LocalDate.parse(fechaTexto);  
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Formato de fecha inválido. Debe ser YYYY-MM-DD. Ejemplo: 2025-03-21"
            );
        }
    }

    //Validar que la hora ingresada sea en un formato valido para el tipo de dato. En el main especificar que es en formato militar
    private LocalTime validarHora(String horaTexto) {
        try {
            return LocalTime.parse(horaTexto);  
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Formato de hora inválido. Debe ser HH:MM en formato militar. Ejemplo: 14:30"
            );
        }
    }

    // validar si aun hay cupo para saber si va a la lista de espera
    public boolean hayCupo() {
        return listaPasajeros.size() < limitePasajeros;
    }

    // lista de pasajeros confirmados
    public void agregarPasajero(Pasajero p) {
        listaPasajeros.add(p);
    }

    // lista de espera
    public void agregarALaListaDeEspera(Pasajero p) {
        listaEspera.add(p);
    }

    // retirar de la lista de espera
    public Pasajero sacarDeListaEspera() {
        return listaEspera.poll();
    }

    //cantidad de puestos disponibles en el vuelo
    public int espaciosDisponibles() {
        return limitePasajeros - listaPasajeros.size();
    }

    // @Override evita errores al sobrescribir métodos que ya existen en Java. Muestro la informacion del vuelo
    @Override
    public String toString() {
        return "El vuelo ingresado tienen los siguientes datos\n" +
                "codigo='" + codigo + '\'' +
                "\n origen='" + origen + '\'' +
                "\n destino='" + destino + '\'' +
                "\n limitePasajeros=" + limitePasajeros +
                "\n horarioSalida=" + horarioSalida +
                "\n fechaSalida=" + fechaSalida;
    }
}
