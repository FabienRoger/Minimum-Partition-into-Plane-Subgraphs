import java.util.ArrayList;
import java.util.Collections;

public class GreedyOptimizer extends Optimizer {
    ArrayList<EdgeWithInfo> edges;
    int edgesColor[];
    GraphSolution solution;
    int nEdges;

    public void solve(GraphSolution wrongSolution) {
        nEdges = wrongSolution.graph.edges.size();

        edges = new ArrayList<>();
        for (int i = 0; i < nEdges; i++) {
            Edge e = wrongSolution.graph.edges.get(i);
            edges.add(new EdgeWithInfo(e.a, e.b, i));
        }
        for (int i = 0; i < nEdges; i++) {
            for (int j = 0; j < i; j++) {
                if (edges.get(i).intersect(edges.get(j))) {
                    edges.get(i).score++;
                    edges.get(j).score++;
                }
            }
        }

        edgesColor = new int[nEdges];
        solution = wrongSolution;

        Collections.sort(edges,
                (e1, e2) -> (e1.score > e2.score ? -1 : (e1.score == e2.score ? 0 : 1)));

        for (int i = 0; i < nEdges; i++) {
            edgesColor[i] = colorsMex(i);
            // System.out.println(
            // i + " " + edgesColor[i] + " " + edges.get(i).a + " " + edges.get(i).b + " " +
            // edges.get(i).id);
        }

        for (int i = 0; i < nEdges; i++) {
            int id = edges.get(i).id;
            solution.colorEdge(id, edgesColor[i]);
        }
    }

    private int colorsMex(int edge) {
        boolean[] colorSeen = new boolean[nEdges];

        for (int i = 0; i < edge; i++) {
            if (edges.get(edge).intersect(edges.get(i))) {
                colorSeen[edgesColor[i]] = true;
            }
        }

        for (int i = 0; i < colorSeen.length; i++) {
            if (!colorSeen[i]) {
                return i;
            }
        }
        return colorSeen.length;
    }

}
