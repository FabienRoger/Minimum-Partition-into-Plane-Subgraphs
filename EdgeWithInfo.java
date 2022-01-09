public class EdgeWithInfo extends Edge {

    int id;
    int score;

    EdgeWithInfo(Point a, Point b, int id) {
        super(a, b);
        this.id = id;
        this.score = 0;
    }
}
