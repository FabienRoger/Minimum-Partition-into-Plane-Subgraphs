import java.util.TreeSet;

public class EdgeForDsaturLight extends Edge {

    int id;
    TreeSet<Integer> intersectionsColors = new TreeSet<>();
    int nbColorNeighbours = 0;
    boolean colored = false;
    int color = -1;
    int score = 0;

    EdgeForDsaturLight(Point a, Point b, int id) {
        super(a, b);
        this.id = id;
    }

}