package evaluacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import extraccion.Extraccion;
import extraccion.Tabla;
import main.Password;
import analisis.Clasificador;
import analisis.Manhattan_detector;
import analisis.Mean_Standar_deviation;
import analisis.Resultados;
import analisis.Z_Scores;

public class Evaluacion {
	
	private String escribirResultado(Resultados r, List<String> caracteristicas, Map<String, Float> umbrales) {
		return new String(r.getFRR() + "	" + r.getFAR()+ "	"  + escribirCarac(caracteristicas, umbrales));
	}
	private String escribirResultado2(Resultados r, Float desv) {
		return new String(r.getFRR() + "	" + r.getFAR() + "	" + desv);
	}
	private String escribirResultado3(Resultados r, Float porcentaje) {
		return new String(r.getFRR() + "	" + r.getFAR() + "	" + porcentaje);
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
		return result;
	}
	
	private String escribirCarac4(List<String> caracteristicas) {
		String result = "", caracAct;
		Iterator<String> it = caracteristicas.iterator();
		while(it.hasNext()){
			caracAct = it.next();
			result += caracAct;
			if(it.hasNext())
				result += ",";
		}
		return result;
	}
	private Tabla buscarTabla(List<Tabla> tablas, String nombre){
		Tabla t = null, act;
		boolean enc = false;
		
		Iterator<Tabla> it = tablas.iterator();
		while(it.hasNext() && !enc){
			act = it.next();
			if(act.getTitulo().equalsIgnoreCase(nombre))
			{
				enc = true;
				t = act; 
			}
		}
		
		return t;
	}
	public void evaluacionManhattan(List<Password> conjunto_entrenamiento, List<Password> conjunto_test){
		//Resultados
		Resultados r;
		//Lista de tablas: una por cada combinacion de caracteristicas
		List<Tabla> listaTablas = new ArrayList<Tabla>();
		//Caracteristicas
		List<String> caracteristicas = new ArrayList<String>();
		//Clasificador
		Clasificador manhattan;
		
      	//Umbrales de las caracteristicas a analizar
      	Map<String, Float> umbrales = new TreeMap<String, Float>();
      	List<Float> umbsDT = new ArrayList<Float>();
      	umbsDT.add(new Float(1.5));
      	umbsDT.add(new Float(2));

      	List<Float> umbsFT1 = new ArrayList<Float>();
      	umbsFT1.add(new Float(1.5));
      	umbsFT1.add(new Float(2));
      	
      	List<Float> umbsFT2 = new ArrayList<Float>();
      	umbsFT2.add(new Float(1.5));
      	umbsFT2.add(new Float(2));
      	
      	List<Float> umbsFT3 = new ArrayList<Float>();
      	umbsFT3.add(new Float(1.5));
      	umbsFT3.add(new Float(2));
      	
      	List<Float> umbsFT4 = new ArrayList<Float>();
      	umbsFT4.add(new Float(1.5));
      	umbsFT4.add(new Float(2));
      	
      	
      	umbrales.put("dt", umbsDT.get(0));
      	umbrales.put("ft1", umbsFT1.get(0));
      	umbrales.put("ft2", umbsFT2.get(0));
      	umbrales.put("ft3", umbsFT3.get(0));
      	umbrales.put("ft4", umbsFT4.get(0));

      	
      	String resultadoAct = "", tituloTabla;
      	//Realizamos una ejecucion del algoritmo para cada combinacion de caracterisiticas
      	for(int i = 0; i < 3; i++){
      		if(i == 0){
      			caracteristicas.add("dt");
      		}
      		else if(i == 1){
      			umbrales.put("dt", umbsDT.get(1));
      		}
      		else if(i == 2){
      			caracteristicas.remove("dt");
      			umbrales.put("dt", umbsDT.get(0));
      		}
      		//Siguiente caracteristica
      		for(int j = 0; j < 3; j++){
      			if(j == 0){
          			caracteristicas.add("ft1");
          		}
      			else if(j == 1){
          			umbrales.put("ft1", umbsFT1.get(1));
          		}
          		else if(j == 2){
          			caracteristicas.remove("ft1");
          	      	umbrales.put("ft1", umbsFT1.get(0));
          		}
      			//Siguiente caracteristica
      			for(int k = 0; k < 3; k++){
      				if(k == 0){
              			caracteristicas.add("ft2");
              		}
      				else if(k == 1){
              			umbrales.put("ft2", umbsFT2.get(1));
              		}
              		else if(k == 2){
              			caracteristicas.remove("ft2");
              	      	umbrales.put("ft2", umbsFT2.get(0));
              		}
      				//Siguiente caracteristica
      				for(int l = 0; l < 3; l++){
          				if(l == 0){
                  			caracteristicas.add("ft3");
                  		}
          				else if(l == 1){
                  			umbrales.put("ft3", umbsFT3.get(1));
                  		}
                  		else if(l == 2){
                  			caracteristicas.remove("ft3");
                  	      	umbrales.put("ft3", umbsFT3.get(0));
                  		}
          				//Siguiente caracteristica
          				for(int m = 0; m < 3; m++){
              				if(m == 0){
                      			caracteristicas.add("ft4");
                      		}
              				else if(m == 1){
                      			umbrales.put("ft4", umbsFT4.get(1));
                      		}
                      		else if(m == 2){
                      			caracteristicas.remove("ft4");
                      	      	umbrales.put("ft4", umbsFT4.get(0));
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
                  	      		
              	      		  	//Escribimos el resultado en una linea junto con el conjunto de caracteristicas
                  	      		resultadoAct += escribirResultado(r, caracteristicas, umbrales);
								//Obtenemos el titulo de la tabla actual
								tituloTabla = escribirCarac4(caracteristicas);
								//Buscamos la tabla con dicho titulo
                  	      		Tabla tabAct = buscarTabla(listaTablas, tituloTabla);
                  	      		//Si todavia no existe una tabla asi, la creamos y la añadimos a la lista
                  	      		if(tabAct == null){
                  	      			Tabla nueva = new Tabla(tituloTabla);
                  	      			nueva.añadirLinea("FRR	FAR	Caracteristicas");
                  	      			nueva.añadirLinea(resultadoAct);
                  	      			listaTablas.add(nueva);
                  	      		}
                  	      		else //Si si existe, almacenamos el resultado en ella
                  	      			tabAct.añadirLinea(resultadoAct);

                  	      		resultadoAct = "";
              				}
              			}
          			}
      			}
      		}
      	}
      	Extraccion ex = new Extraccion();
		//Creamos un txt por cada tabla
		Iterator<Tabla> it = listaTablas.iterator();
      	while(it.hasNext()){
      		Tabla t = it.next();
      		ex.generacion_txt(t.getContenido(), "Manhattan/" + t.getTitulo());
      	}
	}
	
