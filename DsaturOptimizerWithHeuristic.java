import java.util.ArrayList;
import java.util.TreeSet;

public class DsaturOptimizerWithHeuristic extends Optimizer {
    double lengthWeight, centerWeight;
    boolean useHeuristicToStart, useHeuristicToSort;

    DsaturOptimizerWithHeuristic(double lengthWeight, double centerWeight,
            boolean useHeuristicToStart, boolean useHeuristicToSort) {
        this.lengthWeight = lengthWeight;
        this.centerWeight = centerWeight;
        this.useHeuristicToStart = useHeuristicToStart;
        this.useHeuristicToSort = useHeuristicToSort;
    }

    ArrayList<EdgeForDsatur> edges;
    GraphSolution solution;
    int nEdges;
    TreeSet<EdgeForDsatur> edgesToColor;

    public void solve(GraphSolution wrongSolution) {
        nEdges = wrongSolution.graph.edges.size();

        double lengthSum = 0;
        double xSum = 0;
        double ySum = 0;

        edges = new ArrayList<>();
        for (int i = 0; i < nEdges; i++) {
            Edge e = wrongSolution.graph.edges.get(i);
            edges.add(new EdgeForDsatur(e.a, e.b, i));

            lengthSum += e.a.dist(e.b);
            xSum += e.a.x + e.b.x;
            ySum += e.a.y + e.b.y;
        }

        double avgLength = lengthSum / nEdges;
        Point avgPoint = new Point((int) Math.round(xSum / (2 * nEdges)), (int) Math.round(ySum / (2 * nEdges)));

        for (int i = 0; i < nEdges; i++) {
            for (int j = 0; j < i; j++) {
                if (edges.get(i).intersect(edges.get(j))) {
                    edges.get(i).intersections.add(edges.get(j));
                    edges.get(j).intersections.add(edges.get(i));
                }
            }
        }

        for (EdgeForDsatur edge : edges) {
            Point midPoint = new Point((edge.a.x + edge.b.x) / 2, (edge.a.y + edge.b.y) / 2);
            double baseScore = lengthWeight * edge.a.dist(edge.b) / avgLength
                    + centerWeight * avgPoint.dist(midPoint) / avgLength;
            edge.score = baseScore;
        }

        edgesToColor = new TreeSet<EdgeForDsatur>((edgeA, edgeB) -> {
            int scoreCompare = useHeuristicToSort ? Double.compare(edgeA.score, edgeB.score)
                    : Integer.compare(edgeA.nbColorNeighbours, edgeB.nbColorNeighbours);
            if (scoreCompare != 0)
                return -scoreCompare;
            return Integer.compare(edgeA.id, edgeB.id);
        });

        for (EdgeForDsatur edgeForDsatur : edges) {
            edgesToColor.add(edgeForDsatur);
        }

        EdgeForDsatur maxColEdge = useHeuristicToStart ? maxScoreEdge() : maxColEdge();
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

    EdgeForDsatur maxScoreEdge() {
        EdgeForDsatur r = null;
        double m = -1;
        for (EdgeForDsatur edgeForDsatur : edges) {
            if (edgeForDsatur.score > m) {
                r = edgeForDsatur;
                m = edgeForDsatur.score;
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
                neighbour.score++;
                neighbour.intersectionsColors.add(color);
                edgesToColor.add(neighbour);
            }
        }
    }

}
