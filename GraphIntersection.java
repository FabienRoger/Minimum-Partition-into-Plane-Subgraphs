import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

class GraphIntersection {
	ArrayList<Edge> vertices = new ArrayList<Edge>();
	HashMap<Edge, ArrayList<Edge>> voisins = new HashMap<Edge, ArrayList<Edge>>();
	HashMap<Edge, HashSet<Integer>> colo_voisins = new HashMap<Edge, HashSet<Integer>>();
	HashMap<Edge, int[]> nb_colo_voisins = new HashMap<Edge, int[]>();

	GraphIntersection(GraphSolution graph){
		vertices = graph.graph.edges;
		for(Edge e : graph.graph.edges){
			if(!voisins.containsKey(e)){
				voisins.put(e, new ArrayList<Edge>());
				colo_voisins.put(e, new HashSet<Integer>());
				nb_colo_voisins.put(e, new int[graph.nbColors]);
			}
			for(Edge f : graph.graph.edges){
				if( ! f.equals(e) && e.intersect(f)){
					voisins.get(e).add(f);
					colo_voisins.get(e).add(graph.color(f));
					nb_colo_voisins.get(e)[graph.color(f)]++;
				}
			}
		}
	}

	void modify(Edge chosen, int old_color, int new_color){
		for(Edge vois : voisins.get(chosen)){
			if(nb_colo_voisins.get(vois)[old_color]-- == 0)
				colo_voisins.get(vois).remove(old_color);
			if(nb_colo_voisins.get(vois)[new_color]++ == 0)
				colo_voisins.get(vois).add(new_color);
		}
	}
}
