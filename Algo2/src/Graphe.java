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
     * Matrice d'adjacence du graphe
     * Chaque cellule contient l'information (dette, visité)
     */
    private Integer[][] adjacence;
    
    /** Matrice d'accessibilité du graphe. */
    private boolean[][] accessibilite;
    
    /** Correspondance [indiceSommet] -> indice dans la matrice d'adjacence. */
    private ArrayList<Sommet<String>> sommets;
    
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
        sommets = new ArrayList<>();
        if (path != null) {
            int size;
            String ligne;
            String[] dettes;
            BufferedReader in = new BufferedReader(new FileReader(path));
            size = Integer.parseInt(in.readLine());
            this.adjacence = new Integer[size][size];
            // On initialise la matrice d'adjacence à null partout.
            for (int i = 0; i < adjacence.length; i++) {
                for (int j = 0; j < adjacence.length; j++) {
                    adjacence[i][j] = null;
                }
            }
            // lecture du fichier
            while ((ligne = in.readLine()) != null) {
                dettes = ligne.split(" ");
                Sommet  s = new Sommet<>(dettes[0], false);
                Sommet ss = new Sommet<>(dettes[1], false);
                if (!sommets.contains( s)) sommets.add( s);
                if (!sommets.contains(ss)) sommets.add(ss);
                
                adjacence[sommets.indexOf(s)][sommets.indexOf(ss)]
                         = Integer.parseInt(dettes[2]);
            }
            accessibilite = new boolean[adjacence.length][adjacence.length];
            calculerAccessibilite();
        }
    }
    
    /**
     * (Résout l'exercice 2.2 du projet)
     * Une communauté est un ensemble de personnes connectées par leurs 
     * dettes actuelles ou passées. Cette fonction reçoit en entrée le graphe 
     * décrivant les dettes actuelles et passées et 
     * retourne l’ensemble des communautés.
     * 
     * @param g Le graphe dont on doit identifier les communautés
     * @return Une liste reprenant l'ensemble des communautés. 
     *         Une communauté consiste en une liste de String où chaque 
     *         String représente le nom du noeud.
     */
    public static ArrayList<ArrayList<String>> identifierCommunautes(Graphe g) {
        ArrayList<ArrayList<String>> communautes = new ArrayList<>();
        ArrayList<String> communaute = new ArrayList<>();
        for(int i = 0; i < g.adjacence.length; i++) {
            System.out.println(g.sommets.get(i));
            parcours(g, communaute, i);
            if(communaute.size() > 0)
                communautes.add((ArrayList<String>) communaute.clone());
            communaute.clear();
        }
        
        g.resetVisite();
        return communautes;
    }
    
    /**
     * Démarre un parcours du graphe. 
     * @param g Le graphe sur lequel on traite la matrice d'adjacence.
     * @param comm La communauté actuelle que l'on traite dans le parcours.
     * @param noeud Noeud courant (représenté par un entier sur la matrice
     *              d'adjacence).
     */
    private static void parcours(Graphe g, ArrayList<String> comm, int noeud)
    {
        if(!g.estVisite(noeud)) {
            // Marquer le noeud comme visité
            g.marquerVisite(noeud);
            // Ajout du noeud courant dans la communauté
            comm.add(g.sommets.get(noeud).getValeur());
            for(int i = 0; i < g.adjacence.length; i++)
                // Pour chaque destination, s'il n'a pas déjà été visité
                if(g.destinationExiste(noeud, i))
                    parcours(g, comm, i);
        }
    }
    
    /**
     * Indique si le noeud depart possède un lien vers le noeud arrivee.
     * @param depart Le noeud de départ
     * @param arrivee Le noeud d'arrivée
     * @return 
     */
    private boolean destinationExiste(int depart, int arrivee) {
        return adjacence[depart][arrivee] != null
            || adjacence[arrivee][depart] != null;
    }
    
    /**
     * Marque un noeud comme visité.
     * @param noeud Le noeud à marquer comme visité
     */
    private void marquerVisite(int noeud) {
        this.sommets.get(noeud).setVisite(true);
    }
    
    private void resetVisite() {
        sommets.stream().forEach( (sommet) -> sommet.setVisite(false) );
    }
    
    /**
     * Indique si un noeud a été visité ou pas.
     * @param noeud
     * @return 
     */
    private boolean estVisite(int noeud) {
        return this.sommets.get(noeud).isVisite();
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
        res.append(String.format("%-10s", ""));
        for(int i = 0; i < adjacence.length; i++) {
            res.append(String.format("%-10s", sommets.get(i).getValeur()));
        }
        res.append("\n");
        for (int i = 0; i < adjacence.length; i++) {
            res.append(String.format("%-10s", sommets.get(i).getValeur()));
            for (int j = 0; j < adjacence.length; j++) {
                res.append(String.format("%-10s", adjacence[i][j]));
            }
            res.append("\n");
        }
        res.append("Matrice d'accessibilité : \n");
        res.append(String.format("%-10s", ""));
        for(int i = 0; i < adjacence.length; i++) {
            res.append(String.format("%-10s", sommets.get(i).getValeur()));
        }
        res.append("\n");
        for (int i = 0; i < accessibilite.length; i++) {
            res.append(String.format("%-10s", sommets.get(i).getValeur()));
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
                accessibilite[i][j] = (adjacence[i][j] != null);
            }
        }
        for (int k = 0; k < accessibilite.length; ++k) {
            for (int i = 0; i < accessibilite.length; ++i) {
                if (accessibilite[i][k]) {
                    for (int j = 0; j < accessibilite.length; ++j) {
                        accessibilite[i][j] = accessibilite[i][j] 
                                            || accessibilite[k][j];
                    }
                }
            }
        }
    }


}
