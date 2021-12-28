import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 * This class provides methods for dealing with input/output for JSON files
 * 
 * @author Luca Castelli Aleardi (INF421, Ecole Polytechnique, dec 2021)
 *         Heavily modified
 */
public class IO {

	/**
	 * Load an instance of the 'Minimum Partition problem' from an input JSON file
	 * 
	 * @param filename name of the input file
	 */
	public static HashGraphWithInfo loadInput(String filename) {

		System.out.print("Reading JSON input file: " + filename + "...");
		JSONObject json;

		json = loadFile(filename);
		System.out.println("ok");

		// System.out.println(json);

		String id = json.getString("id");
		int n = json.getInt("n");
		int m = json.getInt("m");

		// Init du graphe
		HashGraphWithInfo graphe = new HashGraphWithInfo(id);

		System.out.println("Name of the input instance: " + id);

		System.out.print("Reading coordinates...");
		JSONArray xCoord = json.getJSONArray("x");
		JSONArray yCoord = json.getJSONArray("y");

		if (xCoord.size() != n)
			throw new Error("Error: wrong number of vertex x-coordinates");
		if (yCoord.size() != n)
			throw new Error("Error: wrong number of vertex y-coordinates");

		// Update du graphe
		for (int i = 0; i < n; i++) {
			// int x = Integer.parseInt(xi.toString());
			// int y = Integer.parseInt(yi.toString());
			int x = (Integer) xCoord.get(i);
			int y = (Integer) yCoord.get(i);
			graphe.update_vertices(new Point(x, y));
		}

		System.out.println("done");

		System.out.print("Reading edges...");
		JSONArray edge_i = json.getJSONArray("edge_i");
		JSONArray edge_j = json.getJSONArray("edge_j");
		System.out.println("done (size=" + edge_i.size() + ")");

		// Update de ses sommets
		for (int j = 0; j < m; j++) {
			// int sommet_d = Integer.parseInt(depart.toString());
			// int sommet_a = Integer.parseInt(arrivee.toString());
			int sommet_d = (Integer) edge_i.get(j);
			int sommet_a = (Integer) edge_j.get(j);
			graphe.add_arete(sommet_d, sommet_a);
		}

		System.out.println("\tname: " + id);
		System.out.println("\tnumber of vertices: " + n);
		System.out.println("\tnumber of edges: " + m);
		System.out.println("Input instance loaded from file\n------------------");

		return graphe;
	}

	/**
	 * Check the output format (JSON file) of the solution
	 * 
	 * @param filename name of the output file
	 */

	public static boolean checkOutputFile(String filename, GraphSolution instance) {
		System.out.println("Checking output format (JSON file): " + filename);
		JSONObject json;

		json = loadFile(filename);

		String id = json.getString("instance");
		if (id == null || id.equals(instance.id) == false)
			throw new Error("Wrong name for the output");
		System.out.println("\tName of the output instance (" + id + "): ok");

		JSONArray colors_field = json.getJSONArray("colors");
		if (colors_field == null)
			throw new Error("Error: missing 'colors' field");

		System.out.print("\tChecking colors...");
		int[] colors = colors_field.getIntArray();
		if (colors == null || colors.length != instance.nbColors)
			throw new Error("Error: wrong number of colors");

		for (int c : colors)
			if (c < 0 || c >= instance.nbColors)
				throw new Error("Error: wrong color: " + c);

		System.out.println("ok");

		return true;
	}

	/**
	 * Load a JSON object from input file
	 * 
	 * @param filename name of the input file
	 */
	private static JSONObject loadFile(String filename) {
		JSONObject outgoing = null;
		BufferedReader reader = null;
		FileReader fr = null;

		try {
			fr = new FileReader(filename);
			reader = new BufferedReader(fr);
			outgoing = new JSONObject(reader);
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		} finally {
			try {
				if (reader != null)
					reader.close();

				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				System.err.format("IOException: %s%n", ex);
			}
		}

		return outgoing;
	}

	/**
	 * Output the solution to a JSON file
	 */
	public static void saveSolutionToJSON(GraphSolution solution, String output) {
		if (output == null)
			throw new Error("Wrong output file name");
		if (solution == null)
			throw new Error("Error: solution not defined");

		System.out.print("Saving solution to Json file (" + output + ")... ");

		TC.ecritureDansNouveauFichier(output);
		TC.println("{"); // first line
		TC.println("\t\"type\": \"Solution_CGSHOP2022\",");
		TC.println("\t\"instance\": \"" + solution.id + "\","); // write instance name
		TC.println("\t\"num_colors\": " + solution.nbColors + ","); // write number of colors

		TC.print("\t\"colors\": "); // write the colors of the edges
		TC.print("[");

		TC.print(solution.edgeColors[0]); // color of the first edge
		for (int i = 1; i < solution.edgeColors.length; i++)
			TC.print(", " + solution.edgeColors[i]);
		TC.println("]");

		TC.println("}");

		TC.ecritureSortieStandard();
		System.out.println("done");
	}
}
