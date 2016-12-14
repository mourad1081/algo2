import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Graphe g = new Graphe("graphe2.txt");
        System.out.println(g.toString());
        
        ArrayList<ArrayList<String>> cc = Graphe.identifierCommunautes(g);
        System.out.println("Identification des communautés : ");
        System.out.println("-------------------------------  ");
        for(int i = 0; i < cc.size(); i++) {
            System.out.print("Communauté " + (i+1) + " : " + cc.get(i) + "\n");
        }
    }
}
