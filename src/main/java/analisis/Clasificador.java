package analisis;

import java.util.List;

import main.Password;

public interface Clasificador {
	public void entrenar(List<Password> conjunto_entrenamiento);
	public Resultados testear(List<Password> conjunto_test);
}
