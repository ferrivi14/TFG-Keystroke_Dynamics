package analisis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import main.Password;

public class Manhattan_detector implements Clasificador {
	//Lista de caracteristicas a evaluar
	private List<String> caracteristicas = new ArrayList<String>();
	//Este mapa almacena los umbrales de las caracteristicas a evaluar
	private Map<String, Float> umbrales = new TreeMap<String, Float>();
	//Lista que almacena la media de cada elemento de todas las caracteristica a evaluar
	private List<Password> media = new ArrayList<Password>();
	//Lista que almacena la desviacion tipica de cada elemento de todas las caracteristica a evaluar
	private List<Password> desviacion = new ArrayList<Password>();
	
	public Manhattan_detector(List<String> features, Map<String, Float> thresholds){
		this.caracteristicas = features;
		this.umbrales = thresholds;
	}
	
	private List<Float> sumaDeListas(List<Float> l1, List<Float> l2){
		Iterator<Float> it1 = l1.iterator();
		Iterator<Float> it2 = l2.iterator();
		//Lista resultante
		List<Float> resultado = new ArrayList<Float>();
		while(it1.hasNext() && it2.hasNext()){
			resultado.add(it1.next() + it2.next());							
		}
		return resultado;
	}
	private List<Float> divisionLista(List<Float> l, int num){
		Iterator<Float> it = l.iterator();
		//Lista resultante
		List<Float> resultado = new ArrayList<Float>();
		while(it.hasNext()){
			resultado.add(it.next() / num);							
		}
		return resultado;
	}
	
	private List<Float> restaMedia(List<Float> l, List<Float> media){
		Iterator<Float> it = l.iterator();
		Iterator<Float> itMedia = media.iterator();
		//Lista resultante
		List<Float> resultado = new ArrayList<Float>();
		while(it.hasNext() && itMedia.hasNext()){
			resultado.add(Math.abs(it.next() - itMedia.next()));							
		}
		return resultado;
	}
	private List<Float> sumaDeRestas(List<Float> l1, List<Float> l2, List<Float> media){
		Iterator<Float> it1 = l1.iterator();
		Iterator<Float> it2 = l2.iterator();
		Iterator<Float> itMedia = media.iterator();
		//Lista resultante
		List<Float> resultado = new ArrayList<Float>();
		
		while(it1.hasNext() && it2.hasNext() && itMedia.hasNext()){
			Float v1 = it1.next();
			Float v2 = it2.next();
			Float vmedia = itMedia.next();
			resultado.add(v1 + Math.abs(v2 - vmedia));							
		}
		return resultado;
	}
	
