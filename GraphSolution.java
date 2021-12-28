
public class GraphSolution {
    int nbColors = 0;
    // Colors should be >= 1

    int[] edgeColorsList;
    HashGraphWithInfo graph;

    String id;

    GraphSolution(HashGraphWithInfo graph) {
        id = graph.id;
        this.graph = graph;

        edgeColorsList = new int[graph.edges.size()];
        for (int i = 0; i < edgeColorsList.length; i++) {
            edgeColorsList[i] = 1;
        }
        for (Edge edge : graph.edges) {
            colorEdge(edge, 1);
        }
    }

    void colorEdge(Point a, Point b, int color) {
        this.colorEdge(new Edge(a, b), color);
    }

    void colorEdge(Edge e, int color) {
        colorEdge(this.graph.edgeHashMap.get(e), color);
    }

    void colorEdge(int i, int color) {
        nbColors = Math.max(color, nbColors);
        edgeColorsList[i] = color;
    }
}
