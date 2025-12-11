package Modelo;

public class Registro {//Acciones que se vana ir registrando en la pila
        public int tipo;                // qué acción se realizo
        public Object objeto;           // Objeto involucrado
        public int posicion;            // para la lista de espera
        //public Object datoPrevio;       //Guardar la accion anterior


        public Registro(int tipo, Object objeto) { // contructor para los casos de agregar o retirar
            this.tipo = tipo;
            this.objeto = objeto;
        }

        public Registro(int tipo, Object objeto, int posicion) { //constructor que se usa para el undo de la lista de espera
            this.tipo = tipo;
            this.objeto = objeto;
            this.posicion = posicion;
        }
    
}
