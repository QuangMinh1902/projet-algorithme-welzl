package algorithms;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Utils {
	
	
	public static File[] listeRepertoire ( String path ) {
		File repertoire = new File(path);
        File[] list =null;
        
        if ( repertoire.isDirectory ( ) ) {
                 list = repertoire.listFiles();
               
        } 
        return list ;
} 

	
	
	public static ArrayList<Point> readFile(String filename) {
	    String line;
	    String[] coordinates;
	    ArrayList<Point> points=new ArrayList<Point>();
	    try {
	      BufferedReader input = new BufferedReader(
	          new InputStreamReader(new FileInputStream(filename))
	          );
	      try {
	        while ((line=input.readLine())!=null) {
	          coordinates=line.split("\\s+");
	          points.add(new Point(Integer.parseInt(coordinates[0]),
	                Integer.parseInt(coordinates[1])));
	        }
	       
	      } catch (IOException e) {
	        System.err.println("Exception: interrupted I/O.");
	      } finally {
	        try {
	          input.close();
	        } catch (IOException e) {
	          System.err.println("I/O exception: unable to close "+filename);
	        }
	      }
	    } catch (FileNotFoundException e) {
	      System.err.println("Input file not found.");
	    }
	    return points ;
	  }

	
	


}