	public void evaluacionMean_StandarDerivation(List<Password> conjunto_entrenamiento, List<Password> conjunto_test){
		//Resultados
		Resultados r;
		//Lista de tablas: una por cada combinacion de caracteristicas
		List<Tabla> listaTablas = new ArrayList<Tabla>();
		//Caracteristicas
		List<String> caracteristicas = new ArrayList<String>();
		//Clasificador
		Clasificador mean_standarDerivation;
		
      	//Desviaciones
      	List<Float> desviaciones = new ArrayList<Float>();
      	
      	for(float i = 0; i <= 3; i = i + new Float(0.05)){
      		desviaciones.add(new Float(i));
      	}
      	String resultadoAct = "", tituloTabla;
      	//Realizamos una ejecucion del algoritmo para cada combinacion de caracterisiticas
      	for(int h = 0; h < desviaciones.size(); h++){
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
                  				
                  				//Si la lista de caracteristicas no esta vacia
                  				if(!caracteristicas.isEmpty()){
                  				//Ejecucion del algoritmo
                      				//Creamos el clasificador con las caracteristicas y dessviacion actuales
                      	      		mean_standarDerivation = new Mean_Standar_deviation(caracteristicas, desviaciones.get(h));
                      	      		//Entrenamos
                      	      		mean_standarDerivation.entrenar(conjunto_entrenamiento);
                      	      		//Testeamos
                      	      		r = mean_standarDerivation.testear(conjunto_test);
                      	      		
                      	      		//Escribimos el resultado en una linea junto con el numero de desviaciones
                      	      		resultadoAct += escribirResultado2(r, desviaciones.get(h));
                      	      		//Obtenemos el titulo de la tabla actual
                      	      		tituloTabla = escribirCarac4(caracteristicas);
                      	      		Tabla tabAct = buscarTabla(listaTablas, tituloTabla);
                      	      		//Si todavia no existe una tabla asi, la creamos y la añadimos a la lista
                      	      		if(tabAct == null){
                      	      			Tabla nueva = new Tabla(tituloTabla);
                      	      			nueva.añadirLinea("FRR	FAR	numDesviaciones");
                      	      			nueva.añadirLinea(resultadoAct);
                      	      			listaTablas.add(nueva);
                      	      		}
                      	      		else //Si si existe, almacenamos el resultado en ella
                      	      			tabAct.añadirLinea(resultadoAct);
                      	      		
                      	      		resultadoAct = "";
                  				}
                      			
                  			}
              			}
          			}
          		}
          	}	
      	}
      	
      	Extraccion ex = new Extraccion();
		Iterator<Tabla> it = listaTablas.iterator();
      	while(it.hasNext()){
      		Tabla t = it.next();
      		ex.generacion_txt(t.getContenido(), "Mean_standarDeviation/" + t.getTitulo());
      	}
	}
	public void evaluacionZscores(List<Password> conjunto_entrenamiento, List<Password> conjunto_test){
		//Resultados
		Resultados r;
		//Caracteristicas
		List<String> caracteristicas = new ArrayList<String>();
		//Clasificador
		Clasificador z_scores;
		//Lista de tablas: una por cada combinacion de caracteristicas
		List<Tabla> listaTablas = new ArrayList<Tabla>();
		
      	//Distribucion estandar
      	Float distribucion = new Float(2);
      	
      	//Porcentajes de aciertos
      	List<Float> porAciertos = new ArrayList<Float>();
      	for(int i = 0; i <= 100; i++){
      		porAciertos.add(new Float(i));
      	}
      	
      	String resultadoAct = "", tituloTabla;
      	//Realizamos una ejecucion del algoritmo para cada combinacion de caracterisiticas
      	for(int g = 0; g <= 100; g++){
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
                  				
                  				//Si la lista de caracteristicas no esta vacia
                  				if(!caracteristicas.isEmpty()){
                  				//Ejecucion del algoritmo
                      				//Creamos el clasificador con las caracteristicas y dessviacion actuales
                      	      		z_scores = new Z_Scores(caracteristicas, distribucion, porAciertos.get(g));
                      	      		//Entrenamos
                      	      		z_scores.entrenar(conjunto_entrenamiento);
                      	      		//Testeamos
                      	      		r = z_scores.testear(conjunto_test);
                      	      		
                      	      		//Escribimos el resultado en una linea junto con el porcentaje de aciertos
                      	      		resultadoAct += escribirResultado3(r, porAciertos.get(g));
                      	      		//Obtenemos el titulo de la tabla actual
                      	      		tituloTabla = escribirCarac4(caracteristicas);
                      	      		Tabla tabAct = buscarTabla(listaTablas, tituloTabla);
                      	      		//Si todavia no existe una tabla asi, la creamos y la añadimos a la lista
                      	      		if(tabAct == null){
                      	      			Tabla nueva = new Tabla(tituloTabla);
                      	      			nueva.añadirLinea("FRR	FAR	%Aciertos");
                      	      			nueva.añadirLinea(resultadoAct);
                      	      			listaTablas.add(nueva);
                      	      		}
                      	      		else //Si si existe, almacenamos el resultado en ella
                      	      			tabAct.añadirLinea(resultadoAct);
                      	      		
                      	      		resultadoAct = "";
                  				}
                      			
                  			}
              			}
          			}
          		}	
          	}
      	}
      	
      	
      	Extraccion ex = new Extraccion();
      	Iterator<Tabla> it = listaTablas.iterator();
      	while(it.hasNext()){
      		Tabla t = it.next();
      		ex.generacion_txt(t.getContenido(), "Zscores/" + t.getTitulo());
      	}
		
	}
	public void generarTxtERR(String carpeta){
		List<String> resultados = new ArrayList<String>();
		resultados.add("Características	Umbral	ERR");
		String tituloTabla, errAct;
		Extraccion ex = new Extraccion();
		//Caracteristicas
		List<String> caracteristicas = new ArrayList<String>();
		
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
              				
              				//Si la lista de caracteristicas no esta vacia
              				if(!caracteristicas.isEmpty()){
              					tituloTabla = escribirCarac4(caracteristicas);
              					errAct = ex.obtenerERR_Umbral("files/resultados/" + carpeta + "/" + tituloTabla + ".txt");
              					resultados.add(tituloTabla+ "	" + errAct); 
              				}
          				}
      				}
      			}
      		}
		}
      	ex.generacion_txt(resultados, "ERR/" + carpeta);		
	}
}
