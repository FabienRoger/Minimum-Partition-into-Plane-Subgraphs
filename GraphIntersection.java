import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

class GraphIntersection {
	ArrayList<Edge> vertices = new ArrayList<Edge>();
	HashMap<Edge, ArrayList<Edge>> voisins = new HashMap<Edge, ArrayList<Edge>>();
	HashMap<Edge, HashSet<Integer>> colo_voisins = new HashMap<Edge, HashSet<Integer>>();

	GraphIntersection(GraphSolution graph){
		vertices = graph.graph.edges;
		for(Edge e : graph.graph.edges){
			for(Edge f : graph.graph.edges){
				if( ! f.equals(e) && e.intersect(f)){
					if(!voisins.containsKey(e)){
						voisins.put(e, new ArrayList<Edge>());
						colo_voisins.put(e, new HashSet<Integer>());
					}
					voisins.get(e).add(f);
					colo_voisins.get(e).add(graph.color(f));
				}
			}
		}
	}

	void modify(Edge chosen, int old_color, int new_color){
		for(Edge vois : voisins.get(chosen)){
			colo_voisins.get(vois).remove(old_color);
			colo_voisins.get(vois).add(new_color);
		}
	}
}
