package muestreo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Muestreo {
	//Funcion general de extraccion desde la cual se extraen todos los datos y se almacenan en una lista de Password
	private List<String> extraccion(String file){
		
        BufferedReader br = null;
        String line = "";
        //Listas de datos
        List<String> lista = new ArrayList<String>();
        
        try 
        {
            br = new BufferedReader(new FileReader(file));
            //Si existe una primera linea, continuamos leyendo porque en ella se guardan los titulos de las columnas
            if((line = br.readLine()) != null){
            	//Guardamos la cabecera con los titulos de las columnas en ambas listas
            	lista.add(line);
            	//Mientras que exista una linea, seguimos leyendo
	            while ((line = br.readLine()) != null) {	
	                //Añadimos la linea actual a la lista
	                lista.add(line);
	            }
        	}   
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return lista;
	}
	
	public void muestreo(String file, Float porcentaje, List<String> lista_entrenamiento, List<String> lista_test){
		//Obtenemos la lista de datos que contiene el fichero csv
		List<String> lista = extraccion(file);
		//Ahora dividiremos la lista en 2 partes: entrenamiento y test
		//Seleccionaremos aleatoriamente el porcentaje deseado de la lista y lo extraeremos a la lista de entrenamiento
		//La lista resultante tras extraer los datos de entrenamiento sera la lista de test
		
		//Calculamos el numero de muestras de entrenamiento
		int elem_extraer = (int) (400 * (porcentaje/100));
		int extraidos = 0, sujeto_actual = 1, limite_inf = 1, limite_sup = 400, numeroAleatorio;
		//Mientras que no hayamos obtenido las muestras de entrenamiento de todos los sujetos
		while(sujeto_actual <= 51){
			while(extraidos < elem_extraer){
				numeroAleatorio = (int) Math.floor((Math.random()*limite_sup+limite_inf));
				//Si la muestra ya ha sido extraida, buscamos otra
				while(lista_entrenamiento.contains(lista.get(numeroAleatorio))){
					numeroAleatorio = (int) (Math.random()*limite_sup+limite_inf);
				}
				//Añadimos la muestra a la lista de entrenamiento
				lista_entrenamiento.add(lista.get(numeroAleatorio));
				extraidos++;
			}
			//Actualizamos los limites, el sujeto actual y su numero de extraidos
			limite_inf += 400;
			sujeto_actual++;
			extraidos = 0;
		}
		//Eliminamos las muestras seleccionadas
		Iterator<String> it = lista_entrenamiento.iterator();
		while(it.hasNext()){
			String linea = it.next();
			lista.remove(lista.indexOf(linea));
		}
		
		lista_test = lista_entrenamiento;
		//De esta forma, lista_test contiene el porcentaje de muestras correspondiente a los test
	}
	
	private void generacion_conjuntos(List<String> lista_entrenamiento, List<String> lista_test){
		
	}
}
