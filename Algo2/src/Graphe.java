
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

public class Graphe {
    /** Matrice d'adjacence du graphe **/
    private int[][] adjacence;
    /** Correspondance [Nom du sommet] -> indice dans adjacence **/
    private TreeMap<String, Integer> nomsSommets;
    
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
        nomsSommets = new TreeMap<>();
        if(path != null) {
            int size;
            String ligne;
            String[] dettes;
            int sommetCourant = 0;
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
                if(! nomsSommets.containsKey(dettes[0]))
                    nomsSommets.put(dettes[0], sommetCourant++);
                if (! nomsSommets.containsKey(dettes[1]))
                    nomsSommets.put(dettes[1], sommetCourant++);
                
                adjacence[nomsSommets.get(dettes[0])][nomsSommets.get(dettes[1])]
                        = Integer.parseInt(dettes[2]);
            }
        }
    }
    
    
    /**
     * Permet d'afficher le graphe sous forme de chaine de caractères
     * (La matrice d'adjacence est affichée)
     * @return Graphe sous forme de chaine de caractères
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        
        for (int[] ligne : adjacence) {
            for (int c : ligne) {
                res.append(String.format("%-5s", c));
            }
            res.append("\n");
        }
        
        return res.toString();
    }
    
    
}
