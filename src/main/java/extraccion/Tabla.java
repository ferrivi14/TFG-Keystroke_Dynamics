package extraccion;

import java.util.ArrayList;
import java.util.List;

public class Tabla {
	private String titulo;
	private List<String> contenido;
	
	public Tabla(String nombre){
		this.titulo = nombre;
		this.contenido = new ArrayList<String>();
	}
	public Tabla(String nombre, List<String> texto){
		this.titulo = nombre;
		this.contenido = texto;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public List<String> getContenido() {
		return contenido;
	}
	public void setContenido(List<String> contenido) {
		this.contenido = contenido;
	}
	public void añadirLinea(String cadena){
		this.contenido.add(cadena); 
	}
}
