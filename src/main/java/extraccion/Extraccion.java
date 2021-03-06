package extraccion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.Password;


public class Extraccion{
	//Funcion que extrae de una linea de caracteres los diferentes tiempos de presion
	public List<Float> dwell_time(String line){
		//Creamos la lista
		List<Float> lista = new ArrayList<Float>();
		//Separamos los datos inluidos en la linea introducida por parametro
		String separador = ",";
		String[] columnas = line.split(separador);
		//Recorremos el array, quedandonos con las columnas que contienen los datos que deseamos
        for(int i = 3; i < columnas.length; i = i + 3){
        	lista.add(Float.parseFloat(columnas[i]));
        }
		//Devolvemos la lista
		return lista;		
	}
	//Funcion que extrae de una linea de caracteres los diferentes tiempos de vuelo (Up-Down)
	public List<Float> flight_time_up_down(String line){
		//Creamos la lista
		List<Float> lista = new ArrayList<Float>();
		//Separamos los datos inluidos en la linea introducida por parametro
		String separador = ",";
		String[] columnas = line.split(separador);
		//Recorremos el array, quedandonos con las columnas que contienen los datos que deseamos
        for(int i = 5; i < columnas.length; i = i + 3){
        	lista.add(Float.parseFloat(columnas[i]));
        }
		//Devolvemos la lista
		return lista;			
	}
	//Funcion que extrae de una linea de caracteres los diferentes tiempos de vuelo (Up-Up)
	public List<Float> flight_time_up_up(String line){
		//Creamos la lista
		List<Float> lista = new ArrayList<Float>();
		//Separamos los datos inluidos en la linea introducida por parametro
		String separador = ",";
		String[] columnas = line.split(separador);
		//Recorremos el array, quedandonos con las columnas que contienen los datos que deseamos
        for(int i = 5; i < columnas.length; i = i + 3){
        	if(i + 1 < columnas.length)
        		lista.add(Float.parseFloat(columnas[i]) + Float.parseFloat(columnas[i+1]));
        }
		//Devolvemos la lista
		return lista;
		
	}
	//Funcion que extrae de una linea de caracteres los diferentes tiempos de vuelo (Down-Down)
	public List<Float> flight_time_down_down(String line){
		//Creamos la lista
		List<Float> lista = new ArrayList<Float>();
		//Separamos los datos inluidos en la linea introducida por parametro
		String separador = ",";
		String[] columnas = line.split(separador);
		//Recorremos el array, quedandonos con las columnas que contienen los datos que deseamos
        for(int i = 4; i < columnas.length; i = i + 3){
        	lista.add(Float.parseFloat(columnas[i]));
        }
		//Devolvemos la lista
		return lista;
	}
	//Funcion que extrae de una linea de caracteres los diferentes tiempos de vuelo (Down-Up)
	public List<Float> flight_time_down_up(String line){
		//Creamos la lista
		List<Float> lista = new ArrayList<Float>();
		//Separamos los datos inluidos en la linea introducida por parametro
		String separador = ",";
		String[] columnas = line.split(separador);
		//Recorremos el array, quedandonos con las columnas que contienen los datos que deseamos
        for(int i = 4; i < columnas.length; i = i + 3){
        	if(i + 2 < columnas.length)
        		lista.add(Float.parseFloat(columnas[i]) + Float.parseFloat(columnas[i+2]));
        }
		//Devolvemos la lista
		return lista;
	}
	//Funcion que extrae de una linea de caracteres los diferentes n-graphs dependiendo de la n introducida por parametro
	public  List<Float> nGraph(String line, int n){
		//Creamos la lista
		List<Float> lista = new ArrayList<Float>();
		//Separamos los datos inluidos en la linea introducida por parametro
		String separador = ",";
		String[] columnas = line.split(separador);
		//Recorremos el array, quedandonos con las columnas que contienen los DD	
        for(int i = 4; i < columnas.length; i = i + 3){
        	if(n == 2){
        		lista.add(Float.parseFloat(columnas[i]));
        	}
        	else if(n > 2){
        		//Inicialmente siempre tendra al menos el valor de la casilla inicial
        		Float graph = Float.parseFloat(columnas[i]);
	        	//El numero de casillas a avanzar es igual a (n-2)*3
	        	if((i + 3*(n - 2)) < columnas.length){
	        		int avn = (n - 2);
	        		for(int j = 1; j <= avn; j++)
	        			graph += Float.parseFloat(columnas[i + 3*j]);
	        		lista.add(graph);
	        	}
        	}
        }
		//Devolvemos la lista
		return lista;
	}
	
