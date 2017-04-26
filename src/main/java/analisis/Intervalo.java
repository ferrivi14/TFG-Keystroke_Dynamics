package analisis;

public class Intervalo {
	private Float limiteInf;
	private Float limiteSup;
	
	public Intervalo(Float lI, Float lS){
		this.limiteInf = lI;
		this.limiteSup = lS;
	}
	public void setLimiteInf(Float lim){
		this.limiteInf = lim;
	}
	public void setLimiteSup(Float lim){
		this.limiteSup = lim;
	}
	public boolean estaDentro(Float num){
		boolean ok = true;
		if(num < this.limiteInf || num > this.limiteSup)
			ok = false;
		
		return ok;
	}
}
