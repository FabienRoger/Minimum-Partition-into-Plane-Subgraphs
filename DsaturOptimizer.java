import java.util.ArrayList;
import java.util.TreeSet;

public class DsaturOptimizer extends Optimizer {
    ArrayList<EdgeForDsatur> edges;
    GraphSolution solution;
    int nEdges;
    TreeSet<EdgeForDsatur> edgesToColor;

    public void solve(GraphSolution wrongSolution) {
        nEdges = wrongSolution.graph.edges.size();

        edges = new ArrayList<>();
        for (int i = 0; i < nEdges; i++) {
            Edge e = wrongSolution.graph.edges.get(i);
            edges.add(new EdgeForDsatur(e.a, e.b, i));
        }

        for (int i = 0; i < nEdges; i++) {
            for (int j = 0; j < i; j++) {
                if (edges.get(i).intersect(edges.get(j))) {
                    edges.get(i).intersections.add(edges.get(j));
                    edges.get(j).intersections.add(edges.get(i));
                }
            }
        }

        edgesToColor = new TreeSet<EdgeForDsatur>((edgeA, edgeB) -> {
            int nColorCompare = Integer.compare(edgeA.nbColorNeighbours, edgeB.nbColorNeighbours);
            if (nColorCompare != 0)
                return -nColorCompare;
            return Integer.compare(edgeA.id, edgeB.id);
        });

        for (EdgeForDsatur edgeForDsatur : edges) {
            edgesToColor.add(edgeForDsatur);
        }

        EdgeForDsatur maxColEdge = maxColEdge();
        color(maxColEdge, 0);

        edgesToColor.remove(maxColEdge);

        while (!edgesToColor.isEmpty()) {
            EdgeForDsatur toColor = edgesToColor.first();
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

    EdgeForDsatur maxColEdge() {
        EdgeForDsatur r = null;
        int m = -1;
        for (EdgeForDsatur edgeForDsatur : edges) {
            if (edgeForDsatur.intersections.size() > m) {
                r = edgeForDsatur;
                m = edgeForDsatur.intersections.size();
            }
        }
        return r;
    }

    void color(EdgeForDsatur e, int color) {
        e.colored = true;
        e.color = color;
        for (EdgeForDsatur neighbour : e.intersections) {
            if (!neighbour.colored && !neighbour.intersectionsColors.contains(color)) {
                edgesToColor.remove(neighbour);
                neighbour.nbColorNeighbours++;
                neighbour.intersectionsColors.add(color);
                edgesToColor.add(neighbour);
            }
        }
    }

}
