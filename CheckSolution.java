import java.util.Map;

public class CheckSolution {

    // Naive O(|E|²) implementation : check every distinct pair of edges
    static boolean check(GraphSolution solution) {
        for (int i = 0; i < solution.graph.edges.size(); i++) {
            for (int j = 0; j < i; j++) {
                Edge a = solution.graph.edges.get(i);
                Edge b = solution.graph.edges.get(j);
                int ca = solution.edgeColors[i];
                int cb = solution.edgeColors[j];
                if (!a.equal(b) && a.intersect(b)) {
                    if (ca == cb) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
