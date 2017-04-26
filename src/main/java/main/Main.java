package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import analisis.Clasificador;
import analisis.Manhattan_detector;
import analisis.Mean_Standar_deviation;
import analisis.Resultados;
import muestreo.Muestreo;
import extraccion.Extraccion;

public class Main {

	public static void main(String[] args) {
		/*
		Muestreo m = new Muestreo();
		m.muestreo("files/DSL-StrongPasswordData.csv", new Float(3));
		*/

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
      	Float umb = new Float(0.002);
      	umbrales.put("dt", umb);
      	//Numero de desviaciones que serviran para calcular los umbrales en el segundo clasificador
      	Float desv = new Float(1.5);
      	
      	//Creamos el clasificador
      	//Manhattan
      	//Clasificador manhattan = new Manhattan_detector(caracteristicas, umbrales);
      	//Mean-Standar derivation
      	Clasificador mean_standarDeviation = new Mean_Standar_deviation(caracteristicas, desv);
      	//Entrenamos el clasificador
      	mean_standarDeviation.entrenar(lista_entrenamiento);
      	//Testeamos
      	Resultados r = mean_standarDeviation.testear(lista_test);
      	//Mostramos el resultado
      	r.mostrar();
    
	}

}
