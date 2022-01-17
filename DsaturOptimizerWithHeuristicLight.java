
import java.util.ArrayList;
import java.util.TreeSet;

public class DsaturOptimizerWithHeuristicLight extends Optimizer {
    double lengthWeight, centerWeight;
    boolean useHeuristicToStart, useHeuristicToSort;

    DsaturOptimizerWithHeuristicLight(double lengthWeight) {
        this.lengthWeight = lengthWeight;
    }

    ArrayList<EdgeForDsaturHeuristicLight> edges;
    GraphSolution solution;
    int nEdges;
    TreeSet<EdgeForDsaturHeuristicLight> edgesToColor;

    public void solve(GraphSolution wrongSolution) {
        nEdges = wrongSolution.graph.edges.size();

        double lengthSum = 0;

        edges = new ArrayList<>();
        for (int i = 0; i < nEdges; i++) {
            Edge e = wrongSolution.graph.edges.get(i);
            edges.add(new EdgeForDsaturHeuristicLight(e.a, e.b, i));

            lengthSum += e.a.dist(e.b);
        }

        double avgLength = lengthSum / nEdges;

        for (EdgeForDsaturHeuristicLight edge : edges) {
            double baseScore = lengthWeight * edge.a.dist(edge.b) / avgLength;
            edge.score = baseScore;
        }

        edgesToColor = new TreeSet<EdgeForDsaturHeuristicLight>((edgeA, edgeB) -> {
            int scoreCompare = Double.compare(edgeA.score, edgeB.score);
            if (scoreCompare != 0)
                return -scoreCompare;
            return Integer.compare(edgeA.id, edgeB.id);
        });

        for (EdgeForDsaturHeuristicLight edgeForDsatur : edges) {
            edgesToColor.add(edgeForDsatur);
        }

        // // First the score is the nb of intersections
        // for (int i = 0; i < nEdges; i++) {
        // for (int j = 0; j < i; j++) {
        // if (edges.get(i).intersect(edges.get(j))) {
        // edges.get(i).intersections++;
        // edges.get(j).intersections++;
        // }
        // }
        // }

        EdgeForDsaturHeuristicLight maxColEdge = maxScoreEdge();
        color(maxColEdge, 0);

        edgesToColor.remove(maxColEdge);

        while (!edgesToColor.isEmpty()) {
            EdgeForDsaturHeuristicLight toColor = edgesToColor.first();
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

    // EdgeForDsaturHeuristicLight maxIntersectionsEdge() {
    // EdgeForDsaturHeuristicLight r = null;
    // int m = -1;
    // for (EdgeForDsaturHeuristicLight edgeForDsatur : edges) {
    // if (edgeForDsatur.intersections > m) {
    // r = edgeForDsatur;
    // m = edgeForDsatur.intersections;
    // }
    // }
    // return r;
    // }

    EdgeForDsaturHeuristicLight maxScoreEdge() {
        EdgeForDsaturHeuristicLight r = null;
        double m = -1;
        for (EdgeForDsaturHeuristicLight edgeForDsatur : edges) {
            if (edgeForDsatur.score > m) {
                r = edgeForDsatur;
                m = edgeForDsatur.score;
            }
        }
        return r;
    }

    void color(EdgeForDsaturHeuristicLight e, int color) {
        e.colored = true;
        e.color = color;
        for (int i = 0; i < nEdges; i++) {
            EdgeForDsaturHeuristicLight neighbour = edges.get(i);

            if (!neighbour.intersect(e))
                continue;

            if (!neighbour.colored && !neighbour.intersectionsColors.contains(color)) {
                edgesToColor.remove(neighbour);
                neighbour.nbColorNeighbours++;
                neighbour.score++;
                neighbour.intersectionsColors.add(color);
                edgesToColor.add(neighbour);
            }
        }
    }

}
