package Modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistoricoBase {

    
    private List<String> log = new ArrayList<>(); // Lista donde se guardarán todos los eventos del aeropuerto

    
    public void registrar(String accion) { //Agregar la acciones realizadas en el aeropuerto

        String entrada = "[" + LocalDateTime.now() + "] - " + accion;
        log.add(entrada);
    }

    
    public void mostrarHistorial() { // Mostrar todos las acciones realizadas en la aerolinea

        if (log.isEmpty()) {
            System.out.println("No se ha realizado ninguna accion que se registre en el historico");
            return;
        }else{
            System.out.println("\nHistorico de acciones registradas en el sistema\n");

            for (String linea : log) {
                System.out.println(linea);
            }
        }
    }
}
