public class TestMain {
    public static void main(String[] args) {
        Point p1 = new Point(0, 2);
        Point p2 = new Point(3, -1);
        Point p3 = new Point(-1, -2);
        Point p4 = new Point(25, 26);

        Graph g = new HashGraphWithInfo();

        g.add_arete(p1, p2);
        g.add_arete(p1, p3);
        g.add_arete(p3, p4);
        g.add_arete(p1, p4);

        System.out.println(p1);

        System.out.println("\n");
        System.out.println(g);

        GraphSolution solution = new GraphSolution();
        solution.colorEdge(p3, p4, 1);
        solution.colorEdge(p1, p2, 2);
        solution.colorEdge(p1, p3, 1);
        solution.colorEdge(p1, p4, 1);

        System.out.println(CheckSolution.check(solution));
    }
}
