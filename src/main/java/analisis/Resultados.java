package analisis;

public class Resultados {
	//False Rejection Rate (Tasa de falso rechazo)
	private Float FRR;
	//False Acceptance Rate (Tasa de aceptacion falsa)
	private Float FAR;
	private Float ERR;
	
	public Resultados(Float fr, Float fa){
		this.FRR = fr;
		this.FAR = fa;
	}
	public Resultados(Float err){
		this.ERR = err;
	}
	
	public void mostrar(){
		if(this.FRR != null)
			System.out.println("FRR = " + this.FRR);
		if(this.FAR != null)
			System.out.println("FAR = " + this.FAR);
		if(this.ERR != null)
			System.out.println("ERR = " + this.ERR);
	}
}
