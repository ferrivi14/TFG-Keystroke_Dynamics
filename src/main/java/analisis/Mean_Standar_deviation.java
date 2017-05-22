package analisis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import main.Password;

public class Mean_Standar_deviation  implements Clasificador{
	//Lista de caracteristicas a evaluar
	private List<String> caracteristicas = new ArrayList<String>();
	//Numero de desviaciones que se le sumaran a la media para calcular los umbrales
	private Float numDesviaciones;
	//Lista de password que almacena el valor medio de cada una de las latencias a evaluar de entre todos los sujetos del entrenamiento
	private List<Password> M = new ArrayList<Password>();
	//Lista de Medias de las distancias de todos los password de entrenamiento a M
	private List<Float> medias = new ArrayList<Float>();
	//Listan de Desviaciones tipicas de las distancias de todos los password de entrenamiento a M
	private List<Float> desviaciones = new ArrayList<Float>();
	//Este mapa almacena los umbrales de todos los sujetos
	private Map<String, Float> umbrales = new TreeMap<String, Float>();
	
	public Mean_Standar_deviation(List<String> features, Float deviation){
		this.caracteristicas = features;
		this.numDesviaciones = deviation;
		
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
	//Divide todos los valores de una lista entre un numero
	private List<Float> divisionLista(List<Float> l, int num){
		Iterator<Float> it = l.iterator();
		//Lista resultante
		List<Float> resultado = new ArrayList<Float>();
		while(it.hasNext()){
			resultado.add(it.next() / num);							
		}
		return resultado;
	}

	//Calcula la media de una lista de paswords
	private void calculoMs(List<Password> conjunto_entrenamiento){
		//Iterador que recorre todo el conjunto de entrenamiento
		Iterator<Password> it = conjunto_entrenamiento.iterator();
		
		//Password donde calcularemos el entrenamiento del sujeto actual
		Password passSujAct = null;
		String sujAct = "";
		int numMuestras = 0;
		//Recorremos todas las muestras del cojunto de entrenamiento
		while(it.hasNext()){
			//Password actual del conjunto de entrenamiento
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
					this.M.add(passSujAct);
				}
					
				//Despues, reinicializamos el password actual con el nuevo sujeto
				passSujAct = new Password(act.getSubject());
				sujAct = passSujAct.getSubject();
				//Recorremos todas las caracteristicas a contemplar y
				//dividimos cada uno de sus datos entre el numero de muestras de ese sujeto
				Iterator<String> itCar = this.caracteristicas.iterator();
				while(itCar.hasNext()){
					//Obtenemos la caracteristica
					String carAct= (String) itCar.next();
					//Al ser el primer password del sujeto actual, sus listas de datos seran las mismas
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
				//Reestablecemos el numero de muestras
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
					M.add(passSujAct);
				}
			}
		}
	}

	private List<List<Float>> distanciasEntrenamiento(List<Password> conjunto_entrenamiento) {
		//Lista que almacenará todas las listas de distancias
		//Cada lista de distancias corresponderá a un unico sujeto
		List<List<Float>> listaDeListas = new ArrayList<List<Float>>();
		//Lista actual
		List<Float> listaAct = new ArrayList<Float>();
		//M actual
		Password mAct = null;
		//Password actual
		Password act;
		//Sujeto actual
		String sujAct = "";
		//Recorremos todas las muestras del conjunto de entrenamiento
		Iterator<Password> it = conjunto_entrenamiento.iterator();
		while(it.hasNext()){
			//Obtenemos el siguiente password
			act = it.next();
			//Si seguimos con el mismo sujeto
			if(sujAct.equalsIgnoreCase(act.getSubject())){
				listaAct.add(distanciaEntrePasswords(act, mAct));
			}
			//Si no, significa que pasamos a un nuevo sujeto
			else{
				//Primero, comprobamos si ha habido algun sujeto antes
				if(mAct != null)//Si existe uno anterior... 
				{
					//Insertamos su lista de distancias en la lista de listas
					listaDeListas.add(listaAct);
				}
				//Despues, obtenemos el M del nuevo sujeto
				mAct = obtenerM(act.getSubject());
				//Reasignamos el nuevo sujeto
				sujAct = mAct.getSubject();
				//Reinicializamos la listaAct
				listaAct = new ArrayList<Float>();
				//Añadimos la distancia con la muestra actual
				listaAct.add(distanciaEntrePasswords(act, mAct));
			}
			//Si es el ultimo elemento, insertamos
			if(!it.hasNext()){
				//Primero, comprobamos si ha habido algun sujeto antes
				if(mAct != null)//Si existe uno anterior...
				{
					//Insertamos su lista de distancias en la lista de listas
					listaDeListas.add(listaAct);
				}
			}
		}
		
		return listaDeListas;
	}
	//Calcula la media de una lista de valores
	private Float media(List<Float> lista) {
		Float media = new Float(0);
		//Recorremos la lista
		Iterator<Float> it = lista.iterator();
		while(it.hasNext())
			media += it.next();

		return (media / lista.size());
	}
	//Calcula la desviacion tipica de una lista de valores
	private Float desviacionTipica(List<Float> lista, Float media) {
		Float desv = new Float(0), resultadoAct;
		//Recorremos la lista
		Iterator<Float> it = lista.iterator();
		while(it.hasNext()){
			resultadoAct = (float) Math.pow((it.next() - media), 2);
			desv += resultadoAct;
		}
		desv = (float) Math.sqrt(desv / (lista.size() - 1));
		return desv;
	}
	//Calcula todas las medias y desviaciones tipicasde las distancias de todos 
	//los sujetos de entrenamiento con sus respectivas Ms
	private void calculoMediasYDesviaciones(List<List<Float>> distancias) {
		List<Float> listAct;
		//Recorremos la lista de listas
		Iterator<List<Float>> itListas = distancias.iterator();
		while(itListas.hasNext()){
			//Obtenemos la siguiente lista de distancias
			listAct = itListas.next();
			//Calculamos su media
			Float media = media(listAct);
			//Calculamos su desviacion
			Float desv = desviacionTipica(listAct, media);
			//Insertamos sus valores
			this.medias.add(media);
			this.desviaciones.add(desv);
		}
	}
	//Entrena un conjunto de factores mediante un conjunto de Passwords
	public void entrenar(List<Password> conjunto_entrenamiento) {
		//Obtenemos la M de cada sujeto del entrenamiento
		calculoMs(conjunto_entrenamiento);
		//Calculamos las listas de distancias entre cada password de entrenamiento y su M
		List<List<Float>> distancias = distanciasEntrenamiento(conjunto_entrenamiento);
		//Calculamos las medias y desviaciones de dichas listas de distancias
		calculoMediasYDesviaciones(distancias);
		//Calculamos los umbrales de cada sujeto
		calcularUmbrales();
	}
	
	//Dado un sujeto, obtenemos su M que contiene las listas de sus medias
	private Password obtenerM(String sujeto){
		Password p = null;
		boolean encontrado = false;
		Iterator<Password> it = this.M.iterator();
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
	//Calcula la distancia entre dos latencias
	private Float distanciaEntreElementos(Float v1, Float v2){
		return (Math.abs(v1 - v2));
	}
	//Calcula la distancia entre dos listas de datos
	private Float distanciaEntreCaracteristicas(List<Float> l1, List<Float> l2){
		//Distancia entre una caracteristica y la media
		Float distancia = new Float(0);
		
		//Iteradores de las listas de datos
		Iterator<Float> it1 = l1.iterator();
		Iterator<Float> it2 = l2.iterator();
		
		//Sumatorio de las distancias entre cada elemento y la media
		while(it1.hasNext() && it2.hasNext())
			distancia += distanciaEntreElementos(it1.next(), it2.next());
		
		return distancia;
	}
	//Devuelve la distancia entre dos password
	private Float distanciaEntrePasswords(Password p1, Password p2){
		//Distancia total
		Float distancia = new Float(0);
		//Recorremos todas las caracteristicas a contemplar
		Iterator<String> itCar = this.caracteristicas.iterator();
		while(itCar.hasNext()){
			//Obtenemos la caracteristica actual
			String carAct = itCar.next();
			//Dwell Time
			if(carAct.equalsIgnoreCase("dt"))		
				distancia += distanciaEntreCaracteristicas(p1.getDwell_List(), p2.getDwell_List());
			//Flight times
			else if(carAct.equalsIgnoreCase("ft1"))
				distancia += distanciaEntreCaracteristicas(p1.getFlight_up_down_List(), p2.getFlight_up_down_List());
			
			else if(carAct.equalsIgnoreCase("ft2"))
				distancia += distanciaEntreCaracteristicas(p1.getFlight_up_up_List(), p2.getFlight_up_up_List());

			else if(carAct.equalsIgnoreCase("ft3"))
				distancia += distanciaEntreCaracteristicas(p1.getFlight_down_down_List(), p2.getFlight_down_down_List());

			else if(carAct.equalsIgnoreCase("ft4"))
				distancia += distanciaEntreCaracteristicas(p1.getFlight_down_up_List(), p2.getFlight_down_up_List());

			//N-graphs
			else if(carAct.equalsIgnoreCase("ng"))
				distancia += distanciaEntreCaracteristicas(p1.getNGraph_List(), p2.getNGraph_List());

		}
		
		return distancia;
	}
	//Calcula los umbrales a considerar 
	private void calcularUmbrales() {
		float umbAct, mediaAct, desvAct;
		Password passAct;
		String sujAct;
		//Recorremos todas las Ms para obtener cada sujeto del sistema
		Iterator<Password> itM = this.M.iterator();
		//Recorremos todas las medias y desviaciones que corresponden a todos los sujetos del sistema
		Iterator<Float> itMedias = this.medias.iterator();
		Iterator<Float> itDesviaciones = this.desviaciones.iterator();
		while(itM.hasNext() && itMedias.hasNext() && itDesviaciones.hasNext()){
			//Obtenemos el M actual y seguidamente su sujeto
			passAct = itM.next();
			sujAct = passAct.getSubject();
			//Obtenemos la media y la desviacion
			mediaAct = itMedias.next();
			desvAct = itDesviaciones.next();
			//Calculamos el umbral
			umbAct = mediaAct + (desvAct * this.numDesviaciones);
			//Lo insertamos en el mapa de umbrales con el sujeto correspondiente
			this.umbrales.put(sujAct, umbAct);
		}		
	}
	//Comprueba si una password es realmente un sujeto o no
	public boolean verificarPassword(Password p, String sujeto) throws Exception{
		boolean valido = true;
		//Distancia entre los password
		Float distancia = new Float(0);
		
		//Obtenemos el M de ese sujeto
		Password m = obtenerM(sujeto);
		
		//Si no existe M, lanzamos una excepcion
		if(m == null)
			throw(new Exception("No existe un entrenamiento anterior sobre este sujeto"));
		
		//Obtenemos la distancia entre la password a evaluar y la M de ese sujeto
		distancia = distanciaEntrePasswords(p, m);
		//Si la distancia es menor que el umbral de dicho sujeto, la password es valida
		if(distancia >= umbrales.get(sujeto))
			valido = false;
		
		return valido;
	}
	//Testea un conjunto de Passwords
	public Resultados testear(List<Password> conjunto_test) {
		int numPasswords = 0;
		float falsosAceptados = 0, falsosRechazados = 0, impostores = 0, clientesVerdaderos = 0;
		String sujetoAct = "";
		boolean valido;
		
		//Recorremos la lista de Ms para obtener todos los sujetos
		Iterator<Password> it = this.M.iterator();
		while(it.hasNext()){
			//Obtenemos el sujeto actual
			sujetoAct = it.next().getSubject();
			//Recorremos todos las password del conjunto de test
			Iterator<Password> it2 = conjunto_test.iterator();
			while(it2.hasNext()){
				//Aumentamos el numero de passwords analizados
				numPasswords++;
				//Obtenemos el siguiente password a analizar
				Password act = it2.next();
				//Aumentamos el numero de impostores o clientes reales
				if(sujetoAct.equalsIgnoreCase(act.getSubject()))
					clientesVerdaderos++;
				else
					impostores++;
				try{
					//Verificamos la password
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
		//Calculamos los resultados y los devolvemos
		return new Resultados(falsosRechazados/clientesVerdaderos, falsosAceptados/impostores);
	}

}
