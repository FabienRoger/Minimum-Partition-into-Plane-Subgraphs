package jdg.graph;

/**
 * Grid Layout of a graph: vertices are drawn as 2D points with integer coordinates. 
 * Edges are drawn with straight line segments. <br>
 * <br>
 * This class stores: <br>
 * -) the input graph <tt>g</tt> <br>
 * -) the bounding box: <tt>width</tt>, <tt>height</tt><br>
 * -) It stores the 2D coordinates of vertices <br>
 * 
 * @author Luca Castelli Aleardi (Ecole Polytechnique)
 * @version nov 2021
 */
public class GridLayout {
	/** number of vertices of the input graph */
	public int n;
	/** number of edges of the graph */
	public int e;

	/** Input graph */	
	public AdjacencyListGraph g;

	/** 
	 * 2D positions of the vertices of the graph defining the planar layout. <br>
	 * Remark: vertices are indexed from 0..n-1
	 * */
	public GridPoint[] points;

	/**
	 * Initialize the grid layout
	 * 
	 * @param graph  	the input graph (no combinatorial embedding provided)
	 * @param mesh  	the input mesh, provided with a combinatorial embedding (no geometric coordinates)
	 * @param points 	an array storing, for each vertex, its coordinates (x, y)
	 * @param bends  	an array storing, for each edge, the list of edge points (if any)
	 * @param width  	width of the grid
	 * @param height  	height of the grid
	 * @param maxBends	maximal number of bends per edge
	 **/	
	public GridLayout(AdjacencyListGraph g, GridPoint[] points) {
		this.g=g;
		this.n=g.sizeVertices();
		this.e=g.sizeEdges();
		this.points=points;
	}

	/**
	 * Print at the console the 2D coordinates of the vertices of the graph (one per line)
	 **/	
    public void printCoordinates() {
    	if(this.points==null)
    		return;
    	
    	for(GridPoint p: this.points) {
    		if(p!=null)
    			System.out.println(p);
    	}
    }
    
    /**
     * Return a string describing the parameters of the layout
     */
    public String toString() {
    	String result="";
    	result=result+"\t"+this.n+" vertices, "+this.e+" edges\n";
		
		boolean vertexCoordinatesDefined=false;
		for(int i=0;i<this.n;i++) {
			if(this.points==null || this.points[i]==null) {
				if(this.points[i].getX()!=0 && this.points[i].getY()!=0) // at least one vertex location is different from (0, 0)
					vertexCoordinatesDefined=true;
			}
		}
		if(vertexCoordinatesDefined==true)
			result=result+"\tvertex coordinates: not defined\n";
		else
			result=result+"\tvertex coordinates: defined\n";
		
		return result;
    }

}
