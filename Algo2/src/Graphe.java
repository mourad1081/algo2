import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Graphe représentant les dettes courantes et passées entre différents amis
 *
 * @author Mourad, Mounir
 */
public class Graphe {

    /** Matrice d'adjacence du graphe. */
    private Integer[][] adjacence;
    
    /** Matrice d'accessibilité du graphe. */
    private boolean[][] accessibilite;
    
    /** Correspondance [indiceSommet] -> indice dans la matrice d'adjacence. */
    private ArrayList<Sommet<String>> sommets;
    
    /** 
     * Liste utilisée pour sauvegarder les noeud d'un cycle dans le recherche 
     * recursive 
     */
    private ArrayList<Integer> cheminCycle;
    
    /**
     * Map qui sauvegarde et qui trie les cycles trouvés en fonction de leur 
     * ordre
     */
    private TreeMap<Integer, List<List<Integer>>> cycleOrder;
    
    /**
     * Plus grand sous graphe complet du graphe
     */
    private ArrayList<Integer> maxGroupAmis;
    
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
        cheminCycle = new ArrayList<>();
        cycleOrder = new TreeMap<>();
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
                Sommet<String>  s = new Sommet<>(dettes[0], false);
                Sommet<String> ss = new Sommet<>(dettes[1], false);
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
     * Méthode permettant d'afficher la matrice d'accessibilité du graphe sur
     * la sortie standard.
     */
    public void afficherAccessibilite() {
        StringBuilder res = new StringBuilder();
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
        System.out.println(res.toString());
    }
    
    /**
     * Méthode permettant d'afficher la matrice d'adjacence du graphe sur
     * la sortie standard.
     */
    public void afficherAdjacence() {
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
        
        System.out.println(res.toString());
    }
    
    /**
     * @param g graphe ou se trouve la liste des noeuds composants le plus 
     * grande groupe d'amis.
     * 
     * @return le string contenant le ou l'un des plus grands groupes d'amis du
     * graphe.
     * @author Mounir
     */
    private String afficherMaxGrpAmis(){
        String groupeResultat = "";
        groupeResultat += "Groupe : [";
        for (int i = 0; i < maxGroupAmis.size(); i++) {
            groupeResultat += sommets.get(maxGroupAmis.get(i));
            if (i != maxGroupAmis.size() - 1) {
                groupeResultat += ", ";
            }
        }
        groupeResultat += "]";
        return groupeResultat;
    }
    
    /**
     * Génère la matrice d'accessibilité du graphe (Algorithme de Roy-Warshall).
     */
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
    
    /**
     * @return la liste des sommets qui compose le graphe
     */
    private ArrayList<Integer> indiceSommetsGraphe() {
        ArrayList<Integer> sommetsGraphe = new ArrayList<>();
        for (int i = 0; i < sommets.size(); i++) {
            sommetsGraphe.add(i);
        }
        return sommetsGraphe;
    }

    /**
     * Retourne la liste des sommets non voisin du sommet reçue en paramètre
     * 
     * @param sommet le sommet à traiter 
     * @return la liste des sommets non voisin
     * @author Mounir
     */
    private ArrayList<Integer> listSommetsNonVoisin(int sommet) {
        ArrayList<Integer> nonVoisinList = new ArrayList<>();
        for (int i = 0; i < adjacence[sommet].length; i++) {
            if (!destinationExiste(sommet, i)) {
                nonVoisinList.add(i);
            }
        }
        return nonVoisinList;
    }
    
    /**
     * Indique s'il existe une arrete entre noeud départ et arrivée
     * 
     * @param depart Le noeud de départ
     * @param arrivee Le noeud d'arrivée
     * @return vrai si il existe une arrete entre depart et arrivé, faux sinon
     */
    private boolean destinationExiste(int depart, int arrivee) {
        return adjacence[depart][arrivee] != null
            || adjacence[arrivee][depart] != null;
    }
    
    /**
     * Marque un noeud comme visité.
     * @param noeud Le noeud à marquer comme visité
     */
    private void setSommetVisite(int noeud, boolean etatVisite) {
        this.sommets.get(noeud).setVisite(etatVisite);
    }
    
    /**
     * Reinitialise l'état de la visite du noeud pour un futur parcours.
     */
    private void resetVisite() {
        this.sommets.stream().forEach((s) -> s.setVisite(false));
    }
    
    /**
     * Indique si un noeud a été visité ou pas.
     * 
     * @param noeud noeud a vérifier
     * @return vrai si visité, faux sinon
     */
    private boolean estVisite(int noeud) {
        return this.sommets.get(noeud).isVisite();
    }
    
    /**
     * Permet d'afficher le graphe dans le même format que le fichier d'entrée.
     * @return Le graphe sous la même forme que le fichier d'entrée.
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < adjacence.length; i++)
            for(int j = 0; j < adjacence.length; j++)
                if(adjacence[i][j] != null)
                    res.append(sommets.get(i)).append(" ")
                       .append(sommets.get(j)).append(" ")
                       .append(adjacence[i][j]).append("\n");
        return res.toString();
    }
    
/*------------------------- Static Function Project -------------------------*/
    
