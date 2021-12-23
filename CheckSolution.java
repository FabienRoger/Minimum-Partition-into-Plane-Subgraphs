import java.util.Map;

public class CheckSolution {

    // Naive O(|E|²) implementation : check every distinct pair of edges
    static boolean check(GraphSolution solution) {
        Map<Edge, Integer> coloring = solution.edgeColors;

        for (Map.Entry<Edge, Integer> set1 : coloring.entrySet()) {
            for (Map.Entry<Edge, Integer> set2 : coloring.entrySet()) {
                Edge a = set1.getKey();
                Edge b = set2.getKey();
                int ca = set1.getValue();
                int cb = set2.getValue();
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
