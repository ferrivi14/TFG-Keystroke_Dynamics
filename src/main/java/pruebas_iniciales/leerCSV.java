package pruebas_iniciales;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.Password;

public class leerCSV {
	public static void main(String[] args) {
	     
		String csvFile = "files/DSL-StrongPasswordData.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            List<Password> lista = new ArrayList<Password>();
            if((line = br.readLine()) != null){
	            while ((line = br.readLine()) != null) {
	
	                // use comma as separator
	                String[] columnas = line.split(cvsSplitBy);
	                Password p = new Password(columnas[0], Integer.parseInt(columnas[1]), Integer.parseInt(columnas[2]));
	                System.out.println(Float.parseFloat(columnas[3]));
	                lista.add(p);
	            }
        	}
            Password q = lista.get(0);
            System.out.println("Sujeto " + q.getSubject() + " , session=" + q.getSession() + " rep=" + q.getRep());
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
