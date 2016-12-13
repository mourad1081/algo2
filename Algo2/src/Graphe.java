
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Graphe {
    private int[][] adjacence;
    
    public Graphe() throws IOException {
        this(null);
    }
    
    public Graphe(String path) throws FileNotFoundException, IOException {
        if(path != null) {
            int size;
            BufferedReader in = new BufferedReader(new FileReader(path));
            size = Integer.parseInt(in.readLine());
            this.adjacence = new int[size][size];
        }
    }
    
    
}
