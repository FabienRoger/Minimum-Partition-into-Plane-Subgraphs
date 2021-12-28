import java.util.ArrayList;
import java.util.HashMap;

public class HashGraphWithInfo extends HashGraph {

    ArrayList<Edge> edges = new ArrayList<Edge>();
    HashMap<Edge, Integer> edgeHashMap = new HashMap<>();

    HashGraphWithInfo(String s_id) {
        id = s_id;
    }

    @Override
    void add_arete(Point a, Point b) {
        Edge e = new Edge(a, b);
        edges.add(e);
        edgeHashMap.put(e, edges.size() - 1);
        super.add_arete(a, b);
    }

    // version rapide utilisée par le JSON
    void add_arete(int i, int j) {
        Edge e = new Edge(vertices.get(i), vertices.get(j));
        edges.add(e);
        edgeHashMap.put(e, edges.size() - 1);
        super.add_arete(i, j);
    }
}
