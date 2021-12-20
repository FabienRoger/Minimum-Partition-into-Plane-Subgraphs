import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Graph{
	
	int size = 0;
	HashMap<Point, Integer> vertices; //lookup table pour convertir un point en un entier qui lui est attribué
	ArrayList<ArrayList<Point>> graph;
	
	Graph(){
		vertices = new HashMap<Point, Integer>();
		graph = new ArrayList<ArrayList<Point>>();
	}
	
	void update_vertices(Point a){
		//ajoute un point au graphe s'il n'existe pas déjà
		if(!vertices.containsKey(a)){
			vertices.put(a, size);
			graph.add(new ArrayList<Point>());
			size++;
		}
	}

	void add_arete(Point a, Point b){
		//ajoute les points s'ils ne sont pas déjà dans le graphe
		this.update_vertices(a);
		this.update_vertices(b);

		//relie automatiquement dans les deux sens
		graph.get(vertices.get(a)).add(b);
		graph.get(vertices.get(b)).add(a);
	}

	@Override
	public String toString(){
		String str = "";
		for(Map.Entry<Point, Integer> depart : vertices.entrySet()){
			str += depart.getKey().toString() + " : ";
			for(Point arrivee : graph.get(depart.getValue())){
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
