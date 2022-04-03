package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque{


	/** 
	 * Atributo que almacena las personas totales.
	*/
	private int contadorPersonasTotales;
	/** 
	 * Tabla Hash con el número de personas en cada puerta. 
	*/
	private Hashtable<String, Integer> contadoresPersonasPuerta;	
	/** 
	 * Atributo que contiene el aforo maximo del parque permitido. 
	*/
	private static final int AFORO = 50;

	/** 
	 * Constructor de la clase.
	*/
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
	}

	/** 
	 * Método entrarAlParque.
	 * Sincronizado.
	 * 
	 * @param String puerta. Puerta de entrada al parque
	 * @return void
	*/
	@Override
	public synchronized void entrarAlParque(String puerta){		
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		// Se comprueba precondición.
		comprobarAntesDeEntrar();
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		//Despertamos a los hilos en espera por una orden wait().
		
		this.notifyAll();
		
		// Comprobamos invariantes.
		checkInvariante();
	}
	
	/** 
	 * Método salirDelParque.
	 * 
	 * @param String puerta. Puerta de entrada al parque
	 * @return void
	*/
	@Override
	public void salirDelParque(String puerta) {
		
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadoresPersonasPuerta.remove(puerta);
		}
		// Se comprueba precondición
		comprobarAntesDeSalir();

		// Disminuimos el contador total y el individual
		contadorPersonasTotales--;
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) - 1);

		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Salida");

		//Despertamos a los hilos en espera por una orden wait().
		this.notifyAll();

		// Comprobamos invariantes.
		checkInvariante();
	}
	
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
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
		while(contadorPersonasTotales==AFORO){
			try {
				this.wait();	
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
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
				this.wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); 
				e.printStackTrace();
			}
		}
	}




}
