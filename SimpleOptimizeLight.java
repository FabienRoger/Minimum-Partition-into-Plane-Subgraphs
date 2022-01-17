import java.util.ArrayList;
import java.util.TreeSet;

public class SimpleOptimizeLight extends Optimizer {
    ArrayList<Edge> edges;
    GraphSolution solution;
    int nEdges;

    public void solve(GraphSolution wrongSolution) {
        edges = wrongSolution.graph.edges;
        solution = wrongSolution;
        nEdges = wrongSolution.graph.edges.size();

        for (int i = 0; i < nEdges; i++) {
            solution.colorEdge(i, colorsMex(i));
        }
    }

    private int colorsMex(int edge) {
        TreeSet<Integer> colorSeen = new TreeSet<Integer>();

        for (int j = 0; j < edge; j++) {
            if (edges.get(edge).intersect(edges.get(j))) {
                colorSeen.add(solution.edgeColors[j]);
            }
        }

        for (int i = 0; i < edge; i++) {
            if (!colorSeen.contains(i)) {
                return i;
            }
        }
        return edge;
    }
}
