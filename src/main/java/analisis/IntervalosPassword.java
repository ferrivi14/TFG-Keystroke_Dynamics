package analisis;

import java.util.List;

public class IntervalosPassword {
	private String sujeto;
	private List<Intervalo> dwell_list;
	private List<Intervalo> flight_time_up_down;
	private List<Intervalo> flight_time_up_up;
	private List<Intervalo> flight_time_down_down;
	private List<Intervalo> flight_time_down_up;
	private List<Intervalo> n_graph_list;
	
	public IntervalosPassword(String subject){
		this.sujeto = subject;
	}
	
	public String getSujeto() {
		return sujeto;
	}

	public List<Intervalo> getDwell_list() {
		return dwell_list;
	}

	public List<Intervalo> getFlight_time_up_down() {
		return flight_time_up_down;
	}

	public List<Intervalo> getFlight_time_up_up() {
		return flight_time_up_up;
	}

	public List<Intervalo> getFlight_time_down_down() {
		return flight_time_down_down;
	}

	public List<Intervalo> getFlight_time_down_up() {
		return flight_time_down_up;
	}

	public List<Intervalo> getN_graph_list() {
		return n_graph_list;
	}

	public void setDwell_list(List<Intervalo> dwell_list) {
		this.dwell_list = dwell_list;
	}

	public void setFlight_time_up_down(List<Intervalo> flight_time_up_down) {
		this.flight_time_up_down = flight_time_up_down;
	}

	public void setFlight_time_up_up(List<Intervalo> flight_time_up_up) {
		this.flight_time_up_up = flight_time_up_up;
	}

	public void setFlight_time_down_down(List<Intervalo> flight_time_down_down) {
		this.flight_time_down_down = flight_time_down_down;
	}

	public void setFlight_time_down_up(List<Intervalo> flight_time_down_up) {
		this.flight_time_down_up = flight_time_down_up;
	}

	public void setN_graph_list(List<Intervalo> n_graph_list) {
		this.n_graph_list = n_graph_list;
	}

	
	
}
