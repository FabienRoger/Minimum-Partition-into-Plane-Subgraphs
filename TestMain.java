public class TestMain {
    public static void main(String[] args) {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 5);
        Point p3 = new Point(6, 1);

        Graph g = new HashGraphWithInfo();

        g.add_arete(p1, p2);
        g.add_arete(p1, p3);

        System.out.println(p1);

        System.out.println("\n");
        System.out.println(g);
    }
}
