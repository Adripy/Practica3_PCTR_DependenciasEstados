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
		
		//
		
		
		// Comprobamos postcondición
		checkInvariante();
		
	}
	
	// 
	// Implementación de método salirDelParque
	//
	
	@Override
	public void salirDelParque(String puerta) {
		// TODO 
		
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
	
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		// TODO 
		// TODO
		
		
		
	}

	protected void comprobarAntesDeEntrar(){
		//
		// TODO
		//
	}

	protected void comprobarAntesDeSalir(){
		//
		// TODO
		//
	}




}
