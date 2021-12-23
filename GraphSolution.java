import java.util.HashMap;

public class GraphSolution {
    int nbColors = 0;
    HashMap<Edge, Integer> edgeColors = new HashMap<>();

    void colorEdge(Point a, Point b, int color) {
        this.colorEdge(new Edge(a, b), color);
    }

    void colorEdge(Edge e, int color) {
        nbColors = Math.max(color, nbColors);
        edgeColors.put(e, color);
    }
}
