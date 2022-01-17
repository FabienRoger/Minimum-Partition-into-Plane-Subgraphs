import java.util.TreeSet;

public class EdgeForDsaturHeuristicLight extends Edge {

    int id;
    TreeSet<Integer> intersectionsColors = new TreeSet<>();
    int nbColorNeighbours = 0;
    boolean colored = false;
    int color = -1;
    double score = 0;
    // int intersections = 0;

    EdgeForDsaturHeuristicLight(Point a, Point b, int id) {
        super(a, b);
        this.id = id;
    }

}