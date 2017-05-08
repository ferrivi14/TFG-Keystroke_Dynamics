package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import analisis.Clasificador;
import analisis.Manhattan_detector;
import analisis.Mean_Standar_deviation;
import analisis.Resultados;
import analisis.Z_Scores;
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
      	//Numero de desviaciones que serviran para calcular los umbrales en el Mean, Standar derivation
      	Float desv = new Float(1.5);
      	//Distribucion Estandar (Entre 2 y 3)
      	Float distribEstandar = new Float(3);
      	//Porcentaje de aciertos que debe tener la contraseña para ser aceptada en el Z-Scores
      	Float porAciertos = new Float(90);
      	//Creamos el clasificador
      	
      	//Manhattan
      	//Clasificador manhattan = new Manhattan_detector(caracteristicas, umbrales);
      	
      	//Mean-Standar derivation
      	//Clasificador mean_standarDeviation = new Mean_Standar_deviation(caracteristicas, desv);
      	
      	//Z-scores
      	Clasificador z_scores = new Z_Scores(caracteristicas, distribEstandar, porAciertos);
      	//Entrenamos el clasificador
      	z_scores.entrenar(lista_entrenamiento);
      	//Testeamos
      	Resultados r = z_scores.testear(lista_test);
      	//Mostramos el resultado
      	r.mostrar();
    
	}

}
