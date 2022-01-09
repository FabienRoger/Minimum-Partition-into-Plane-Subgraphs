public class Edge {
    Point a, b;

    Edge(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int hashCode() {
        return a.hashCode() % 10000 + b.hashCode() * 10000;
    }

    @Override
    public String toString() {
        return a.toString() + "-" + b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Edge) {
            return this.equal((Edge) o);
        }
        return false;
    }

    boolean equal(Edge o) {
        return this.a.equal(o.a) && this.b.equal(o.b);
    }

    public boolean intersect(Edge e) {
        return Edge.intersect(this.a, this.b, e.a, e.b);
    }

    static private int orientation(Point p, Point q, Point r) {
        long val = (q.y - p.y) * (long) (r.x - q.x) - (q.x - p.x) * (long) (r.y - q.y);

        if (val == 0) {
            return 0;
        }
        if (val > 0) {
            return 1;
        }
        return -1;
    }

    static private boolean intersect(Point p1, Point q1, Point p2, Point q2) {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 == 0 || o2 == 0 || o3 == 0 || o4 == 0)
            return false;

        boolean seg1CutSeg2 = o1 != o2;
        boolean seg2CutSeg1 = o3 != o4;
        return seg1CutSeg2 && seg2CutSeg1;

    }
}
