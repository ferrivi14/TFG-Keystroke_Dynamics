package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import analisis.Clasificador;
import analisis.Manhattan_detector;
import analisis.Resultados;
import muestreo.Muestreo;
import extraccion.Extraccion;

public class Main {

	public static void main(String[] args) {
				
		/*
	    //Ahora recorreremos la lista de contraseñas para acceder a los valores que contienen
	    //Para ello, creamos un iterador de la lista
	    Iterator<Password> it = lista_contraseñas.iterator();
	    //while(it.hasNext()){
	    if(it.hasNext()){
	    	Password act = it.next();
	    	act.mostrarDatos();
	    	
	    	System.out.print(act.getSubject() + " " + act.getSession() + " " + act.getRep() + " ");
	    	//Recuperamos la lista de tiempos 
	    	List<Float> lista = act.getDwell_List();
	    	//List<Float> lista = act.getFlight_up_down_List();
	    	//List<Float> lista = act.getFlight_up_up_List();
	    	//List<Float> lista = act.getFlight_down_down_List();
	    	//List<Float> lista = act.getFlight_down_up_List();
	    	//List<Float> lista = act.getNGraph_List();
	    	
	    	//Repetimos el proceso de recorrer una lista, esta vez la lista de tiempos de presion
	    	Iterator<Float> it2 = lista.iterator();
	    	while(it2.hasNext()){
	    		Float number = it2.next();
	    		//Mostramos por pantalla el tiempo de presion actual
	    		if(it2.hasNext())
	    			System.out.print(number + " ");
	    		else
	    			System.out.println(number);
	    	}
	    	
	    	
	    }*/
		
        //Direccion de los documentos de donde se extraeran los datos
      	String conjunto_entrenamiento = "files/muestreo/entrenamiento.csv";
      	String conjunto_test = "files/muestreo/test.csv";
      	//Extraemos la lista de Passwords de entrenamiento y test
      	Extraccion ext = new Extraccion();
      	List<Password> lista_entrenamiento = ext.extraccion_completa(conjunto_entrenamiento);
      	List<Password> lista_test = ext.extraccion_completa(conjunto_test);
      	//Lista de caracteristicas a contemplar en el analisis
      	List<String> caracteristicas = new ArrayList<String>();
      	caracteristicas.add("dt");
      	//Umbrales de las caracteristicas a analizar
      	Map<String, Float> umbrales = new TreeMap<String, Float>();
      	Float umb = new Float(0.02);
      	umbrales.put("dt", umb);
      	//Creamos el clasificador
      	Clasificador manhattan = new Manhattan_detector(caracteristicas, umbrales);
      	//Entrenamos el clasificador
      	manhattan.entrenar(lista_entrenamiento);
      	//Testeamos
      	Resultados r = manhattan.testear(lista_test);
      	//Mostramos el resultado
      	r.mostrar();
	}

}
