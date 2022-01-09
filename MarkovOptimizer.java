import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.lang.Math;

public class MarkovOptimizer extends Optimizer {
	GraphIntersection graph;
	GraphSolution solution;

	int nColors; //Nombre de couleurs utilisées
	HashSet<Integer> usedColors; //La couleur est utilisée ?
	int[] manyColors; //Combien de fois est-elle utilisée ?

	static int N_iter = 5000;


	public void solve(GraphSolution wrongSolution){
		//solve de zéro

		int nEdges = wrongSolution.graph.edges.size();

		graph = new GraphIntersection(wrongSolution);

		solution = wrongSolution;

		nColors = 0;
		for(Edge e : graph.vertices){
			usedColors.add(nColors);
			solution.colorEdge(e, nColors++);
		}

		manyColors = new int[nColors];
		for(int i = 0; i<nColors; i++){
			manyColors[i] = 1;
		}

		Random random = new Random();
		double last_pot = potential(graph);

		double best_pot = last_pot;
		GraphSolution attempting = solution;

		for(int i = 0; i<N_iter; i++){
			//Choisir random edge
			int c = random.nextInt(nEdges);
			Edge chosen = graph.vertices.get(c);
			//Choisir au pif sa couleur ( on tire tant qu'il faut -> set couleurs voisins)
			////opé à ne faire que si changement de nColors
			ArrayList<Integer> set = new ArrayList<Integer>(usedColors);

			do{
				c = random.nextInt(nColors);
				c = set.get(c);
			}while(graph.colo_voisins.get(chosen).contains(c));

			//La changer (O(deg(edge)))
			double tentative = potential(graph, chosen, c);
			if(random.nextDouble() < Math.exp((last_pot - tentative)/temperature(i))){
				graph.modify(chosen, attempting.color(chosen), c);
				attempting.colorEdge(chosen, c);

				if(tentative < best_pot){
					solution = attempting;
					best_pot = tentative;
				}

				last_pot = tentative;
			}
		}
	}

	double temperature(int i){
		return 300 * Math.pow(i+1, -.03);
	}

	double potential(GraphIntersection graph){
		return nColors;
	}

	double potential(GraphIntersection graph, Edge chosen, int new_color){
		return nColors;
	}
}
