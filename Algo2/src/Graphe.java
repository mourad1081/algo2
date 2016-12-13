
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Graphe représentant les dettes courantes et passées entre différents amis
 *
 * @author Mourad, Mounir
 */
public class Graphe {

    /**
     * Matrice d'adjacence du graphe *
     */
    private int[][] adjacence;
    /**
     * Matrice d'accessibilité du graphe *
     */
    private boolean[][] accessibilite;
    /**
     * Correspondance [Nom du sommet] -> indice dans adjacence *
     */
    private ArrayList<String> nomsSommets;

    /**
     * Construit un graphe vide
     *
     * @throws IOException Si une erreur d'entrée/sortie est survenue
     */
    public Graphe() throws IOException {
        this(null);
    }

    /**
     * Construit un graphe sur base d'un fichier donné en paramètre
     *
     * @param path Le chemin vers le fichier à parser
     * @throws FileNotFoundException Si le fichier n'est pas trouvé
     * @throws IOException Si une erreur d'entrée/sortie est survenue
     */
    public Graphe(String path) throws FileNotFoundException, IOException {
        nomsSommets = new ArrayList<>();
        if (path != null) {
            int size;
            String ligne;
            String[] dettes;
            BufferedReader in = new BufferedReader(new FileReader(path));
            size = Integer.parseInt(in.readLine());
            this.adjacence = new int[size][size];
            // On initialise la matrice d'adjacence à -1 partout.
            for (int i = 0; i < adjacence.length; i++) {
                for (int j = 0; j < adjacence.length; j++) {
                    adjacence[i][j] = -1;
                }
            }

            // lecture du fichier
            while ((ligne = in.readLine()) != null) {
                dettes = ligne.split(" ");
                if (!nomsSommets.contains(dettes[0])) {
                    nomsSommets.add(dettes[0]);
                }
                if (!nomsSommets.contains(dettes[1])) {
                    nomsSommets.add(dettes[1]);
                }

                adjacence[nomsSommets.indexOf(dettes[0])][nomsSommets.indexOf(dettes[1])]
                        = Integer.parseInt(dettes[2]);
            }
            accessibilite = new boolean[adjacence.length][adjacence.length];
            calculerAccessibilite();
        }
    }

    /**
     * Permet d'afficher le graphe sous forme de chaine de caractères (La
     * matrice d'adjacence est affichée)
     *
     * @return Graphe sous forme de chaine de caractères
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Matrice d'adjacence : \n");
        res.append(String.format("%-5s", ""));
        for(int i = 0; i < adjacence.length; i++) {
            res.append(String.format("%-5s", nomsSommets.get(i)));
        }
        res.append("\n");
        for (int i = 0; i < adjacence.length; i++) {
            res.append(String.format("%-5s", nomsSommets.get(i)));
            for (int j = 0; j < adjacence.length; j++) {
                res.append(String.format("%-5s", adjacence[i][j]));
            }
            res.append("\n");
        }
        res.append("Matrice d'accessibilité : \n");
        res.append(String.format("%-10s", ""));
        for(int i = 0; i < adjacence.length; i++) {
            res.append(String.format("%-10s", nomsSommets.get(i)));
        }
        res.append("\n");
        for (int i = 0; i < accessibilite.length; i++) {
            res.append(String.format("%-10s", nomsSommets.get(i)));
            for (int j = 0; j < accessibilite.length; j++) {
                res.append(String.format("%-10s", accessibilite[i][j]));
            }
            res.append("\n");
        }

        return res.toString();
    }

    private void calculerAccessibilite() {
        for (int i = 0; i < adjacence.length; i++) {
            for (int j = 0; j < adjacence[i].length; j++) {
                accessibilite[i][j] = adjacence[i][j] != -1;
            }
        }
        for (int k = 0; k < accessibilite.length; ++k) {
            for (int i = 0; i < accessibilite.length; ++i) {
                if (accessibilite[i][k]) {
                    for (int j = 0; j < accessibilite.length; ++j) {
                        accessibilite[i][j] = accessibilite[i][j] || accessibilite[k][j];
                    }
                }
            }
        }
    }
}
