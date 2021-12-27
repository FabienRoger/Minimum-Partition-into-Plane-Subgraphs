import java.util.ArrayList;

public class HashGraphWithInfo extends HashGraph {

    ArrayList<Edge> edges = new ArrayList<Edge>();

	HashGraphWithInfo(String s_id){
		id = s_id;
	}

    @Override
    void add_arete(Point a, Point b) {
        edges.add(new Edge(a, b));
        super.add_arete(a, b);
    }
	
	//version rapide utilisée par le JSON
	void add_arete(int i, int j){
		edges.add(new Edge(vertices.get(i), vertices.get(j)));
        super.add_arete(i, j);
	}
}