	//Funcion general de extraccion desde la cual se extraen todos los datos y se almacenan en una lista de Password
	public List<Password> extraccion_completa(String csvFile){
		
        BufferedReader br = null;
        String line = "";
        String separador = ",";
        //Lista de Password
        List<Password> lista_contraseñas = new ArrayList<Password>();
        
        try 
        {
            br = new BufferedReader(new FileReader(csvFile));
            //Si existe una primera linea, continuamos leyendo porque en ella se guardan los titulos de las columnas
            if((line = br.readLine()) != null){
            	//Mientras que exista una linea, seguimos leyendo
	            while ((line = br.readLine()) != null) {	
	                //Usamos la coma como separador
	                String[] columnas = line.split(separador);
	                //Creamos una Password con los valores de usuario, sesion y repeticion
	                Password p = new Password(columnas[0], Integer.parseInt(columnas[1]), Integer.parseInt(columnas[2]));
	                
	                //Extraemos los tiempos de presion y los guardamos en la Password
	                p.setDwell_List(dwell_time(line));
	                //Extraemos los diferentes tipos de tiempos de vuelo y los guardamos en la Password
	                p.setFlight_up_down_List(flight_time_up_down(line));
	                p.setFlight_down_down_List(flight_time_down_down(line));
	                p.setFlight_up_up_List(flight_time_up_up(line));
	                p.setFlight_down_up_List(flight_time_down_up(line));
	                //Extraemos los n-graphs y los guardamos en la Password
	                p.setNGraph_List(nGraph(line, 3));
	                
	                //Añadimos la contraseña actual a la lista de contraseñas
	                lista_contraseñas.add(p);
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
		return lista_contraseñas;
	}
	//Funcion que genera un archivo .txt a traves de una lista de string
	public void generacion_txt(List<String> lista, String nombre){
		String ruta = "files/resultados/" + nombre + ".txt";
        BufferedWriter bw = null;

        try 
        {
            bw = new BufferedWriter(new FileWriter(ruta));
            Iterator<String> it = lista.iterator();
            
	    	while(it.hasNext()){
	    		String linea = it.next();
	    		bw.write(linea + "\r\n");
	    	}
        }
        catch (FileNotFoundException e) {
        	//e.printStackTrace();
            System.err.println(e.getMessage());
        }
        catch (IOException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
        }
        finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.err.println(e.getMessage());
                }
            }
        }
	}
	
	//Funcion que dado un fichero de resultados, devuelve el mejor ERR y el umbral al que corresponde en una cadena de caracteres
	public String obtenerERR_Umbral(String csvFile){
		
        BufferedReader br = null;
        String line = "";
        String separador = "	";
        
        Float FRR_actual;
        Float FAR_actual;
        Float ERR = null;
        Float difAct;
        Float difMenor = Float.MAX_VALUE;
        String umbAct;
        String umbDefinitivo = null;
        
        try 
        {
            br = new BufferedReader(new FileReader(csvFile));
            //Si existe una primera linea, continuamos leyendo porque en ella se guardan los titulos de las columnas
            if((line = br.readLine()) != null){
            	//Mientras que exista una linea, seguimos leyendo
	            while ((line = br.readLine()) != null) {	
	                //Usamos la tabulacion como separador
	                String[] columnas = line.split(separador);
	                //Obtenemos los valores actuales
	                FRR_actual = Float.parseFloat(columnas[0]);
	                FAR_actual = Float.parseFloat(columnas[1]);
	                umbAct = columnas[2];
	                //Comprobamos si su diferencia es la menor hasta el momento
	                difAct = Math.abs(FRR_actual - FAR_actual);
	                if(difAct < difMenor){
	                	difMenor = difAct;
	                	ERR = (FRR_actual + FAR_actual)/new Float(2);
	                	umbDefinitivo = umbAct;
	                }
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
		return (umbDefinitivo + "	" + ERR);
	}
}
