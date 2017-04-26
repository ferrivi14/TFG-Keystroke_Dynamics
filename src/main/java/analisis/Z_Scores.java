package analisis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import main.Password;

public class Z_Scores implements Clasificador {
	//Lista de caracteristicas a evaluar
	private List<String> caracteristicas = new ArrayList<String>();
	//Lista que almacena la media de cada elemento de todas las caracteristica a evaluar
	private List<Password> media = new ArrayList<Password>();
	//Lista que almacena la desviacion tipica de cada elemento de todas las caracteristica a evaluar
	private List<Password> desviacion = new ArrayList<Password>();
	//Distribucion estandar
	Float distribEstandar;
	//Este mapa almacena los umbrales de las caracteristicas a evaluar
	private Map<String, List<Intervalo>> intervalos = new TreeMap<String, List<Intervalo>>();
	//Porcentaje de aciertos que debe tener una contraseña para ser aceptada en el sistema
	Float porcentajeAciertos;
	
	public Z_Scores(List<String> features, Float z, Float porAciertos){
		this.caracteristicas = features;
		this.distribEstandar = z;
		this.porcentajeAciertos = porAciertos;
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
			resultado.add((float) Math.pow((it.next() - itMedia.next()), 2));							
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
			resultado.add((float) (v1 + Math.pow((v2 - vmedia),2)));							
		}
		return resultado;
	}
	private List<Float> raizDivision(List<Float> l, int num){
		Iterator<Float> it = l.iterator();
		//Lista resultante
		List<Float> resultado = new ArrayList<Float>();
		while(it.hasNext()){
			resultado.add((float) Math.sqrt(it.next() / num));							
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
						passSujAct.setDwell_List(sumaDeListas(passSujAct.getFlight_up_down_List(), act.getFlight_up_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft2")){
						passSujAct.setDwell_List(sumaDeListas(passSujAct.getFlight_up_up_List(), act.getFlight_up_up_List()));				
					}
					else if(carAct.equalsIgnoreCase("ft3")){
						passSujAct.setDwell_List(sumaDeListas(passSujAct.getFlight_down_down_List(), act.getFlight_down_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft4")){
						passSujAct.setDwell_List(sumaDeListas(passSujAct.getFlight_down_up_List(), act.getFlight_down_up_List()));
					}
					//N-graphs
					else if(carAct.equalsIgnoreCase("ng")){
						passSujAct.setDwell_List(sumaDeListas(passSujAct.getNGraph_List(), act.getNGraph_List()));
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
							passSujAct.setDwell_List(divisionLista(passSujAct.getFlight_up_down_List(), numMuestras));
						}
						else if(carAct.equalsIgnoreCase("ft2")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getFlight_up_up_List(), numMuestras));				
						}
						else if(carAct.equalsIgnoreCase("ft3")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getFlight_down_down_List(), numMuestras));
						}
						else if(carAct.equalsIgnoreCase("ft4")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getFlight_down_up_List(), numMuestras));
						}
						//N-graphs
						else if(carAct.equalsIgnoreCase("ng")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getNGraph_List(), numMuestras));
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
						passSujAct.setDwell_List(act.getFlight_up_down_List());
					}
					else if(carAct.equalsIgnoreCase("ft2")){
						passSujAct.setDwell_List(act.getFlight_up_up_List());				
					}
					else if(carAct.equalsIgnoreCase("ft3")){
						passSujAct.setDwell_List(act.getFlight_down_down_List());
					}
					else if(carAct.equalsIgnoreCase("ft4")){
						passSujAct.setDwell_List(act.getFlight_down_up_List());
					}
					//N-graphs
					else if(carAct.equalsIgnoreCase("ng")){
						passSujAct.setDwell_List(act.getNGraph_List());
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
							passSujAct.setDwell_List(divisionLista(passSujAct.getFlight_up_down_List(), numMuestras));
						}
						else if(carAct.equalsIgnoreCase("ft2")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getFlight_up_up_List(), numMuestras));				
						}
						else if(carAct.equalsIgnoreCase("ft3")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getFlight_down_down_List(), numMuestras));
						}
						else if(carAct.equalsIgnoreCase("ft4")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getFlight_down_up_List(), numMuestras));
						}
						//N-graphs
						else if(carAct.equalsIgnoreCase("ng")){
							passSujAct.setDwell_List(divisionLista(passSujAct.getNGraph_List(), numMuestras));
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
						passSujAct.setDwell_List(sumaDeRestas(passSujAct.getFlight_up_down_List(), act.getFlight_up_down_List(), passMedia.getFlight_up_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft2")){
						passSujAct.setDwell_List(sumaDeRestas(passSujAct.getFlight_up_up_List(), act.getFlight_up_up_List(), passMedia.getFlight_up_up_List()));				
					}
					else if(carAct.equalsIgnoreCase("ft3")){
						passSujAct.setDwell_List(sumaDeRestas(passSujAct.getFlight_down_down_List(), act.getFlight_down_down_List(), passMedia.getFlight_down_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft4")){
						passSujAct.setDwell_List(sumaDeRestas(passSujAct.getFlight_down_up_List(), act.getFlight_down_up_List(), passMedia.getFlight_down_up_List()));
					}
					//N-graphs
					else if(carAct.equalsIgnoreCase("ng")){
						passSujAct.setDwell_List(sumaDeRestas(passSujAct.getNGraph_List(), act.getNGraph_List(), passMedia.getNGraph_List()));
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
							passSujAct.setDwell_List(raizDivision(passSujAct.getDwell_List(), numMuestras - 1));
						}
						//Flight times
						else if(carAct.equalsIgnoreCase("ft1")){
							passSujAct.setDwell_List(raizDivision(passSujAct.getFlight_up_down_List(), numMuestras - 1));
						}
						else if(carAct.equalsIgnoreCase("ft2")){
							passSujAct.setDwell_List(raizDivision(passSujAct.getFlight_up_up_List(), numMuestras - 1));				
						}
						else if(carAct.equalsIgnoreCase("ft3")){
							passSujAct.setDwell_List(raizDivision(passSujAct.getFlight_down_down_List(), numMuestras - 1));
						}
						else if(carAct.equalsIgnoreCase("ft4")){
							passSujAct.setDwell_List(raizDivision(passSujAct.getFlight_down_up_List(), numMuestras - 1));
						}
						//N-graphs
						else if(carAct.equalsIgnoreCase("ng")){
							passSujAct.setDwell_List(raizDivision(passSujAct.getNGraph_List(), numMuestras - 1));
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
						passSujAct.setDwell_List(restaMedia(act.getFlight_up_down_List(), passMedia.getFlight_up_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft2")){
						passSujAct.setDwell_List(restaMedia(act.getFlight_up_up_List(), passMedia.getFlight_up_up_List()));				
					}
					else if(carAct.equalsIgnoreCase("ft3")){
						passSujAct.setDwell_List(restaMedia(act.getFlight_down_down_List(), passMedia.getFlight_down_down_List()));
					}
					else if(carAct.equalsIgnoreCase("ft4")){
						passSujAct.setDwell_List(restaMedia(act.getFlight_down_up_List(), passMedia.getFlight_down_up_List()));
					}
					//N-graphs
					else if(carAct.equalsIgnoreCase("ng")){
						passSujAct.setDwell_List(restaMedia(act.getNGraph_List(), passMedia.getNGraph_List()));
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
							passSujAct.setDwell_List(raizDivision(passSujAct.getDwell_List(), numMuestras - 1));
						}
						//Flight times
						else if(carAct.equalsIgnoreCase("ft1")){
							passSujAct.setDwell_List(raizDivision(passSujAct.getFlight_up_down_List(), numMuestras - 1));
						}
						else if(carAct.equalsIgnoreCase("ft2")){
							passSujAct.setDwell_List(raizDivision(passSujAct.getFlight_up_up_List(), numMuestras - 1));				
						}
						else if(carAct.equalsIgnoreCase("ft3")){
							passSujAct.setDwell_List(raizDivision(passSujAct.getFlight_down_down_List(), numMuestras - 1));
						}
						else if(carAct.equalsIgnoreCase("ft4")){
							passSujAct.setDwell_List(raizDivision(passSujAct.getFlight_down_up_List(), numMuestras - 1));
						}
						//N-graphs
						else if(carAct.equalsIgnoreCase("ng")){
							passSujAct.setDwell_List(raizDivision(passSujAct.getNGraph_List(), numMuestras - 1));
						}
					}
					this.desviacion.add(passSujAct);
				}
			}
		}
	}
	private Intervalo crearIntervalo(Float media, Float desv){
		Float limiteInf = media - (this.distribEstandar * desv);
		Float limiteSup = media + (this.distribEstandar * desv);
		
		return new Intervalo(limiteInf, limiteSup);
	}
	private List<Intervalo> crearIntervalosCaracteristica(List<Float> medias, List<Float> desviaciones){
		List<Intervalo> lista_intervalos = new ArrayList<Intervalo>();
		Float media, desv; 
		//
		Iterator<Float> itM = medias.iterator();
		Iterator<Float> itD = medias.iterator();
		while(itM.hasNext() && itD.hasNext()){
			media = itM.next();
			desv = itD.next();
			lista_intervalos.add(crearIntervalo(media, desv));
		}
			
		return lista_intervalos;
	}
	private void calculoIntervalos() {
		// TODO Auto-generated method stub
		
	}
	public void entrenar(List<Password> conjunto_entrenamiento) {
		media(conjunto_entrenamiento);
		desviacionTipica(conjunto_entrenamiento);
		calculoIntervalos();
	}

	private boolean verificarPassword(Password p, String sujeto) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Resultados testear(List<Password> conjunto_test) {
		int numPasswords = 0;
		float falsosAceptados = 0, falsosRechazados = 0;
		String sujetoAct = "";
		boolean valido;
		
		//Recorremos la lista de Ms para obtener todos los sujetos
		Iterator<Password> it = this.media.iterator();
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
		System.out.println(falsosRechazados +" "+ falsosAceptados +" "+ numPasswords);
		return new Resultados(falsosRechazados/numPasswords, falsosAceptados/numPasswords);
	}
}
