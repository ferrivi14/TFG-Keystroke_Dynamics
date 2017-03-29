package analisis;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import main.Password;

public interface Clasificador {
	public void entrenar(List<Password> conjunto_entrenamiento);
	public void mostrar();
	public void testear(List<Password> conjunto_test);
}
