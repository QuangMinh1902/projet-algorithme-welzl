package algorithms;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import supportGUI.Circle;

public class main {

	public static void main(String[] args) {
		long debut = System.currentTimeMillis();
		DefaultTeam dt = new DefaultTeam();
		String path = "samples/";
		File[] listFic = Utils.listeRepertoire(path);

		try (PrintWriter writer = new PrintWriter(new FileWriter("output.csv"))) {
			// Write header to CSV file
			writer.println("WelzlTime - NaifTime");

			for (File x : listFic) {
				ArrayList<Point> points = Utils.readFile(x.getPath());

				long debutW = System.nanoTime();
				Circle cw = dt.welzl(points);
				long tempsW = System.nanoTime() - debutW;

				long debutN = System.nanoTime();
				Circle cn = dt.naif(points);
				long tempsN = System.nanoTime() - debutN;

				writer.println(tempsW + " - " + tempsN);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(System.currentTimeMillis() - debut);
	}
}
