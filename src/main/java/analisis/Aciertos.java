package analisis;

public class Aciertos {
	private int aciertos;
	private int latencias;
	
	public Aciertos(int aci, int lat){
		this.aciertos = aci;
		this.latencias = lat;
	}
	public int getAciertos() {
		return aciertos;
	}
	public void setAciertos(int aciertos) {
		this.aciertos = aciertos;
	}
	public int getLatencias() {
		return latencias;
	}
	public void setLatencias(int latencias) {
		this.latencias = latencias;
	}
	public void suma(Aciertos a){
		this.aciertos += a.getAciertos();
		this.latencias += a.getLatencias();
	}
	public boolean aciertosSuficientes(Float porcentajeAciertos){
		boolean ok = true;
		porcentajeAciertos = porcentajeAciertos/100;
		//Si el numero de aciertos es menor que el porcentaje por el numero total de latencias, la password es falsa
		if(this.aciertos < (porcentajeAciertos * this.latencias))
			ok = false;
		
		return ok;
	}
}