    /**
     * (Résout l'exercice 2.1 du projet)
     * Tous les cycles dirigés peuvent être simplifiés. Sur base de cette idée,
     * La fonction recherche et effectue la réduction de dette sur le graphe,
     * cette fonction est également responsable de démarrer la recherche 
     * récurssive de cycle
     * 
     * @param g le graphe sur lequel éffectuer la reduction de dette
     * @author Mounir
     */
    public static void reduireDetteGraphe(Graphe g) {
        for (int i = 0; i < g.sommets.size(); i++) {
            if (g.accessibilite[i][i]) {
                g.cheminCycle.add(i);
                for (int j = 0; j < g.adjacence[i].length; j++) {
                    if (i != j && g.adjacence[i][j] != null
                            && g.adjacence[i][j] != 0) {
                        identifierNoeudsCycles(j, g);
                    }
                }
                g.cheminCycle.remove(new Integer(i));
            }
        }
        for (Map.Entry<Integer, List<List<Integer>>> entry : g.cycleOrder.entrySet()) {
            for (int j = 0; j < entry.getValue().size(); j++) {
                reduireDetteCycle(entry.getValue().get(j), g);
            }
        }
        g.cycleOrder.clear();
    }

    /**
     * Démarre la recherche des cycles a partir d'un noeud de départ
     * 
     * @param noeudDepart noeud initial de lancement du cycle qui va aller de
     * (noeudDepart --> ... --> noeudDepart)
     * @param g le graphe sur lequel on effectue la recherche 
     * @author Mounir
     */
    private static void identifierNoeudsCycles(int noeudDepart, Graphe g) {
        g.cheminCycle.add(noeudDepart);
        g.setSommetVisite(noeudDepart, true);
        if (!g.cheminCycle.isEmpty() && noeudDepart == g.cheminCycle.get(0)) {
            if (!g.cycleOrder.containsKey(g.cheminCycle.size() - 1)) {
                g.cycleOrder.put(g.cheminCycle.size() - 1, new ArrayList<>());
            }
            g.cycleOrder.get(g.cheminCycle.size() - 1).add(new ArrayList<>(g.cheminCycle));
        } else {
            for (int i = 0; i < g.adjacence[noeudDepart].length; i++) {
                if (noeudDepart != i && !g.sommets.get(i).isVisite()
                        && g.adjacence[noeudDepart][i] != null
                        && g.adjacence[noeudDepart][i] != 0) {
                    identifierNoeudsCycles(i, g);
                }
            }
        }
        g.setSommetVisite(noeudDepart, false);
        g.cheminCycle.remove(new Integer(noeudDepart));
    }
    
    /**
     * Effectue la réduction de dette sur un cycle au niveau de la matrice 
     * d'adjacence du graphe
     * @param cheminCycle liste des sommets contenue dans le cycle
     * @param g le graphe qui contient le cycle
     * @author Mounir
     */
    private static void reduireDetteCycle(List<Integer> cheminCycle, Graphe g) {
        int detteRedTemp, detteReduction = g.adjacence[cheminCycle.get(0)][cheminCycle.get((0 + 1) % cheminCycle.size())];
        for (int i = 1; i < cheminCycle.size() - 1; i++) {
            detteRedTemp = g.adjacence[cheminCycle.get(i)][cheminCycle.get((i + 1) % cheminCycle.size())];
            if (detteRedTemp == 0) {
                return;
            }
            if (detteRedTemp < detteReduction) {
                detteReduction = detteRedTemp;
            }
        }
        for (int i = 0; i < cheminCycle.size() - 1; i++) {
            g.adjacence[cheminCycle.get(i)][cheminCycle.get((i + 1) % cheminCycle.size())] -= detteReduction;
        }
    }
    
    /**
     * (Résout l'exercice 2.2 du projet)
     * Une communauté est un ensemble de personnes connectées par leurs 
     * dettes actuelles ou passées. Cette fonction reçoit en entrée le graphe 
     * décrivant les dettes actuelles et passées et 
     * retourne l’ensemble des communautés. (cf. fichier PDF du projet)
     * 
     * @param g Le graphe dont on doit identifier les communautés
     * @return Une liste reprenant l'ensemble des communautés. 
     *         Une communauté consiste en une liste de String où chaque 
     *         String représente le nom du noeud.
     * @author Mourad
     */
    public static ArrayList<ArrayList<String>> identifierCommunautes(Graphe g) {
        ArrayList<ArrayList<String>> communautes = new ArrayList<>();
        ArrayList<String> communaute = new ArrayList<>();
        for(int i = 0; i < g.adjacence.length; i++) {
            parcours(g, communaute, i);
            if(communaute.size() > 0)
                communautes.add(new ArrayList<>(communaute));
            communaute.clear();
        }
        
        g.resetVisite();
        return communautes;
    }
    
