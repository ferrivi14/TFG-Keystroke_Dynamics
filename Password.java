package main;

import java.util.Iterator;
import java.util.List;

public class Password {
	private String subject;
	private int session;
	private int rep;
	private List<Float> dwell_list;
	private List<Float> flight_time_up_down;
	private List<Float> flight_time_up_up;
	private List<Float> flight_time_down_down;
	private List<Float> flight_time_down_up;
	private List<Float> n_graph_list;
	
	public Password(){}
	
	public Password(String sujeto, int sesion, int repeticion){
		this.subject = sujeto;
		this.session = sesion;
		this.rep = repeticion;
	}
	
	public String getSubject(){
		return this.subject;
	}
	
	public int getSession(){
		return this.session;
	}
	
	public int getRep(){
		return this.rep;
	}
	
	public List<Float> getDwell_List(){
		return this.dwell_list;
	}
	
	public void setDwell_List(List<Float>lista){
		this.dwell_list = lista;
	}
	
	public List<Float> getFlight_up_down_List(){
		return this.flight_time_up_down;
	}
	
	public void setFlight_up_down_List(List<Float>lista){
		this.flight_time_up_down = lista;
	}
	
	public List<Float> getFlight_up_up_List(){
		return this.flight_time_up_up;
	}
	
	public void setFlight_up_up_List(List<Float>lista){
		this.flight_time_up_up = lista;
	}
	
	public List<Float> getFlight_down_down_List(){
		return this.flight_time_down_down;
	}
	
	public void setFlight_down_down_List(List<Float>lista){
		this.flight_time_down_down = lista;
	}
	
	public List<Float> getFlight_down_up_List(){
		return this.flight_time_down_up;
	}
	
	public void setFlight_down_up_List(List<Float>lista){
		this.flight_time_down_up = lista;
	}
	public List<Float> getNGraph_List(){
		return this.n_graph_list;
	}
	
	public void setNGraph_List(List<Float>lista){
		this.n_graph_list = lista;
	}
	private void mostrarLista(List<Float> lista){
		//Creamos un iterador que sera el que recorra la lista
		Iterator<Float> it = lista.iterator();
		//Mientras que haya otro elemento en la lista
    	while(it.hasNext()){
    		Float number = it.next();
    		//Mostramos por pantalla el elemento
    		if(it.hasNext())
    			System.out.print(number + " ");
    		else
    			System.out.println(number);
    	}
	}
	public void mostrarDatos(){
		
	    System.out.println("Sujeto: " + this.subject + " Sesion: " + this.session + " Repeticion: " + this.rep + " ");
	    System.out.print("	Dwell Times: ");
	    mostrarLista(this.dwell_list);
	    System.out.print("	Flight Times Up-Down: ");
	    mostrarLista(this.flight_time_up_down);
	    System.out.print("	Dwell Times Up-Up: ");
	    mostrarLista(this.flight_time_up_up);
	    System.out.print("	Dwell Times Down-Down: ");
	    mostrarLista(this.flight_time_down_down);
	    System.out.print("	Dwell Times Down-Up: ");
	    mostrarLista(this.flight_time_down_up);
	    System.out.print("	N-Graphs: ");
	    mostrarLista(this.n_graph_list);
	    System.out.print("");
	}
}
