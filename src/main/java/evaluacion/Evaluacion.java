package evaluacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import extraccion.Extraccion;
import main.Password;
import analisis.Clasificador;
import analisis.Manhattan_detector;
import analisis.Mean_Standar_deviation;
import analisis.Resultados;
import analisis.Z_Scores;

public class Evaluacion {
	
	private String escribirResultado(Resultados r) {
		return new String("FRR: " + r.getFRR() + " FAR: " + r.getFAR() );
	}
	private String escribirCarac(List<String> caracteristicas, Map<String, Float> umbrales) {
		String result = "", caracAct;
		Iterator<String> it = caracteristicas.iterator();
		while(it.hasNext()){
			caracAct = it.next();
			result += caracAct;
			result += "(" + umbrales.get(caracAct) + ")";
			if(it.hasNext())
				result += ",";
		}
		result += ": ";
		return result;
	}
	private String escribirCarac2(List<String> caracteristicas, Float desv) {
		String result = "Desviacion = " + desv + "; Caracteristicas: ", caracAct;
		Iterator<String> it = caracteristicas.iterator();
		while(it.hasNext()){
			caracAct = it.next();
			result += caracAct;
			if(it.hasNext())
				result += ",";
		}
		result += ": ";
		return result;
	}
	private String escribirCarac3(List<String> caracteristicas, Float distribucion, Float porAciertos) {
		String result = "Distribucion = " + distribucion + "; % Aciertos: " + porAciertos + "; Caracteristicas: ", caracAct;
		Iterator<String> it = caracteristicas.iterator();
		while(it.hasNext()){
			caracAct = it.next();
			result += caracAct;
			if(it.hasNext())
				result += ",";
		}
		result += ": ";
		return result;
	}
	
	public void evaluacionManhattan(List<Password> conjunto_entrenamiento, List<Password> conjunto_test){
		//Resultados
		Resultados r;
		List<String> resultados = new ArrayList<String>();
		//Caracteristicas
		List<String> caracteristicas = new ArrayList<String>();
		//Clasificador
		Clasificador manhattan;
		
      	//Umbrales de las caracteristicas a analizar
      	Map<String, Float> umbrales = new TreeMap<String, Float>();
      	List<Float> umbsDT = new ArrayList<Float>();
      	umbsDT.add(new Float(0.2));
      	umbsDT.add(new Float(0.0));
      	umbsDT.add(new Float(-0.35));

      	List<Float> umbsFT1 = new ArrayList<Float>();
      	umbsFT1.add(new Float(0.4));
      	umbsFT1.add(new Float(0.1));
      	umbsFT1.add(new Float(-0.4));
      	
      	List<Float> umbsFT2 = new ArrayList<Float>();
      	umbsFT2.add(new Float(0.15));
      	umbsFT2.add(new Float(-0.05));
      	umbsFT2.add(new Float(-0.35));
      	
      	List<Float> umbsFT3 = new ArrayList<Float>();
      	umbsFT3.add(new Float(0.25));
      	umbsFT3.add(new Float(0.0));
      	umbsFT3.add(new Float(-0.3));
      	
      	List<Float> umbsFT4 = new ArrayList<Float>();
      	umbsFT4.add(new Float(0.15));
      	umbsFT4.add(new Float(-0.05));
      	umbsFT4.add(new Float(-0.35));
      	
      	List<Float> umbsNG = new ArrayList<Float>();
      	umbsNG.add(new Float(0.15));
      	umbsNG.add(new Float(-0.05));
      	umbsNG.add(new Float(-0.45));
      	
      	umbrales.put("dt", umbsDT.get(0));
      	umbrales.put("ft1", umbsFT1.get(0));
      	umbrales.put("ft2", umbsFT2.get(0));
      	umbrales.put("ft3", umbsFT3.get(0));
      	umbrales.put("ft4", umbsFT4.get(0));
      	umbrales.put("ng", umbsNG.get(0));

      	
      	String resultadoAct = "";
      	//Realizamos una ejecucion del algoritmo para cada combinacion de caracterisiticas
      	for(int i = 0; i < 4; i++){
      		if(i == 0){
      			caracteristicas.add("dt");
      		}
      		else if(i == 1){
      			umbrales.put("dt", umbsDT.get(1));
      		}
      		else if(i == 2){
      			umbrales.put("dt", umbsDT.get(2));
      		}
      		else if(i == 3){
      			caracteristicas.remove("dt");
      		}
      		//Siguiente caracteristica
      		for(int j = 0; j < 4; j++){
      			if(j == 0){
          			caracteristicas.add("ft1");
          		}
      			else if(j == 1){
          			umbrales.put("ft1", umbsFT1.get(1));
          		}
          		else if(j == 2){
          			umbrales.put("ft1", umbsFT1.get(2));
          		}
          		else if(j == 3){
          			caracteristicas.remove("ft1");
          		}
      			//Siguiente caracteristica
      			for(int k = 0; k < 4; k++){
      				if(k == 0){
              			caracteristicas.add("ft2");
              		}
      				else if(k == 1){
              			umbrales.put("ft2", umbsFT2.get(1));
              		}
              		else if(k == 2){
              			umbrales.put("ft2", umbsFT2.get(2));
              		}
              		else if(k == 3){
              			caracteristicas.remove("ft2");
              		}
      				//Siguiente caracteristica
      				for(int l = 0; l < 4; l++){
          				if(l == 0){
                  			caracteristicas.add("ft3");
                  		}
          				else if(l == 1){
                  			umbrales.put("ft3", umbsFT3.get(1));
                  		}
                  		else if(l == 2){
                  			umbrales.put("ft3", umbsFT3.get(2));
                  		}
                  		else if(l == 3){
                  			caracteristicas.remove("ft3");
                  		}
          				//Siguiente caracteristica
          				for(int m = 0; m < 4; m++){
              				if(m == 0){
                      			caracteristicas.add("ft4");
                      		}
              				else if(m == 1){
                      			umbrales.put("ft4", umbsFT4.get(1));
                      		}
                      		else if(m == 2){
                      			umbrales.put("ft4", umbsFT4.get(2));
                      		}
                      		else if(m == 3){
                      			caracteristicas.remove("ft4");
                      		}
              				//Siguiente caracteristica
              				for(int n = 0; n < 4; n++){
                  				if(n == 0){
                          			caracteristicas.add("ng");
                          		}
                  				else if(n == 1){
                          			umbrales.put("ng", umbsNG.get(1));
                          		}
                          		else if(n == 2){
                          			umbrales.put("ng", umbsNG.get(2));
                          		}
                          		else if(n == 3){
                          			caracteristicas.remove("ng");
                          		}
                  				//Si la lista de caracteristicas no esta vacia
                  				if(!caracteristicas.isEmpty()){
                  				//Ejecucion del algoritmo
                      				//Creamos el clasificador conn las caracteristicas y umbrales actuales
                      	      		manhattan = new Manhattan_detector(caracteristicas, umbrales);
                      	      		//Entrenamos
                      	      		manhattan.entrenar(conjunto_entrenamiento);
                      	      		//Testeamos
                      	      		r = manhattan.testear(conjunto_test);
                      	      		//Escribimos el resultado en una linea junto con las caracteristicas actuales
                      	      		resultadoAct += escribirCarac(caracteristicas, umbrales); 
                      	      		resultadoAct += escribirResultado(r);
                      	      		//Guardamos el resulado en la lista de resultados
                      	      		resultados.add(resultadoAct);
                      	      		resultadoAct = "";
                  				}
                  			}
              			}
          			}
      			}
      		}
      	}
      	Extraccion ex = new Extraccion();
		ex.generacion_txt(resultados, "resultados-mahanttan");
	}
	