	private void media(List<Password> conjunto_entrenamiento){
		Iterator<Password> it = conjunto_entrenamiento.iterator();
		
		//Password donde calcularemos el entrenamiento del sujeto actual
		Password passSujAct = null;
		String sujAct = "";
		int numMuestras = 0;
		//Recorremos todas las muestras del cojunto de entrenamiento
		while(it.hasNext()){
			Password act = it.next();
			
			//Si seguimos con el mismo sujeto
			if(sujAct.equalsIgnoreCase(act.getSubject())){
				numMuestras++;
				Iterator<String> itCar = this.caracteristicas.iterator();
				//Recorremos todas las caracteristicas a contemplar y añadimos sus datos al entrenamiento del sujeto actual
				while(itCar.hasNext()){
					//Obtenemos la caracteristica
					String carAct = itCar.next();
					
					//Sumamos los valores de la lista del password actual al entrenamiento del sujeto
					//Dwell Time
					if(carAct.equalsIgnoreCase("dt")){
						passSujAct.setDwell_List(sumaDeListas(passSujAct.getDwell_List(), act.getDwell_List()));
					}
					//Flight times
					else if(carAct.equalsIgnoreCase("ft1")){
						passSujAct.setFlight_up_down_List(sumaDeListas(passSujAct.getFlight_up_down_List(), act.getFlight_up_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft2")){
						passSujAct.setFlight_up_up_List(sumaDeListas(passSujAct.getFlight_up_up_List(), act.getFlight_up_up_List()));				
					}
					else if(carAct.equalsIgnoreCase("ft3")){
						passSujAct.setFlight_down_down_List(sumaDeListas(passSujAct.getFlight_down_down_List(), act.getFlight_down_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft4")){
						passSujAct.setFlight_down_up_List(sumaDeListas(passSujAct.getFlight_down_up_List(), act.getFlight_down_up_List()));
					}
					//N-graphs
					else if(carAct.equalsIgnoreCase("ng")){
						passSujAct.setNGraph_List(sumaDeListas(passSujAct.getNGraph_List(), act.getNGraph_List()));
					}
				}
			}
			//Si no, significa que pasamos a un nuevo sujeto
			else{
				//Primero, comprobamos si ha habido algun sujeto antes
				if(passSujAct != null)//Si existe uno anterior... lo insertamos en la lista de entrenamiento
				{
					Iterator<String> itCar = this.caracteristicas.iterator();
					//Recorremos todas las caracteristicas a contemplar y y dividimos cada uno de sus datos entre el numero de muestras de ese sujeto
					while(itCar.hasNext()){
						//Obtenemos la caracteristica
						String carAct= (String) itCar.next();
						//Dividimos cada uno de sus datos entre el numero de muestras de ese sujeto
						//Dwell Time
						if(carAct.equalsIgnoreCase("dt")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getDwell_List(), numMuestras));
						}
						//Flight times
						else if(carAct.equalsIgnoreCase("ft1")){
							passSujAct.setFlight_up_down_List(divisionLista(passSujAct.getFlight_up_down_List(), numMuestras));
						}
						else if(carAct.equalsIgnoreCase("ft2")){
							passSujAct.setFlight_up_up_List(divisionLista(passSujAct.getFlight_up_up_List(), numMuestras));				
						}
						else if(carAct.equalsIgnoreCase("ft3")){
							passSujAct.setFlight_down_down_List(divisionLista(passSujAct.getFlight_down_down_List(), numMuestras));
						}
						else if(carAct.equalsIgnoreCase("ft4")){
							passSujAct.setFlight_down_up_List(divisionLista(passSujAct.getFlight_down_up_List(), numMuestras));
						}
						//N-graphs
						else if(carAct.equalsIgnoreCase("ng")){
							passSujAct.setNGraph_List(divisionLista(passSujAct.getNGraph_List(), numMuestras));
						}
					}
					this.media.add(passSujAct);
				}
					
				//Despues, reinicializamos el password actual con el nuevo sujeto
				passSujAct = new Password(act.getSubject());
				Iterator<String> itCar = this.caracteristicas.iterator();
				//Recorremos todas las caracteristicas a contemplar y y dividimos cada uno de sus datos entre el numero de muestras de ese sujeto
				while(itCar.hasNext()){
					//Obtenemos la caracteristica
					String carAct= (String) itCar.next();
					//Dividimos cada uno de sus datos entre el numero de muestras de ese sujeto
					//Dwell Time
					if(carAct.equalsIgnoreCase("dt")){
						passSujAct.setDwell_List(act.getDwell_List());
					}
					//Flight times
					else if(carAct.equalsIgnoreCase("ft1")){
						passSujAct.setFlight_up_down_List(act.getFlight_up_down_List());
					}
					else if(carAct.equalsIgnoreCase("ft2")){
						passSujAct.setFlight_up_up_List(act.getFlight_up_up_List());				
					}
					else if(carAct.equalsIgnoreCase("ft3")){
						passSujAct.setFlight_down_down_List(act.getFlight_down_down_List());
					}
					else if(carAct.equalsIgnoreCase("ft4")){
						passSujAct.setFlight_down_up_List(act.getFlight_down_up_List());
					}
					//N-graphs
					else if(carAct.equalsIgnoreCase("ng")){
						passSujAct.setNGraph_List(act.getNGraph_List());
					}
				}
				
				sujAct = passSujAct.getSubject();
				numMuestras = 1;
			}
			//Si es el ultimo elemento, insertamos
			if(!it.hasNext()){
				//Primero, comprobamos si ha habido algun sujeto antes
				if(passSujAct != null)//Si existe uno anterior... lo insertamos en la lista de entrenamiento
				{
					Iterator<String> itCar = this.caracteristicas.iterator();
					//Recorremos todas las caracteristicas a contemplar y y dividimos cada uno de sus datos entre el numero de muestras de ese sujeto
					while(itCar.hasNext()){
						//Obtenemos la caracteristica
						String carAct= (String) itCar.next();
						//Dividimos cada uno de sus datos entre el numero de muestras de ese sujeto
						//Dwell Time
						if(carAct.equalsIgnoreCase("dt")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getDwell_List(), numMuestras));
						}
						//Flight times
						else if(carAct.equalsIgnoreCase("ft1")){
							passSujAct.setFlight_up_down_List(divisionLista(passSujAct.getFlight_up_down_List(), numMuestras));
						}
						else if(carAct.equalsIgnoreCase("ft2")){
							passSujAct.setFlight_up_up_List(divisionLista(passSujAct.getFlight_up_up_List(), numMuestras));				
						}
						else if(carAct.equalsIgnoreCase("ft3")){
							passSujAct.setFlight_down_down_List(divisionLista(passSujAct.getFlight_down_down_List(), numMuestras));
						}
						else if(carAct.equalsIgnoreCase("ft4")){
							passSujAct.setFlight_down_up_List(divisionLista(passSujAct.getFlight_down_up_List(), numMuestras));
						}
						//N-graphs
						else if(carAct.equalsIgnoreCase("ng")){
							passSujAct.setNGraph_List(divisionLista(passSujAct.getNGraph_List(), numMuestras));
						}
					}
					media.add(passSujAct);
				}
			}
		}
	}
	private void desviacionTipica(List<Password> conjunto_entrenamiento){
				
		//Password donde calcularemos el entrenamiento del sujeto actual
		Password passSujAct = null;
		String sujAct = "";
		int numMuestras = 0;
		Password passMedia = null;
		
		//Recorremos todas las muestras del cojunto de entrenamiento
		Iterator<Password> it = conjunto_entrenamiento.iterator();
		Iterator<Password> itMedia = this.media.iterator();
		while(it.hasNext()){
			Password act = it.next();
			//Si seguimos con el mismo sujeto
			if(sujAct.equalsIgnoreCase(act.getSubject())){
				numMuestras++;
				Iterator<String> itCar = this.caracteristicas.iterator();
				//Recorremos todas las caracteristicas a contemplar y añadimos sus datos al entrenamiento del sujeto actual
				while(itCar.hasNext()){
					//Obtenemos la caracteristica
					String carAct = itCar.next();
					
					//Sumamos los valores de la lista del password actual al entrenamiento del sujeto
					//Dwell Time
					if(carAct.equalsIgnoreCase("dt")){
						passSujAct.setDwell_List(sumaDeRestas(passSujAct.getDwell_List(), act.getDwell_List(), passMedia.getDwell_List()));
					}
					//Flight times
					else if(carAct.equalsIgnoreCase("ft1")){
						passSujAct.setFlight_up_down_List(sumaDeRestas(passSujAct.getFlight_up_down_List(), act.getFlight_up_down_List(), passMedia.getFlight_up_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft2")){
						passSujAct.setFlight_up_up_List(sumaDeRestas(passSujAct.getFlight_up_up_List(), act.getFlight_up_up_List(), passMedia.getFlight_up_up_List()));				
					}
					else if(carAct.equalsIgnoreCase("ft3")){
						passSujAct.setFlight_down_down_List(sumaDeRestas(passSujAct.getFlight_down_down_List(), act.getFlight_down_down_List(), passMedia.getFlight_down_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft4")){
						passSujAct.setFlight_down_up_List(sumaDeRestas(passSujAct.getFlight_down_up_List(), act.getFlight_down_up_List(), passMedia.getFlight_down_up_List()));
					}
					//N-graphs
					else if(carAct.equalsIgnoreCase("ng")){
						passSujAct.setNGraph_List(sumaDeRestas(passSujAct.getNGraph_List(), act.getNGraph_List(), passMedia.getNGraph_List()));
					}
				}
			}
			//Si no, significa que pasamos a un nuevo sujeto
			else{
				//Primero, comprobamos si ha habido algun sujeto antes
				if(passSujAct != null)//Si existe uno anterior... lo insertamos en la lista de entrenamiento
				{
					Iterator<String> itCar = this.caracteristicas.iterator();
					//Recorremos todas las caracteristicas a contemplar y y dividimos cada uno de sus datos entre el numero de muestras de ese sujeto
					while(itCar.hasNext()){
						//Obtenemos la caracteristica
						String carAct= (String) itCar.next();
						//Dividimos cada uno de sus datos entre el numero de muestras de ese sujeto
						//Dwell Time
						if(carAct.equalsIgnoreCase("dt")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getDwell_List(), numMuestras - 1));
						}
						//Flight times
						else if(carAct.equalsIgnoreCase("ft1")){
							passSujAct.setFlight_up_down_List(divisionLista(passSujAct.getFlight_up_down_List(), numMuestras - 1));
						}
						else if(carAct.equalsIgnoreCase("ft2")){
							passSujAct.setFlight_up_up_List(divisionLista(passSujAct.getFlight_up_up_List(), numMuestras - 1));				
						}
						else if(carAct.equalsIgnoreCase("ft3")){
							passSujAct.setFlight_down_down_List(divisionLista(passSujAct.getFlight_down_down_List(), numMuestras - 1));
						}
						else if(carAct.equalsIgnoreCase("ft4")){
							passSujAct.setFlight_down_up_List(divisionLista(passSujAct.getFlight_down_up_List(), numMuestras - 1));
						}
						//N-graphs
						else if(carAct.equalsIgnoreCase("ng")){
							passSujAct.setNGraph_List(divisionLista(passSujAct.getNGraph_List(), numMuestras - 1));
						}
					}
					this.desviacion.add(passSujAct);
				}
					
				//Despues, reinicializamos el password actual con el nuevo sujeto
				passSujAct = new Password(act.getSubject());
				sujAct = passSujAct.getSubject();
				passMedia = itMedia.next();
				Iterator<String> itCar2 = this.caracteristicas.iterator();
				while(itCar2.hasNext()){
					//Obtenemos la caracteristica
					String carAct = itCar2.next();
					//Dwell Time
					if(carAct.equalsIgnoreCase("dt")){
						passSujAct.setDwell_List(restaMedia(act.getDwell_List(), passMedia.getDwell_List()));
					}
					//Flight times
					else if(carAct.equalsIgnoreCase("ft1")){
						passSujAct.setFlight_up_down_List(restaMedia(act.getFlight_up_down_List(), passMedia.getFlight_up_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft2")){
						passSujAct.setFlight_up_up_List(restaMedia(act.getFlight_up_up_List(), passMedia.getFlight_up_up_List()));				
					}
					else if(carAct.equalsIgnoreCase("ft3")){
						passSujAct.setFlight_down_down_List(restaMedia(act.getFlight_down_down_List(), passMedia.getFlight_down_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft4")){
						passSujAct.setFlight_down_up_List(restaMedia(act.getFlight_down_up_List(), passMedia.getFlight_down_up_List()));
					}
					//N-graphs
					else if(carAct.equalsIgnoreCase("ng")){
						passSujAct.setNGraph_List(restaMedia(act.getNGraph_List(), passMedia.getNGraph_List()));
					}
				}
				numMuestras = 1;
			}
			//Si es el ultimo sujeto
			if(!it.hasNext()){
				//Primero, comprobamos si ha habido algun sujeto antes
				if(passSujAct != null)//Si existe uno anterior... lo insertamos en la lista de entrenamiento
				{
					Iterator<String> itCar = this.caracteristicas.iterator();
					//Recorremos todas las caracteristicas a contemplar y y dividimos cada uno de sus datos entre el numero de muestras de ese sujeto
					while(itCar.hasNext()){
						//Obtenemos la caracteristica
						String carAct= (String) itCar.next();
						//Dividimos cada uno de sus datos entre el numero de muestras de ese sujeto
						//Dwell Time
						if(carAct.equalsIgnoreCase("dt")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getDwell_List(), numMuestras - 1));
						}
						//Flight times
						else if(carAct.equalsIgnoreCase("ft1")){
							passSujAct.setFlight_up_down_List(divisionLista(passSujAct.getFlight_up_down_List(), numMuestras - 1));
						}
						else if(carAct.equalsIgnoreCase("ft2")){
							passSujAct.setFlight_up_up_List(divisionLista(passSujAct.getFlight_up_up_List(), numMuestras - 1));				
						}
						else if(carAct.equalsIgnoreCase("ft3")){
							passSujAct.setFlight_down_down_List(divisionLista(passSujAct.getFlight_down_down_List(), numMuestras - 1));
						}
						else if(carAct.equalsIgnoreCase("ft4")){
							passSujAct.setFlight_down_up_List(divisionLista(passSujAct.getFlight_down_up_List(), numMuestras - 1));
						}
						//N-graphs
						else if(carAct.equalsIgnoreCase("ng")){
							passSujAct.setNGraph_List(divisionLista(passSujAct.getNGraph_List(), numMuestras - 1));
						}
					}
					this.desviacion.add(passSujAct);
				}
			}
		}
	}


	//Funcion con la que entrenamos la dia y desviacion tipica de cada elemento de cada caracteristica
	public void entrenar(List<Password> conjunto_entrenamiento) {
		media(conjunto_entrenamiento);
		desviacionTipica(conjunto_entrenamiento);
	}
	
	//Dado un sujeto, obtenemos el password que contiene las listas de sus medias
	private Password obtenerPasswordMedia(String sujeto){
		Password p = null;
		boolean encontrado = false;
		Iterator<Password> it = this.media.iterator();
		while(it.hasNext() && !encontrado){
			p = it.next();
			if(p.getSubject().equalsIgnoreCase(sujeto))
				encontrado = true;
		}
		if(encontrado)
			return p;
		else
			return null;
	}
	
	//Dado un sujeto, obtenemos el password que contiene las listas de sus desviaciones tipicas
	private Password obtenerPasswordDesviacion(String sujeto){
		Password p = null;
		boolean encontrado = false;
		Iterator<Password> it = this.desviacion.iterator();
		while(it.hasNext() && !encontrado){
			p = it.next();
			if(p.getSubject().equalsIgnoreCase(sujeto))
				encontrado = true;
		}
		if(encontrado)
			return p;
		else
			return null;
	}
	
	private Float distanciaEntreElementos(Float valor, Float media, Float desviacion){
		return (valor - media)/desviacion;
	}
	//Calcula la distancia en unidades de desviación estándar entre la plantilla y la muestra se calcula mediante
	private Float distanciaEntreCaracteristicas(List<Float> l, List<Float> media, List<Float> desv){
		//Distancia entre una caracteristica a la media en desviaciones tipicas
		Float distancia = new Float(0);
		//Numero de elementos por caracteristica
		int numElem = 0;
		//Iteradores de las listas de datos
		Iterator<Float> itList = l.iterator();
		Iterator<Float> itMedia = media.iterator();
		Iterator<Float> itDesv = desv.iterator();
		
		//Sumatorio de las distancias entre cada elemento con la media
		while(itList.hasNext() && itMedia.hasNext() && itDesv.hasNext()){
			distancia += distanciaEntreElementos(itList.next(), itMedia.next(), itDesv.next());
			numElem++;
		}
		//Dividimos la distancia entre el numero de elementos
		distancia = distancia/numElem;
		
		return distancia;
	}
	//Comprueba si una password es realmente un sujeto o no
	public boolean verificarPassword(Password p, String sujeto) throws Exception{
		//
		boolean valido = true;
		//Distancia entre los password
		Float distancia = new Float(0);
		
		//Obtenemos la media y desviacion tipica
		Password media = obtenerPasswordMedia(sujeto);
		Password desv = obtenerPasswordDesviacion(sujeto);
		
		//Si no existen la media ni la desviacion,  lanzamos una excepcion
		if(media == null || desv == null)
			throw(new Exception("No existe un entrenamiento anterior sobre este sujeto"));
		
		//Recorremos todas las caracteristicas a contemplar
		Iterator<String> itCar = this.caracteristicas.iterator();
		while(itCar.hasNext() && valido){
			//Obtenemos la caracteristica
			String carAct = itCar.next();
			//Dwell Time
			if(carAct.equalsIgnoreCase("dt")){
				
				distancia = distanciaEntreCaracteristicas(p.getDwell_List(), media.getDwell_List(), desv.getDwell_List());
				//System.out.println(distancia);
				//Comprobamos si el password es valido o no
				if(distancia > this.umbrales.get("dt")){
					valido = false;
				}
			}
			//Flight times
			else if(carAct.equalsIgnoreCase("ft1")){
				distancia = distanciaEntreCaracteristicas(p.getFlight_up_down_List(), media.getFlight_up_down_List(), desv.getFlight_up_down_List());
				//Comprobamos si el password es valido o no
				if(distancia > this.umbrales.get("ft1")){
					valido = false;
				}
			}
			else if(carAct.equalsIgnoreCase("ft2")){
				distancia = distanciaEntreCaracteristicas(p.getFlight_up_up_List(), media.getFlight_up_up_List(), desv.getFlight_up_up_List());
				//Comprobamos si el password es valido o no
				if(distancia > this.umbrales.get("ft2")){
					valido = false;
				}
			}
			else if(carAct.equalsIgnoreCase("ft3")){
				distancia = distanciaEntreCaracteristicas(p.getFlight_down_down_List(), media.getFlight_down_down_List(), desv.getFlight_down_down_List());
				//Comprobamos si el password es valido o no
				if(distancia > this.umbrales.get("ft3")){
					valido = false;
				}
			}
			else if(carAct.equalsIgnoreCase("ft4")){
				distancia = distanciaEntreCaracteristicas(p.getFlight_down_up_List(), media.getFlight_down_up_List(), desv.getFlight_down_up_List());
				//Comprobamos si el password es valido o no
				if(distancia > this.umbrales.get("ft4")){
					valido = false;
				}
			}
			//N-graphs
			else if(carAct.equalsIgnoreCase("ng")){
				distancia = distanciaEntreCaracteristicas(p.getNGraph_List(), media.getNGraph_List(), desv.getNGraph_List());
				//Comprobamos si el password es valido o no
				if(distancia > this.umbrales.get("ng")){
					valido = false;
				}
			}
		}
		
		return valido;
	}
	//Comprueba cada uno de los password con el resto
	public Resultados testear(List<Password> conjunto_test) {
		int numPasswords = 0;
		float falsosAceptados = 0, falsosRechazados = 0;
		String sujetoAct = "";
		boolean valido;
		
		//Recorremos la lista de medias para obtener todos los sujetos
		Iterator<Password> it = this.media.iterator();
		while(it.hasNext()){
			sujetoAct = it.next().getSubject();
			//Recorremos todos las password del conjunto de test
			Iterator<Password> it2 = conjunto_test.iterator();
			while(it2.hasNext()){
				//Aumentamos el numero de passwords analizados
				numPasswords++;
				Password act = it2.next();
				//System.out.println("Sujeto: " + sujetoAct + " Password: "+ act.getSubject());
				try{
					valido = verificarPassword(act, sujetoAct);
					//Si accepta el password pero los sujetos son distintos, aumentamos los falsos acertados
					if(valido && !sujetoAct.equalsIgnoreCase(act.getSubject()))
						falsosAceptados++;
					//Si rechaza el password pero los sujetos son iguales, aumentamos los falsos rechazados
					else if(!valido && sujetoAct.equalsIgnoreCase(act.getSubject()))
						falsosRechazados++;
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}
		//System.out.println(falsosRechazados +" "+ falsosAceptados +" "+ numPasswords);
		return new Resultados(falsosRechazados/numPasswords, falsosAceptados/numPasswords);
	}
}
