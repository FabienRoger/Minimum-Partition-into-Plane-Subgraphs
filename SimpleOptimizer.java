import java.util.ArrayList;

public class SimpleOptimizer extends Optimizer {
    ArrayList<Edge> edges;
    GraphSolution solution;
    int nEdges;
    ArrayList<ArrayList<Integer>> intersections;

    public GraphSolution solve(GraphSolution wrongSolution) {
        edges = wrongSolution.graph.edges;
        solution = wrongSolution;
        nEdges = wrongSolution.graph.edges.size();
        intersections = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < nEdges; i++) {
            intersections.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < nEdges; i++) {
            for (int j = 0; j < i; j++) {
                if (edges.get(i).intersect(edges.get(j))) {
                    intersections.get(i).add(j);
                    // intersections.get(j).add(i);
                }
            }
        }
        for (int i = 0; i < nEdges; i++) {
            solution.colorEdge(i, colorsMex(i));
        }

        return solution;
    }

    // private int findBadEdge() {
    // for (int i = 0; i < nEdges; i++) {
    // for (Integer j : intersections.get(i)) {
    // if (solution.edgeColors[i] == solution.edgeColors[j]) {
    // return i;
    // }
    // }
    // }

    // return -1;
    // }

    private int colorsMex(int edge) {
        boolean[] colorSeen = new boolean[nEdges];

        for (Integer j : intersections.get(edge)) {
            colorSeen[solution.edgeColors[j]] = true;
        }

        for (int i = 0; i < colorSeen.length; i++) {
            if (!colorSeen[i]) {
                return i;
            }
        }
        return colorSeen.length;
    }
}
