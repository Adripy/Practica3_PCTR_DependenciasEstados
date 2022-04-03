// PAQUETES
package src.p03.c01;

/** 
 * Clase SistemaLanzador.
 * @author Adrián Cordero Bernal y Jesús García Armario.
*/
public class SistemaLanzador {
	/**
	 * MÉTODO RAÍZ.
	 * @param args Número de puertas.
	 */
	public static void main(String[] args) {
		// INICIALIZACIÓN DE ATRIBUTOS.
		/**  Aforo máximo del parque. */
		final int CAPACIDAD = 50;
		/**Instancia de tipo Parque*/
		IParque parque = new Parque(CAPACIDAD);
		/** Letra de puerta inicial. */ 
		char letra_puerta = 'A';

		// COMIENZO DE EJECUCIÓN.
		System.out.println("¡Parque abierto!");

		// Lanzamiento de hilos.
		for (int i = 0; i < Integer.parseInt(args[0]); i++) {
			
			String puerta = ""+((char) (letra_puerta++));
			
			// Creación de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread (entradas).start();
			
			// Creación de hilos de salida.
			ActividadSalidaPuerta salidas = new ActividadSalidaPuerta(puerta, parque);
			new Thread (salidas).start();
		}
	}	
}