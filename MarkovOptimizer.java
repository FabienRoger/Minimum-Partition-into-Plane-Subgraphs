import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class MarkovOptimizer extends Optimizer {
	GraphIntersection graph;
	GraphSolution solution;

	int nColors; // Nombre de couleurs utilisées
	HashSet<Integer> usedColors = new HashSet<Integer>(); // La couleur est utilisée ?
	int[] manyColors; // Combien de fois est-elle utilisée ?

	static int N_iter = 50000;

	public void solve(GraphSolution wrongSolution) {
		// doit partir d'une solution valide non nulle
		int nEdges = wrongSolution.graph.edges.size();

		graph = new GraphIntersection(wrongSolution);

		solution = wrongSolution;

		/*
		 * nColors = 0;
		 * for(Edge e : graph.vertices){
		 * usedColors.add(nColors);
		 * solution.colorEdge(e, nColors++);
		 * }
		 * 
		 * solution.nbColors = nColors;
		 */

		nColors = solution.nbColors;

		manyColors = new int[nColors];
		for (Edge e : graph.vertices) {
			for (int i = 0; i < nColors; i++)
				manyColors[i] += graph.nb_colo_voisins.get(e)[i];
		}

		Random random = new Random();
		double last_pot = potential(graph);

		double best_pot = last_pot;
		GraphSolution attempting = solution;

		int c;
		Edge chosen;

		for (int i = 0; i < N_iter; i++) {

			if (i % 1000 == 0)
				System.out.println("i : " + i);

			if (nColors <= 1 || usedColors.size() <= 1)
				break;
			// Choisir random edge

			do {

				chosen = graph.vertices.get(random.nextInt(nEdges));

			} while (graph.colo_voisins.get(chosen).size() == nColors);
			// Choisir au pif sa couleur ( on tire tant qu'il faut -> set couleurs voisins)
			Set<Integer> set = new HashSet<Integer>();
			set.addAll(usedColors);

			do {
				c = getRandomElement(set);
				set.remove(c);
			} while (graph.colo_voisins.get(chosen).contains(c));

			// La changer (O(deg(edge)))
			double tentative = potential(graph, chosen, c);

			if (random.nextDouble() < Math.exp((last_pot - tentative) / temperature(i))) {

				manyColors[attempting.color(chosen)]--;
				if (manyColors[attempting.color(chosen)] == 0) {
					usedColors.remove(attempting.color(chosen));
					nColors--;
				}
				manyColors[c]++;

				graph.modify(chosen, attempting.color(chosen), c);

				attempting.colorEdge(chosen, c);

				if (tentative < best_pot) {
					solution = attempting;
					best_pot = tentative;

				}

				last_pot = tentative;
			}

		}

		System.out.println("alors:  " + nColors);

	}

	double temperature(int i) {
		return 300 * Math.pow(i + 1, -.03);
	}

	double potential(GraphIntersection graph) {
		return nColors;
	}

	double potential(GraphIntersection graph, Edge chosen, int new_color) {
		return nColors;
	}

	// Trouvé sur internet
	private static <E> E getRandomElement(Set<? extends E> set) {
		Random random = new Random();

		// Generate a random number using nextInt
		// method of the Random class.
		int randomNumber = random.nextInt(set.size());

		Iterator<? extends E> iterator = set.iterator();

		int currentIndex = 0;
		E randomElement = null;

		// iterate the HashSet
		while (iterator.hasNext()) {

			randomElement = iterator.next();

			// if current index is equal to random number
			if (currentIndex == randomNumber)
				return randomElement;

			// increase the current index
			currentIndex++;
		}

		return randomElement;
	}
}
