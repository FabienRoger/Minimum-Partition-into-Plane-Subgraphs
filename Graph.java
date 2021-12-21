import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Graph{
	
	int size = 0;
	HashMap<Point, ArrayList<Point>> graph;
	

	Graph(){
		graph = new HashMap<Point, ArrayList<Point>>();
	}
	
	void update_vertices(Point a){
		//ajoute un point au graphe s'il n'existe pas déjà
		if(!graph.containsKey(a)){
			graph.put(a, new ArrayList<Point>());
			size++;
		}
	}

	void add_arete(Point a, Point b){
		this.update_vertices(a);
		this.update_vertices(b);

		//relie automatiquement dans les deux sens
		graph.get(a).add(b);
		graph.get(b).add(a);
	}

	@Override
	public String toString(){
		String str = "";
		for(Map.Entry<Point, ArrayList<Point>> depart : graph.entrySet()){
			str += depart.getKey().toString() + " : ";
			for(Point arrivee : depart.getValue()){
				str += arrivee.toString() + " ";
			}
			str += "\n";
		}
		return str;
	}

	void show(){
		//affiche le graphe
	}

	int solve(){
		//return sol_fct(this);
		return 0;
	}
	
	public static void main(String[] args){
		Point p1 = new Point(2,3);
		Point p2 = new Point(4,5);
		Point p3 = new Point(6,1);


		Graph g = new Graph();

		g.add_arete(p1, p2);
		g.add_arete(p1, p3);

		System.out.println(p1);

		System.out.println("\n");
		System.out.println(g);
	}
	


}
