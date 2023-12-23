import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import algorithms.DefaultTeam;

import supportGUI.Circle;

public class main {
	public static void main(String[] args) {
		// Instantiation de l'équipe par défaut
		DefaultTeam defaultTeam = new DefaultTeam();

		// Chemin du répertoire contenant les fichiers
		String cheminRepertoire = "samples/";

		// Obtention de la liste des fichiers dans le répertoire
		File[] listeFichiers = Utils.listerRepertoire(cheminRepertoire);

		try (PrintWriter writer = new PrintWriter(new FileWriter("output.csv"))) {
			// Écriture de l'en-tête dans le fichier CSV
			writer.println("WelzlTime - NaifTime");

			// Boucle à travers chaque fichier du répertoire
			for (File fichier : listeFichiers) {
				// Lecture des points à partir du fichier
				ArrayList<Point> points = Utils.lireFichier(fichier.getPath());

				// Calcul du temps d'exécution de l'algorithme de Welzl
				long debutWelzl = System.nanoTime();
				Circle cercleWelzl = defaultTeam.welzl(points);
				long tempsWelzl = (System.nanoTime() - debutWelzl) / 1_000; // Conversion en microsecondes

				// Calcul du temps d'exécution de l'algorithme naif
				long debutNaif = System.nanoTime();
				Circle cercleNaif = defaultTeam.naif(points);
				long tempsNaif = (System.nanoTime() - debutNaif) / 1_000; // Conversion en microsecondes

				// Écriture des temps d'exécution dans le fichier CSV
				writer.println(tempsWelzl + " - " + tempsNaif);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
