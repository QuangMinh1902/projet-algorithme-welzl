import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Utils {

	// Méthode pour obtenir la liste des fichiers dans un répertoire
	public static File[] listerRepertoire(String chemin) {
		File repertoire = new File(chemin);
		File[] listeFichiers = null;

		if (repertoire.isDirectory()) {
			listeFichiers = repertoire.listFiles();
		}

		return listeFichiers;
	}

	// Méthode pour lire les points à partir d'un fichier
	public static ArrayList<Point> lireFichier(String nomFichier) {
		String ligne;
		String[] coordonnees;
		ArrayList<Point> points = new ArrayList<>();

		try {
			// Ouverture du fichier en lecture
			BufferedReader lecteur = new BufferedReader(
					new InputStreamReader(new FileInputStream(nomFichier)));

			try {
				// Lecture des lignes du fichier
				while ((ligne = lecteur.readLine()) != null) {
					// Séparation des coordonnées par espace et ajout du point à la liste
					coordonnees = ligne.split("\\s+");
					points.add(new Point(Integer.parseInt(coordonnees[0]),
							Integer.parseInt(coordonnees[1])));
				}
			} catch (IOException e) {
				System.err.println("Exception : Entrée/Sortie interrompue.");
			} finally {
				try {
					// Fermeture du lecteur
					lecteur.close();
				} catch (IOException e) {
					System.err.println("Exception I/O : Impossible de fermer " + nomFichier);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Fichier d'entrée introuvable.");
		}

		return points;
	}
}
