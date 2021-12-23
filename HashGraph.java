import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class HashGraph extends Graph {

	int size = 0;
	HashMap<Point, ArrayList<Point>> graph = new HashMap<Point, ArrayList<Point>>();

	void update_vertices(Point a) {
		// ajoute un point au graphe s'il n'existe pas déjà
		if (!graph.containsKey(a)) {
			graph.put(a, new ArrayList<Point>());
			size++;
		}
	}

	@Override
	void add_arete(Point a, Point b) {
		this.update_vertices(a);
		this.update_vertices(b);

		// relie automatiquement dans les deux sens
		graph.get(a).add(b);
		graph.get(b).add(a);
	}

	@Override
	public String toString() {
		String str = "";
		for (Map.Entry<Point, ArrayList<Point>> depart : graph.entrySet()) {
			str += depart.getKey().toString() + " : ";
			for (Point arrivee : depart.getValue()) {
				str += arrivee.toString() + " ";
			}
			str += "\n";
		}
		return str;
	}

	void show() {
		// affiche le graphe
	}

	int solve() {
		// return sol_fct(this);
		return 0;
	}
}
