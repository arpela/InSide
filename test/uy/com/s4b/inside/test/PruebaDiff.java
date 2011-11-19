package uy.com.s4b.inside.test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import uy.com.s4b.inside.core.ejbs.report.impl.difflib.Delta;
import uy.com.s4b.inside.core.ejbs.report.impl.difflib.DiffUtils;
import uy.com.s4b.inside.core.ejbs.report.impl.difflib.Patch;


/**
 * 
 * Title: PruebaDiff.java <br>
 * Description: <br>
 * Fecha creaci�n: 01/10/2011 <br>
 * Copyright: S4B <br>
 * Company: S4B - http://www.s4b.com.uy <br>
 * @author Pablo
 *
 */
public class PruebaDiff {

	private static List<String> fileToLines(String filename) {
          List<String> lines = new LinkedList<String>();
          String line = "";
          try {
                  BufferedReader in = new BufferedReader(new FileReader(filename));
                  while ((line = in.readLine()) != null) {
                          lines.add(line);
                  }
          } catch (IOException e) {
                  e.printStackTrace();
          }
          return lines;
  }


	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<String> original = fileToLines("/home/pablo/archivo1.txt");
        List<String> revised  = fileToLines("/home/pablo/archivo2.txt");
		
		//List<String> original = Arrays.asList("L1", "L2", "L3", "L4");
		//List<String> revised = Arrays.asList("L1", "L2", "L3", "L4", "L5");	
		
         
         // Se hace el diff entre los dos String. 
		 // Devuelve un objeto Patch, el cual contiene un List de deltas.
         Patch patch = DiffUtils.diff(original, revised);

         for (Delta delta: patch.getDeltas()) {
        	 System.out.println(delta.getType()); 					//enum CHANGE, DELETE, INSERT
        	 
        	 System.out.println(delta.getOriginal().getPosition());	//nro. linea original con diferencia
        	 														//primera linea = 0
        	 
        	 System.out.println(delta.getOriginal().getLines());	//List con texto original de la linea
        	 														//si es INSERT no hay texto 
        	 														//si es DELETE tiene el texto eliminado
        	 														//si es CHANGE tiene el texto original
        	 
        	 System.out.println(delta.getRevised().getPosition());	//nro. linea revisada con diferencia
        	 
        	 System.out.println(delta.getRevised().getLines());		//List del texto con diferencias
        	 														//si es INSERT tiene el nuevo texto
        	 														//si es DELETE no hay texto
        	 														//si es CHANGE tiene el texto modificado
        	 System.out.println(delta);
         }

	}

}
