import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Graphe g = new Graphe("grapheText.txt");
        System.out.println(g.toString());
    }
}
