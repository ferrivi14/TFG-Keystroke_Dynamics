package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import analisis.Clasificador;
import analisis.Manhattan_detector;
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
		
        //Direccion del documento del que se extraeran los datos
      	String csvFile = "files/muestreo/test.csv";
      	//Extraemos la lista de Password
      	Extraccion ext = new Extraccion();
      	List<Password> lista_contraseñas = ext.extraccion_completa(csvFile);
      	//
      	List<String> caracteristicas = new ArrayList<String>();
      	caracteristicas.add("dt");
      	Map<String, Float> umbrales = new TreeMap<String, Float>();
      	Float umb = new Float(0.001);
      	umbrales.put("dt", umb);
      	Clasificador manhattan = new Manhattan_detector(caracteristicas, umbrales);
      	
      	manhattan.entrenar(lista_contraseñas);
      	
      	manhattan.mostrar();
	}

}
