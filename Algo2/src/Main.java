import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String fichier = "";
        if(args.length > 0) 
            fichier = args[0];
        else {
            System.err.println("Erreur : Veuillez spécifier un fichier à parser.");
            return;
        }
        try  {
            Graphe g = new Graphe(fichier);
            Graphe.reduireDetteGraphe(g);
            System.out.println("+-----------------------------------+");
            System.out.println("|          Graphe simplifie         |");
            System.out.println("+===================================+");
            System.out.println(g.toString());
            System.out.println("");
            
            // Identification des communautés
            ArrayList<ArrayList<String>> cc = Graphe.identifierCommunautes(g);
            System.out.println("+-----------------------------------+");
            System.out.println("|  Identification des communautes   |");
            System.out.println("+===================================+");
            System.out.println("| num.  |         Communaute        |");
            System.out.println("+-------+---------------------------+");
            for(int i = 0; i < cc.size(); i++)
                System.out.format("%-2s%5d%-3s%25s%-2s%n", "|", i, " | ", 
                                  cc.get(i), " |" );
            System.out.println("+-----------------------------------+");
            System.out.println("");
            
            // Identification des hubs sociaux
            System.out.println("+-----------------------------------+");
            System.out.println("|  Identification des hubs sociaux  |");
            System.out.println("+===================================+");
            System.out.println("|   K   |            Hubs           |");
            System.out.println("+-------+---------------------------+");
            for(int i = 1; i <= 5; i++)
                System.out.format("%-2s%5d%-3s%25s%-2s%n", "|", i, " | ", 
                                  Graphe.hubSociaux(g, i), " |" );
            System.out.println("+-----------------------------------+");
            System.out.println("");
            
            // Plus grand groupe d'amis
            System.out.println("+-----------------------------------+");
            System.out.println("|  Identification d'un des plus     | \n"
                             + "|      grands groupes d'amis        |");
            System.out.println("+===================================+");
            System.out.println(Graphe.identifierGrpMaxAmis(g));
        } catch(IOException e) {
            System.err.println("Erreur : Veuillez spécifier un fichier correct.");
        }
    }
}
