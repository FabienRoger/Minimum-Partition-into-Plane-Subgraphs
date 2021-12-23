import java.util.HashMap;

public class GraphSolution {
    int nbColors = 0;
    // Colors should be >= 1
    HashMap<Edge, Integer> edgeColors = new HashMap<>();

    GraphSolution() {

    }

    GraphSolution(HashGraphWithInfo graph) {
        for (Edge edge : graph.edges) {
            colorEdge(edge, 1);
        }
    }

    void colorEdge(Point a, Point b, int color) {
        this.colorEdge(new Edge(a, b), color);
    }

    void colorEdge(Edge e, int color) {
        nbColors = Math.max(color, nbColors);
        if (edgeColors.containsKey(e)) {

        }
        edgeColors.put(e, color);
    }
}
