// PAQUETE
package src.p03.c01;

// IMPORTS
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * Clase ActividadSalidaPuerta.
 * @implements Runnable.
*/
public class ActividadSalidaPuerta implements Runnable{

	/** Número de salidas máximas por puerta*/
    private static final int NUMSALIDAS = 20;
	/** Nombre de la puerta*/
	private String puerta;
	/** Interface IParque*/
	private IParque parque;

	/**
	 * Constructor de la Clase.
	 * @param puerta
	 * @param parque
	 */
    public ActividadSalidaPuerta(String puerta,IParque parque) {
        this.parque=parque;
        this.puerta=puerta;
    }

    @Override
    public void run() {
        for(int i = 0; i < NUMSALIDAS; i ++) {
            try {
            	parque.salirDelParque(puerta);
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5) * 1000); // Simula salida aleatoria dando un tiempo de parada 0 a 5 segundos
            } catch (InterruptedException e) {
            	Logger.getGlobal().log(Level.INFO, "Salida interrumpida");
                Logger.getGlobal().log(Level.INFO, e.toString());
                return;
            }

        }
    }
}
