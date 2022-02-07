
/**
 * A class defining an instance of the problem containing: <br>
 * -) the coordinates of vertices <br>
 * -) the list of ordered edges of the input graph <br>
 * 
 * @author Luca Castelli Aleardi (INF421, Ecole Polytechnique, dec 2021)
 *
 */
public class Instance {
    /** coordinates of the bounding box containing all vertices*/
    public int xmin=Integer.MAX_VALUE, xmax=Integer.MIN_VALUE, ymin=Integer.MAX_VALUE, ymax=Integer.MIN_VALUE;
    
    /** number of vertices */
    int n;
    /** number of edges */
    int m;
    /** Name of the input instance */
    String id;
    /** array storing the x-coordinates of the vertices */
    int[] x;
    /** array storing the y-coordinates of the vertices */
    int[] y;
    /** arrays storing the edges (the two extremities): <br>
     * -) edges_i[k] stores the index of the first vertex of the k-th edge <br>
     * -) edges_j[k] stores the index of the second vertex of the k-th edge
     **/
    int[] edge_i, edge_j;
        
    public Instance(String name, int n, int m, int[] x, int[] y, int[] edge_i, int[] edge_j) {
    	if(x==null || y==null || x.length!=n || y.length!=n)
    		throw new Error("Error: vertex coordinates are not defined or wrong");
    	if(edge_i==null || edge_i.length!=m)
       		throw new Error("Error: edges are not defined or wrong: m="+m+", edge_i size="+edge_i.length);

    	if(edge_j==null || edge_j.length!=m)
    		throw new Error("Error: edges are not defined or wrong: m="+m+", edge_j size="+edge_j.length);
    	
    	this.id=name;
    	this.n=n;
    	this.m=m;
    	this.x=x;
    	this.y=y;
    	this.edge_i=edge_i;
    	this.edge_j=edge_j;
    	
    	this.computeBoundingBox();
    }
    
    /**
     * Compute and store the bounding box containing all vertices. <br>
     * Results are stored in the 'xmin/xmax/ymin/ymax' fields of this class.
     */
    public void computeBoundingBox() {
    	for(int i=0;i<n;i++) {
    		xmin=Math.min(xmin, x[i]);
    		xmax=Math.max(xmax, x[i]);
    		ymin=Math.min(ymin, y[i]);
    		ymax=Math.max(ymax, y[i]);
    	}
    }
    
    public String toString() {
    	String result="Instance of Minimum Partition problem:\n";
    	result=result+"\tnumber of vertices="+this.n+"\n";
    	result=result+"\tnumber of edges="+this.m+"\n";
    	result=result+"\tbounding box= ["+this.xmin+", "+this.xmax+"]x["+this.ymin+", "+this.ymax+"]\n";
    	
    	return result;
    }
    
    
    public void print() {
    	System.out.println("vertex coordinates: ");
    	this.print(x);
    	this.print(y);
    	System.out.println("edges: ");
    	this.print(edge_i);
    	this.print(edge_j);
    }

    private void print(int[] t) {
    	System.out.print("[");
    	for(int x: t)
    		System.out.print(x+" ");
    	System.out.println("]");
    }
        
}
