
public class GraphSolution {
    int nbColors = 0;
    // Colors should be >= 0

    int[] edgeColors;
    HashGraphWithInfo graph;

    String id;

    GraphSolution(HashGraphWithInfo graph) {
        id = graph.id;
        this.graph = graph;

        edgeColors = new int[graph.edges.size()];
        for (Edge edge : graph.edges) {
            colorEdge(edge, 0);
        }
    }

    void colorEdge(Point a, Point b, int color) {
        this.colorEdge(new Edge(a, b), color);
    }

    void colorEdge(Edge e, int color) {
        colorEdge(this.graph.edgeHashMap.get(e), color);
    }

    void colorEdge(int i, int color) {
        nbColors = Math.max(color + 1, nbColors);
        edgeColors[i] = color;
    }
}
