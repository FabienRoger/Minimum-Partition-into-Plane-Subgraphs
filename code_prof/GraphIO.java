import java.awt.Color;
import jdg.graph.AdjacencyListGraph;
import jdg.graph.GridLayout;
import jdg.graph.GridPoint;
import jdg.graph.Node;

/**
 * This class provides methods for dealing with input/output for JSON files storing graphs
 * 
 * @author Luca Castelli Aleardi (Ecole Polytechnique, nov 2020)
 */
public class GraphIO {
	
	/**
	 * Load an input instance (the input graph) from an input JSON file. <br>
	 * <br>
	 * Remark: the input graph is not provided with a combinatorial embedding
	 * 
	 * @param filename  name of the file storing the input instance
	 */
	public static GridLayout loadGraphFromInstance(Instance instance){
		GridPoint[] points=null;
		AdjacencyListGraph g=new AdjacencyListGraph();
		
		int n=instance.n;
		int e=instance.m;
		
		System.out.print("Reading vertices...");
		points=new GridPoint[n];
		for(int i=0;i<n;i++) {
			int id=i;
			int x=instance.x[i];
			int y=instance.y[i];
			points[i]=new GridPoint(x, y);
			
    		Color color=null; // no color defined for this application
    		g.addNode(new Node(id, color));
			//System.out.println("point "+id+" "+points[i]);
		}
		System.out.println("done");

		System.out.print("Reading edges...");
		for(int i=0;i<e;i++) {
			int index1=instance.edge_i[i];
			int index2=instance.edge_j[i];
			Node v1=g.getNode(index1);
			Node v2=g.getNode(index2);

			if(v1==null || v2==null) {
				throw new Error("Error: wrong vertex indices "+index1+" "+index2);
			}
			if(v1!=v2 && g.adjacent(v1, v2)==false && g.adjacent(v2, v1)==false) { // loops and multiple edges are not allowed
				g.addEdge(v1, v2); // addEdge already adds the two edges (v1, v2) and (v2, v1)
			}
		}
    	System.out.println("done");

		System.out.println("graph created from input instance\n------------------");
		System.out.println(g.sizeVertices()+" vertices, "+g.sizeEdges()+" edges");
		
		GridLayout result=new GridLayout(g, points);
		return result;
	}
	
}