	public void evaluacionMean_StandarDerivation(List<Password> conjunto_entrenamiento, List<Password> conjunto_test){
		//Resultados
		Resultados r;
		List<String> resultados = new ArrayList<String>();
		//Caracteristicas
		List<String> caracteristicas = new ArrayList<String>();
		//Clasificador
		Clasificador mean_standarDerivation;
		
      	//Desviaciones
      	List<Float> desviaciones = new ArrayList<Float>();
      	desviaciones.add(new Float(1.0));
      	desviaciones.add(new Float(1.25));
      	desviaciones.add(new Float(1.5));
      	desviaciones.add(new Float(1.75));
      	desviaciones.add(new Float(2.0));
      	
      	String resultadoAct = "";
      	//Realizamos una ejecucion del algoritmo para cada combinacion de caracterisiticas
      	for(int h = 0; h < 5; h++){
      		for(int i = 0; i < 2; i++){
          		if(i == 0){
          			caracteristicas.add("dt");
          		}
          		else if(i == 1){
          			caracteristicas.remove("dt");
          		}
          		//Siguiente caracteristica
          		for(int j = 0; j < 2; j++){
          			if(j == 0){
              			caracteristicas.add("ft1");
              		}
              		else if(j == 1){
              			caracteristicas.remove("ft1");
              		}
          			//Siguiente caracteristica
          			for(int k = 0; k < 2; k++){
          				if(k == 0){
                  			caracteristicas.add("ft2");
                  		}
                  		else if(k == 1){
                  			caracteristicas.remove("ft2");
                  		}
          				//Siguiente caracteristica
          				for(int l = 0; l < 2; l++){
              				if(l == 0){
                      			caracteristicas.add("ft3");
                      		}
                      		else if(l == 1){
                      			caracteristicas.remove("ft3");
                      		}
              				//Siguiente caracteristica
              				for(int m = 0; m < 2; m++){
                  				if(m == 0){
                          			caracteristicas.add("ft4");
                          		}
                          		else if(m == 1){
                          			caracteristicas.remove("ft4");
                          		}
                  				//Siguiente caracteristica
                  				for(int n = 0; n < 2; n++){
                      				if(n == 0){
                              			caracteristicas.add("ng");
                              		}
                              		else if(n == 1){
                              			caracteristicas.remove("ng");
                              		}
                      				//Si la lista de caracteristicas no esta vacia
                      				if(!caracteristicas.isEmpty()){
                      				//Ejecucion del algoritmo
                          				//Creamos el clasificador con las caracteristicas y dessviacion actuales
                          	      		mean_standarDerivation = new Mean_Standar_deviation(caracteristicas, desviaciones.get(h));
                          	      		//Entrenamos
                          	      		mean_standarDerivation.entrenar(conjunto_entrenamiento);
                          	      		//Testeamos
                          	      		r = mean_standarDerivation.testear(conjunto_test);
                          	      		//Escribimos el resultado en una linea junto con las caracteristicas actuales
                          	      		resultadoAct += escribirCarac2(caracteristicas, desviaciones.get(h)); 
                          	      		resultadoAct += escribirResultado(r);
                          	      		//Guardamos el resulado en la lista de resultados
                          	      		resultados.add(resultadoAct);
                          	      		resultadoAct = "";
                      				}
                      			}
                  			}
              			}
          			}
          		}
          	}	
      	}
      	
      	Extraccion ex = new Extraccion();
		ex.generacion_txt(resultados, "resultados-mean_standarDerivation");
	}
	public void evaluacionZscores(List<Password> conjunto_entrenamiento, List<Password> conjunto_test){
		//Resultados
		Resultados r;
		List<String> resultados = new ArrayList<String>();
		//Caracteristicas
		List<String> caracteristicas = new ArrayList<String>();
		//Clasificador
		Clasificador z_scores;
		
      	//Distribuciones
      	List<Float> distribuciones = new ArrayList<Float>();
      	distribuciones.add(new Float(1.5));
      	distribuciones.add(new Float(2));
      	distribuciones.add(new Float(2.5));
      	distribuciones.add(new Float(3));
      	
      	//Porcentajes de aciertos
      	List<Float> porAciertos = new ArrayList<Float>();
      	porAciertos.add(new Float(50));
      	porAciertos.add(new Float(60));
      	porAciertos.add(new Float(70));
      	porAciertos.add(new Float(80));
      	porAciertos.add(new Float(90));
      	
      	String resultadoAct = "";
      	//Realizamos una ejecucion del algoritmo para cada combinacion de caracterisiticas
      	for(int g = 0; g < 5; g++){
      		for(int h = 0; h < 4; h++){
          		for(int i = 0; i < 2; i++){
              		if(i == 0){
              			caracteristicas.add("dt");
              		}
              		else if(i == 1){
              			caracteristicas.remove("dt");
              		}
              		//Siguiente caracteristica
              		for(int j = 0; j < 2; j++){
              			if(j == 0){
                  			caracteristicas.add("ft1");
                  		}
                  		else if(j == 1){
                  			caracteristicas.remove("ft1");
                  		}
              			//Siguiente caracteristica
              			for(int k = 0; k < 2; k++){
              				if(k == 0){
                      			caracteristicas.add("ft2");
                      		}
                      		else if(k == 1){
                      			caracteristicas.remove("ft2");
                      		}
              				//Siguiente caracteristica
              				for(int l = 0; l < 2; l++){
                  				if(l == 0){
                          			caracteristicas.add("ft3");
                          		}
                          		else if(l == 1){
                          			caracteristicas.remove("ft3");
                          		}
                  				//Siguiente caracteristica
                  				for(int m = 0; m < 2; m++){
                      				if(m == 0){
                              			caracteristicas.add("ft4");
                              		}
                              		else if(m == 1){
                              			caracteristicas.remove("ft4");
                              		}
                      				//Siguiente caracteristica
                      				for(int n = 0; n < 2; n++){
                          				if(n == 0){
                                  			caracteristicas.add("ng");
                                  		}
                                  		else if(n == 1){
                                  			caracteristicas.remove("ng");
                                  		}
                          				//Si la lista de caracteristicas no esta vacia
                          				if(!caracteristicas.isEmpty()){
                          				//Ejecucion del algoritmo
                              				//Creamos el clasificador con las caracteristicas y dessviacion actuales
                              	      		z_scores = new Z_Scores(caracteristicas, distribuciones.get(h), porAciertos.get(g));
                              	      		//Entrenamos
                              	      		z_scores.entrenar(conjunto_entrenamiento);
                              	      		//Testeamos
                              	      		r = z_scores.testear(conjunto_test);
                              	      		//Escribimos el resultado en una linea junto con las caracteristicas actuales
                              	      		resultadoAct += escribirCarac3(caracteristicas, distribuciones.get(h), porAciertos.get(g)); 
                              	      		resultadoAct += escribirResultado(r);
                              	      		//Guardamos el resulado en la lista de resultados
                              	      		resultados.add(resultadoAct);
                              	      		resultadoAct = "";
                          				}
                          			}
                      			}
                  			}
              			}
              		}
              	}	
          	}
      	}
      	
      	
      	Extraccion ex = new Extraccion();
		ex.generacion_txt(resultados, "resultados-Zscores");
	}
	
}