    /**
     * Démarre un parcours du graphe. 
     * 
     * @param g Le graphe sur lequel on traite la matrice d'adjacence.
     * @param comm La communauté actuelle que l'on traite dans le parcours.
     * @param noeud Noeud courant (représenté par un entier sur la matrice
     *              d'adjacence).
     * @author Mourad
     */
    private static void parcours(Graphe g, ArrayList<String> comm, int noeud)
    {
        if(!g.estVisite(noeud)) {
            g.setSommetVisite(noeud, true);
            comm.add(g.sommets.get(noeud).getValeur());
            for(int i = 0; i < g.adjacence.length; i++)
                if(g.destinationExiste(noeud, i))
                    parcours(g, comm, i);
        }
    }
    
    /**
     * (Résout l'exercice 2.3 du projet)
     * Algorithme qui reçoit en entrée le graphe décrivant les dettes actuelles 
     * et passées, ainsi qu’un nombre K et retourne l’ensemble des hubs sociaux 
     * tels que leur suppression entraîne la création de 2 communautés d’au 
     * moins K individus. (cf. fichier PDF)
     * 
     * @param g Le graphe dont il faut identifier les hubs sociaux.
     * @param K Le nombre minimum de noeuds présent dans le hub social.
     * @author Mourad
     * @return La liste des hubs sociaux. Un hub social n'est qu'un String 
     *         représentant le nom du noeud. 
     *         Donc, en soi, on retourne une liste de String en soi.
     */
    public static ArrayList<String> hubSociaux(Graphe g, int K) {
        ArrayList<String> hubs = new ArrayList<>();
        ArrayList<ArrayList<String>> communautes;
        communautes = Graphe.identifierCommunautes(g);
        int nbCommunautes = communautes.size();
        int nbKIndividus = (int) communautes.stream()
                                            .filter(s -> s.size() >= K)
                                            .count();
        for(int i = 0; i < g.sommets.size(); i++) {
            g.setSommetVisite(i, true);
            communautes = Graphe.identifierCommunautes(g);
            if((communautes.size() > nbCommunautes)
                && (communautes.stream()
                               .filter(s -> s.size() >= K)
                               .count() > nbKIndividus))
            {
                hubs.add(g.sommets.get(i).getValeur());
            }
        }
        return hubs;
    }
    
    /**
     * (Résout l'exercice 2.4 du projet)
     * un groupe d’amis est l’ensemble des noeuds d’un sous graphe non-dirigé 
     * complet du graphe des dettes. Cette fonction reçoit en entrée le graphe 
     * décrivant les dettes actuelles et passées et retourne le ou l'un des 
     * plus grands groupes d'amis. (cf. fichier PDF)
     * 
     * @param g Le graphe dont on doit identifier les plus grands groupes d'amis
     * @return un string contenant le ou l'un des plus grands groupes d'amis.
     * @author Mounir
     */
    public static String identifierGrpMaxAmis(Graphe g) {
        g.maxGroupAmis = new ArrayList<>();
        ArrayList<Integer> r = new ArrayList<>();
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> p = new ArrayList<>(g.indiceSommetsGraphe());
        ArrayList<ArrayList<Integer>> noeudNonVoisin = new ArrayList<>();
        for (int i = 0; i < g.adjacence.length; i++) {
            noeudNonVoisin.add(g.listSommetsNonVoisin(i));
        }
        GrpMaxAmisRecurssif(r, p, x, noeudNonVoisin, g);
        return g.afficherMaxGrpAmis();
    }

    /**
     * Fonction récurssive qui cherche tous les groupes d'amis de différentes 
     * tailles mais qui ne sauvegarde que le plus grand groupe d'entre eux. 
     * Ce problème est également connue sous le nom "Maximum clique problem" et 
     * est considéré comme un problème NP-Difficile.
     * 
     * @param r représente un résultat temporaire qui peut devenir résultat 
     * final durant le dernier appel de la fonction
     * @param p l'ensemble de tous les noeuds candidats
     * @param x l'ensembe de tous les noeuds exclus
     * @param noeudNonVoisin contient l'ensemble des noeuds non voisins de 
     * chaque noeud tel que noeudNonVoisin.get(i)-->{list des non voisins de i}
     * @param g graphe utilisée pour la sauvegarde du résultat temporaire dans 
     * le cas ou il est accepté
     * @author Mounir
     */
    private static void GrpMaxAmisRecurssif(ArrayList<Integer> r,
            ArrayList<Integer> p, ArrayList<Integer> x,
            final ArrayList<ArrayList<Integer>> noeudNonVoisin, Graphe g) {
        ArrayList<Integer> xTemp;
        ArrayList<Integer> pTemp;
        if (p.isEmpty() && x.isEmpty() && r.size() > g.maxGroupAmis.size()) {
            g.maxGroupAmis = new ArrayList<Integer>(r);
        }
        for (int i = 0; i < p.size(); i++) {
            r.add(p.get(i));
            pTemp = new ArrayList<>(p);
            xTemp = new ArrayList<>(x);
            pTemp.removeAll(noeudNonVoisin.get(p.get(i)));
            xTemp.removeAll(noeudNonVoisin.get(p.get(i)));
            GrpMaxAmisRecurssif(r, pTemp, xTemp, noeudNonVoisin, g);
            r.remove(p.get(i));
            x.add(p.get(i));
            p.remove(p.get(i));
        }
    }
}
