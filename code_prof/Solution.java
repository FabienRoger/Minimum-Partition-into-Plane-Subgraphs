
/**
 * A class defining a solution to the Minimum Partition problem. <br>
 * 
 * The parallel-motion steps of all robots are stored in an ArrayList<byte[]>, whose 'k-th' element is an array of size 'n'.<br>
 * 
 * @author Luca Castelli Aleardi (INF421, Ecole Polytechnique, dec 2021)
 *
 */
public class Solution {
	/** Name of the input instance */
	public String instance;
	
	/** number of sub-graphs in the partition or, equivalently the number of colors (size of the solution) */
	public int numColors;
	
	/** array storing the colors of edges */
	public int[] colors;
	
	/**
	 * Initialize the solution <br>
	 * trivial partition (only one color): all edges belong to the same sub-graph
	 */
	public Solution(String name, int m) {
		this.instance=name;
		this.numColors=1;
		this.colors=new int[m];
	}
	
	/**
	 * Initialize the solution <br>
	 */
	public Solution(String name, int numColors, int[] colors) {
		this.instance=name;
		this.numColors=numColors;
		this.colors=colors;
	}
	
	/**
	 * Check whether the solution describes a valid partition into plane sub-graphs.
	 * 
	 * @return TRUE if the sub-graphs are PLANE (no edge crossings)
	 */
	public boolean isValid() {
		throw new Error("Checking the solution: TO BE COMPLETED");
	}
	
	public String toString() {
		String result="Solution to the input instance: "+this.instance+"\n";
		result=result+"\tnumber of sub-graphs (number of colors): "+this.numColors+"\n";
		return result;
	}
	
}
