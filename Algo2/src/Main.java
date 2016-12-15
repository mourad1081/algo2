import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        String fichier = "graphe1.txt";
        if(args.length > 0) {
            fichier = args[0];
        }
        Graphe g = new Graphe(fichier);
        
        Graphe.reduireDetteGraphe(g);
        System.out.println("Graphe simplifié : ");
        System.out.println("-----------------  ");
        System.out.println(g.toString());
        
        ArrayList<ArrayList<String>> cc = Graphe.identifierCommunautes(g);
        System.out.println("Identification des communautés : ");
        System.out.println("-------------------------------  ");
        for(int i = 0; i < cc.size(); i++) {
            System.out.print("Communauté " + (i+1) + " : " + cc.get(i) + "\n");
        }
        
        System.out.println();
        System.out.println("Identification hubs sociaux (min. 1 individus) :");
        System.out.println("----------------------------------------------- ");
        System.out.println("Hubs : " + Graphe.hubSociaux(g, 1) + "\n");
        System.out.println("Identification hubs sociaux (min. 2 individus) :");
        System.out.println("----------------------------------------------- ");
        System.out.println("Hubs : " + Graphe.hubSociaux(g, 2) + "\n");
        System.out.println("Identification hubs sociaux (min. 3 individus) :");
        System.out.println("----------------------------------------------- ");
        System.out.println("Hubs : " + Graphe.hubSociaux(g, 3) + "\n");
        System.out.println("Identification d'un des plus grands groupes "
                + "d'amis :");
        System.out.println("---------------------------------------------------");
        System.out.println(Graphe.identifierGrpMaxAmis(g));
        
    }
}
