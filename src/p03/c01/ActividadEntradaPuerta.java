// PAQUETE
package src.p03.c01;

// IMPORTS
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * Clase ActividadEntradaPuerta.
 * @implements Runnable.
*/
public class ActividadEntradaPuerta implements Runnable{

		/** Número de entradas máximas por puerta*/
		private static final int NUMENTRADAS = 20;
		/** Nombre de la puerta*/
		private String puerta;
		/** Interface IParque*/
		private IParque parque;

		/**
		 * Constructor de la Clase.
		 * @param puerta
		 * @param parque
		 */
		public ActividadEntradaPuerta(String puerta, IParque parque) {
			this.puerta = puerta;
			this.parque = parque;
		}

		@Override
		public void run() {
			for (int i = 0; i < NUMENTRADAS; i ++) {
				try {
					parque.entrarAlParque(puerta);
					TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000); // Simula entrada aleatoria dando un tiempo de parada 0 a 5 segundos
				} catch (InterruptedException e) {
					Logger.getGlobal().log(Level.INFO, "Entrada interrumpida");
					Logger.getGlobal().log(Level.INFO, e.toString());
					return;
				}
			}
		}
}
