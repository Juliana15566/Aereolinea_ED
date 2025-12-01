package Modelo;

public class Pasajero {

    private String pasaporte;
    private String nombre;

    // Constructor que recibe como parametros el pasaporte y el nombre (Realizando validaciones)
    public Pasajero(String pasaporte, String nombre) {
        this.pasaporte = validarpasaporte(pasaporte);
        this.nombre = estandarizarNombre(nombre);
    }

    // Getters (mostrar el dato)
    public String getpasaporte() {
        return pasaporte;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters (modificar el dato)
    public void setpasaporte(String pasaporte) {
        this.pasaporte = validarpasaporte(pasaporte);
    }

    public void setNombre(String nombre) {
        this.nombre = estandarizarNombre(nombre);
    }

    
    private String validarpasaporte(String pasaporte) {
        // Verificar que NO esté vacío
        if (pasaporte == null || pasaporte.trim().isEmpty()) {
            System.out.println("El pasaporte no puede estar vacío"); 
        }

        // valida que los caracteres solo sean valores numericos
        if (!pasaporte.matches("\\d+")) {
            System.out.println("\"El pasaporte solo puede contener números\"");
        }

        return pasaporte;
    }

    //Se estandariza el nombre para que en el valor ingresado la primera letra sea en mayuscula y el resto de la palabra sea en minuscula
    private String estandarizarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre no puede estar vacío"); 
        }

        //Quita espacios adelante y atras y tambien pone todo el texto en minusculas
        nombre = nombre.toLowerCase().trim();

        //Pone en un arreglo todo el contenido, pero separado por los espacios
        String[] partes = nombre.split("\\s+");

        //Vamos gusradando el contenido del arreglo pero ya enn una cadena de texto
        StringBuilder nombreFormateado = new StringBuilder();

        //Recorro la posicion del arreglo letra por letra
        for (String p : partes) {
            if (p.length() > 0) {
                // Primera letra mayúscula + resto minúscula
                nombreFormateado.append(
                        p.substring(0, 1).toUpperCase()
                ).append(
                        p.substring(1)
                ).append(" ");
            }
        }

        return nombreFormateado.toString().trim();
    }

    // toString para mostrar el pasajero
    @Override
    public String toString() {
        return "Se registro el pasajero con los siguientes datos\n" +
                "pasaporte='" + pasaporte + '\'' +
                "\nnombre='" + nombre + '\''
                ;
    }
}