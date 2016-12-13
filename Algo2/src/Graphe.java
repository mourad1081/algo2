import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Graphe représentant les dettes courantes et passées entre différents amis
 * @author Mourad, Mounir
 */
public class Graphe {
    /** Matrice d'adjacence du graphe **/
    private int[][] adjacence;
    /** Correspondance [Nom du sommet] -> indice dans adjacence **/
    private ArrayList<String> nomsSommets;
    
    /**
     * Construit un graphe vide
     * @throws IOException Si une erreur d'entrée/sortie est survenue
     */
    public Graphe() throws IOException {
        this(null);
    }
    
    /**
     * Construit un graphe sur base d'un fichier donné en paramètre
     * @param path Le chemin vers le fichier à parser
     * @throws FileNotFoundException Si le fichier n'est pas trouvé
     * @throws IOException Si une erreur d'entrée/sortie est survenue
     */
    public Graphe(String path) throws FileNotFoundException, IOException {
        nomsSommets = new ArrayList<>();
        if(path != null) {
            int size;
            String ligne;
            String[] dettes;
            BufferedReader in = new BufferedReader(new FileReader(path));
            size = Integer.parseInt(in.readLine());
            this.adjacence = new int[size][size];
            // On initialise la matrice d'adjacence à -1 partout.
            for(int i = 0; i < adjacence.length; i++)
                for(int j = 0; j < adjacence.length; j++)
                    adjacence[i][j] = -1;
            
            // lecture du fichier
            while((ligne = in.readLine()) != null) {
                dettes = ligne.split(" ");
                if(! nomsSommets.contains(dettes[0]))
                    nomsSommets.add(dettes[0]);
                if (! nomsSommets.contains(dettes[1]))
                    nomsSommets.add(dettes[1]);
                
                adjacence[nomsSommets.indexOf(dettes[0])]
                         [nomsSommets.indexOf(dettes[1])] 
                        = Integer.parseInt(dettes[2]);
            }
        }
    }
    
    /**
     * (Résout l'exercice 2.2 du projet)
     * Une communauté est un ensemble de personnes connectées par leurs 
     * dettes actuelles ou passées. Cette fonction reçoit en entrée le graphe 
     * décrivant les dettes actuelles et passées et 
     * retourne l’ensemble des communautés.
     * 
     * @param e Le graphe dont on doit identifier les communautés
     * @return Une liste reprenant l'ensemble des communautés. 
     *         Une communauté consiste en une liste de String où chaque 
     *         String représente le nom du noeud.
     */
    public static ArrayList<ArrayList<String>> identifierCommunautes(Graphe e) {
        return null;
    }
    
    
    /**
     * Permet d'afficher le graphe sous forme de chaine de caractères
     * (La matrice d'adjacence est affichée)
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
        
        return res.toString();
    }
    
    
}
