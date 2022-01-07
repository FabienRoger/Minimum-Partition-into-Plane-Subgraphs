import java.util.ArrayList;
import java.util.TreeSet;

public class EdgeForDsatur extends Edge {

    int id;
    ArrayList<EdgeForDsatur> intersections = new ArrayList<>();
    TreeSet<Integer> intersectionsColors = new TreeSet<>();
    int nbColorNeighbours = 0;
    boolean colored = false;
    int color = -1;

    EdgeForDsatur(Point a, Point b, int id) {
        super(a, b);
        this.id = id;
    }

}