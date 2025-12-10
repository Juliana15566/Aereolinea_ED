package Modelo;

public class Reserva {

    private String codReserva;
    private Pasajero pasajero;   // ← objeto completo
    private Vuelo vuelo;         // ← objeto completo
    private int estado;

    public Reserva(Pasajero pasajero, Vuelo vuelo, int estado) {
        this.codReserva = generarCodigo(); 
        this.pasajero = pasajero;
        this.vuelo = vuelo;
        setEstado(estado); //dentro del set hace la asignacion y si se pone como los otros se pierde la validacion y seria redundante
    }

    public String getCodReserva() {
         return codReserva; 
    }
    public Pasajero getPasajero() { 
        return pasajero;
    }
    public Vuelo getVuelo() { 
        return vuelo; 
    }
    public int getEstado() { 
        return estado; 
    }

    public void setPasajero(Pasajero pasajero) { 
        this.pasajero = pasajero; 
    }
    public void setVuelo(Vuelo vuelo) { 
        this.vuelo = vuelo; 
    }
    public void setEstado(int estado) {
        if (validarEstado(estado)) {
            this.estado = estado;
        }
    }

    private String generarCodigo() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codigo = new StringBuilder();
        java.util.Random aletorio = new java.util.Random();

        for (int i = 0; i < 6; i++) { //codigo de 6 caracteres
            int aleatorio_codigo = aletorio.nextInt(caracteres.length()); //guardar el valor aleatorio en una variable
            codigo.append(caracteres.charAt(aleatorio_codigo)); //guardar el caracter aleatorio enn el StringBuilder
        }
        return codigo.toString(); //devolver el codigo generado
    }

    private boolean validarEstado(int estado) {
        if (estado == 0 || estado == 1) {
            return true;
        } else {
            System.out.println("Error: el estado debe ser 0 (cancelado) o 1 (confirmado).");
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "Reserva{" +
                "codReserva='" + codReserva + '\'' +
                ", pasajero=" + pasajero +
                ", vuelo=" + vuelo +
                ", estado='" + estado + '\'' +
                '}';
    }
}
