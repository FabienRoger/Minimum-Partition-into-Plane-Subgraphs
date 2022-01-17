import java.util.ArrayList;
import java.util.TreeSet;

public class DsaturOptimizerLight extends Optimizer {
    ArrayList<EdgeForDsaturLight> edges;
    GraphSolution solution;
    int nEdges;
    TreeSet<EdgeForDsaturLight> edgesToColor;

    public void solve(GraphSolution wrongSolution) {
        nEdges = wrongSolution.graph.edges.size();

        edges = new ArrayList<>();
        for (int i = 0; i < nEdges; i++) {
            Edge e = wrongSolution.graph.edges.get(i);
            edges.add(new EdgeForDsaturLight(e.a, e.b, i));
        }

        edgesToColor = new TreeSet<EdgeForDsaturLight>((edgeA, edgeB) -> {
            int nColorCompare = Integer.compare(edgeA.nbColorNeighbours, edgeB.nbColorNeighbours);
            if (nColorCompare != 0)
                return -nColorCompare;
            return Integer.compare(edgeA.id, edgeB.id);
        });

        for (EdgeForDsaturLight edgeForDsatur : edges) {
            edgesToColor.add(edgeForDsatur);
        }

        // First the score is the nb of intersections
        for (int i = 0; i < nEdges; i++) {
            for (int j = 0; j < i; j++) {
                if (edges.get(i).intersect(edges.get(j))) {
                    edges.get(i).score++;
                    edges.get(j).score++;
                }
            }
        }

        EdgeForDsaturLight maxColEdge = maxColEdge();

        // Then it is the number of neighbours with different colors
        for (int i = 0; i < nEdges; i++) {
            edges.get(i).score = 0;
        }

        color(maxColEdge, 0);

        edgesToColor.remove(maxColEdge);

        while (!edgesToColor.isEmpty()) {
            EdgeForDsaturLight toColor = edgesToColor.first();
            int i = 0;
            while (toColor.intersectionsColors.contains(i)) {
                i++;
            }
            color(toColor, i);
            edgesToColor.remove(toColor);
        }

        solution = wrongSolution;

        for (int i = 0; i < nEdges; i++) {
            int id = edges.get(i).id;
            solution.colorEdge(id, edges.get(i).color);
            // System.out.println(id + " " + edges.get(i).color);
        }
    }

    EdgeForDsaturLight maxColEdge() {
        EdgeForDsaturLight r = null;
        int m = -1;
        for (int i = 0; i < nEdges; i++) {
            EdgeForDsaturLight edgeForDsatur = edges.get(i);
            if (edgeForDsatur.score > m) {
                r = edgeForDsatur;
                m = edgeForDsatur.score;
            }
        }
        return r;
    }

    void color(EdgeForDsaturLight e, int color) {
        e.colored = true;
        e.color = color;
        for (int i = 0; i < nEdges; i++) {
            EdgeForDsaturLight neighbour = edges.get(i);

            if (!neighbour.intersect(e))
                continue;

            if (!neighbour.colored && !neighbour.intersectionsColors.contains(color)) {
                edgesToColor.remove(neighbour);
                neighbour.nbColorNeighbours++;
                neighbour.intersectionsColors.add(color);
                edgesToColor.add(neighbour);
            }
        }
    }

}
