// PAQUETE
package src.p03.c01;

// IMPORTS
import java.util.Enumeration;
import java.util.Hashtable;

/** 
 * Clase Parque.
 * @author Adrián Cordero Bernal y Jesús García Armario.
 * @implements src.p03.c01.IParque
*/
public class Parque implements IParque{

	/** 
	 * Atributo que almacena las personas totales.
	*/
	private int contadorPersonasTotales;
	/** Contador de entradas por puerta*/
	private Hashtable<String, Integer> contadoresPersonasPuertaEntrada;
	/** Contador de salidas por puerta*/
	private Hashtable<String, Integer> contadoresPersonasPuertaSalida;
	/** 
	 * Atributo que contiene el aforo maximo del parque permitido. 
	*/
	private int AFORO;

	/** 
	 * Constructor de la clase.
	*/
	public Parque(int capacidad) {
		// Se inicializan los atributos.
		contadorPersonasTotales = 0;
		contadoresPersonasPuertaEntrada = new Hashtable<String, Integer>();
		contadoresPersonasPuertaSalida= new Hashtable<String, Integer>();
		this.AFORO = capacidad;
	}

	/** 
	 * Método entrarAlParque.
	 * Sincronizado.
	 * 
	 * @param String puerta. Puerta de entrada al parque
	 * @return void
	 * @throws InterruptedException 
	*/
	@Override
	public synchronized void entrarAlParque(String puerta){		
		
		// Se comprueba precondición.
		comprobarAntesDeEntrar();
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuertaEntrada.get(puerta) == null){
			contadoresPersonasPuertaEntrada.put(puerta, 0);
		}
		
		// Aumentamos el contador total
		contadorPersonasTotales++;	
		// Aumentamos el contador individual de entradas
		contadoresPersonasPuertaEntrada.put(puerta, contadoresPersonasPuertaEntrada.get(puerta) + 1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		// Comprobamos invariantes.
		checkInvariante();
		
		//Despertamos a los hilos en espera por una orden wait().
		notifyAll();
	}
	
	/** 
	 * Método salirDelParque.
	 * 
	 * @param String puerta. Puerta de entrada al parque
	 * @return void
	*/
	@Override
	public synchronized void salirDelParque(String puerta){
		
		// Se comprueba precondición
		comprobarAntesDeSalir();
		
		if (contadoresPersonasPuertaSalida.get(puerta) == null) {
			contadoresPersonasPuertaSalida.put(puerta,0);
		}

		// Disminuimos el contador total y el individual
		contadorPersonasTotales--;
		// Aumentamos el contador individual de salidas
		contadoresPersonasPuertaSalida.put(puerta, contadoresPersonasPuertaSalida.get(puerta) + 1);

		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Salida");

		// Comprobamos invariantes.
		checkInvariante();
		
		//Despertamos a los hilos en espera por una orden wait().
		notifyAll();
	}
	
	/**
	 * Método imprimirInfo.
	 * Imprime la información del parque.
	 * @param puerta Puerta de acceso.
	 * @param movimiento Entrada o Salida.
	 */
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales);
		
		if(movimiento.equals("Entrada")) {
			// Iteramos por todas las puertas de entrada e imprimimos sus entradas
			for(String p: contadoresPersonasPuertaEntrada.keySet()){
				System.out.println("  -->Entradas por puerta " + p + " " + contadoresPersonasPuertaEntrada.get(p));
			}
		}
		else {
			// Iteramos por todas las puertas de salida e imprimimos sus salidas
			for(String p: contadoresPersonasPuertaSalida.keySet()){
				System.out.println("  -->Salidas por puerta " + p + " " + contadoresPersonasPuertaSalida.get(p));
			}
		}
		System.out.println(" ");
	}
	/**
	 * Método privado que calcula el total de visitantes del parque según cada puerta.
	 * @return int sumaContadoresPuerta. Total de visitantes dentro del parque.
	*/
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
		
		Enumeration<Integer> iterPuertasEntrada = contadoresPersonasPuertaEntrada.elements();
		Enumeration<Integer> iterPuertasSalida = contadoresPersonasPuertaSalida.elements();
		
		while (iterPuertasEntrada.hasMoreElements()) { 
			sumaContadoresPuerta += iterPuertasEntrada.nextElement(); 
		}
		while (iterPuertasSalida.hasMoreElements()) { 
			sumaContadoresPuerta -= iterPuertasSalida.nextElement();
		}
		return sumaContadoresPuerta;
	}
	
	/**
	 * Método que chequea los invariantes.
	 */
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parque";
		assert contadorPersonasTotales <= AFORO : "INV: Aforo máximo alcanzado, no se permiten más personas.";
		assert contadorPersonasTotales >= 0 : "INV: Error en las salidas del parque.";
	}

	/** 
	 * Mientras el parque esté lleno, no se podrá entrar y mantendrá en espera.
	*/
	protected void comprobarAntesDeEntrar(){
		while(contadorPersonasTotales == AFORO){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}

	/** 
	 * Mientras no haya personas dentro del parque permanecera a la espera.
	*/
	protected void comprobarAntesDeSalir(){
		while (contadorPersonasTotales == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
