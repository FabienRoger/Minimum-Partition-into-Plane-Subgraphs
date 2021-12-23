import java.util.ArrayList;

public class HashGraphWithInfo extends HashGraph {

    ArrayList<Edge> edges = new ArrayList<Edge>();

    @Override
    void add_arete(Point a, Point b) {
        edges.add(new Edge(a, b));
        super.add_arete(a, b);
    }
}
