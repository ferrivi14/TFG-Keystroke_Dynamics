package pruebas_iniciales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import main.Password;

public class leerTXT {
	public static void main(String [] arg) {
	      File archivo = null;
	      FileReader fr = null;
	      BufferedReader br = null;

	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File ("files/DSL-StrongPasswordData.txt");
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea;
	         String splitBy = "           ";
	         List<Password> lista = new ArrayList<Password>();
			 if((linea = br.readLine()) != null){
			    while ((linea = br.readLine()) != null) {
			
			        // use comma as separator
			        String[] columnas = linea.split(splitBy);
			       
			        Password p = new Password(columnas[0], Integer.parseInt(columnas[1]), Integer.parseInt(columnas[2]));
			        lista.add(p);
			    }
			 }
			 
			 Password q = lista.get(0);
			 System.out.println("Sujeto " + q.getSubject() + " , session=" + q.getSession() + " rep=" + q.getRep());
     
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
	   }
}
