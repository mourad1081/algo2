import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fichier = "";
        if(args.length > 0)
            fichier = args[0];
        else {
            int numberTest = lireNumeroTest();
            switch (numberTest) {
                case 0:
                    return;
                case 1: case 2: case 3:
                case 4: case 5:
                    fichier = "grapheTest/graphe"+numberTest+".txt";
                    break;
                case 6:
                    fichier = "grapheTest/grapheProjet.txt";
                    break;
            }
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
            System.out.println("| Identification des hubs sociaux   |");
            System.out.println("+===================================+");
            System.out.println("|   K   |            Hubs           |");
            System.out.println("+-------+---------------------------+");
            for(int i = 1; i <= 5; i++)
                System.out.format("%-2s%5d%-3s%25s%-2s%n", "|", i, " | ",
                                  Graphe.hubSociaux(g, i), " |" );
            System.out.println("+-----------------------------------+");
            System.out.println("");

            System.out.println("Identification d'un des plus grands groupes "
                    + "d'amis :");
            System.out.println("---------------------------------------------------");
            System.out.println(Graphe.identifierGrpMaxAmis(g));
        } catch(IOException e) {
            System.err.println("Erreur : Veuillez spécifier un fichier correct.");
        }
    }

    public static int lireNumeroTest(){
        int numTest = -1;
        String filesNumber = "\n[0] Quitter\n\n[1] graphe1.txt\n[2] graphe2.txt"
                           +"\n[3] graphe3.txt\n[4] graphe4.txt\n"
                           +"[5] graphe5.txt\n[6] grapheProjet.txt";
        String askNumber = "Encoder le numéro du graphe test à lancer [1 - 6] "
                         +"(exemple 1 : pour graphe1.txt) : ";
        Scanner kb = new Scanner(System.in);
        System.out.println("---------------------------------------------------"
                          +"---------------------------------");
        System.out.println("Voici nos fichiers tests que vous pouvez éxecuter :");
        System.out.println(filesNumber);
        while(numTest < 0 || numTest > 6){
            System.out.print(askNumber);
            while(!kb.hasNextInt()){
                kb.next();
                System.out.print(askNumber);
            }
            numTest = kb.nextInt();
        }
        System.out.println("---------------------------------------------------"
                          +"---------------------------------");
        return numTest;
    }
}
