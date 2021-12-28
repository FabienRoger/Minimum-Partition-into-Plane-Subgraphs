import processing.core.PApplet;

/**
 * Main program that takes as input a JSON file storing the input instance
 * 
 * @author Luca Castelli Aleardi (Ecole Polytechnique, dec 2021)
 *
 *         Modified
 */
public class GraphViewer extends PApplet {
	/** An instance of the input problem */
	// public static Instance instance;

	public static HashGraphWithInfo graph;

	public static int xmin = Integer.MAX_VALUE, xmax = Integer.MIN_VALUE, ymin = Integer.MAX_VALUE,
			ymax = Integer.MIN_VALUE;

	/** Solution of the problem (to be computed) */
	// public Solution solution=null; // to be computed
	/** An algorithm for computing a Minimum Partition into plane sub-graphs */
	// public MyBestAlgorithm algo;

	/** Graph corresponding to the input problem */
	// public static GridLayout graph;

	// color definitions
	private int[] whiteColor = new int[] { 255, 255, 255 };
	private int[] grayColor = new int[] { 150, 150, 150 };
	private int[] darkGrayColor = new int[] { 100, 100, 100 };

	/** User options */
	private static String userOptionsAll = ""
			+ "press 'r' to run your algorithm\n"
			+ "press 'v' to check the validity of the solution\n";

	// parameters of the 2d frame/canvas
	public static int sizeX; // horizontal size of the canvas
	public static int sizeY; // vertical size of the canvas (pixels)
	/** width and height of the graph layout (bounding box) */
	public int layoutWidth, layoutHeight;
	public double scaleFactor = 1.;
	private int shiftX, shiftY; // (pixels) coordinates of the bottom left vertex of the bounding box
	int[] currentMousePosition; // mouse position on the screen (pixels)
	int[] oldMousePosition; // mouse position on the screen (pixels)
	boolean mouseIsDragged = false;

	/** export current solution to Json output file */
	public MyButton exportJson;

	public void settings() {
		this.size(sizeX, sizeY); // set the size of the Java Processing frame
		this.layoutWidth = GraphViewer.xmax - GraphViewer.xmin;
		this.layoutHeight = GraphViewer.ymax - GraphViewer.ymin;
		this.shiftX = 50;
		this.shiftY = 50;
	}

	public void setup() {
		System.out.println("Initializing GraphViewer program");

		// set buttons and colors
		exportJson = new MyButton(this, "export layout to JSON", 650, 4, 145, 20);
		// this.algo=new MyBestAlgorithm(this.instance);
		this.textSize(10);
	}

	/**
	 * Deal with keyboard events
	 */
	public void keyPressed() {

		switch (key) {
			case ('+'):
				this.scaleFactor *= 1.2;
				break;
			case ('-'):
				this.scaleFactor *= 1.2;
				break;
			// case('r'): this.solution=this.algo.run(); break;
			// case('v'): this.solution.isValid(); break;
		}

	}

	/**
	 * Deal with keyboard events
	 */
	public void keyReleased() {
		/*
		 * switch(key) {
		 * }
		 */
	}

	/**
	 * Deal with mouse interaction
	 */
	public void mousePressed() {
		this.currentMousePosition = new int[] { mouseX, mouseY };
		this.oldMousePosition = new int[] { mouseX, mouseY };

		/*
		 * TODO : This if/else is not implemented yet
		 * 
		 * if (this.exportJson.mouseIsOver()) {
		 * System.out.println("Export layout to Json output file");
		 * IO.saveSolutionToJSON(this.solution, "output.json"); // export the solution
		 * in JSON format
		 * IO.checkOutputFile("output.json", this.instance); // check output format of
		 * the solution
		 * }
		 * else {
		 * }
		 */
	}

	/**
	 * Deal with mouse interaction
	 */
	public void mouseReleased() {
		if (mouseButton == RIGHT) { // translate the window
			int deltaX = (mouseX - currentMousePosition[0]) / 2;
			int deltaY = (currentMousePosition[1] - mouseY) / 2;
			this.shiftX = this.shiftX + deltaX;
			this.shiftY = this.shiftY + deltaY;

			this.currentMousePosition = new int[] { mouseX, mouseY };
		} else if (mouseButton == LEFT) {
			/*
			 * System.out.println("Region selection (mouse released): ");
			 * this.currentMousePosition=new int[] {mouseX, mouseY};
			 * int[] p=this.getGridCellFromMouseLocation(this.oldMousePosition[0],
			 * this.oldMousePosition[1]);
			 * int[] q=this.getGridCellFromMouseLocation(mouseX, mouseY);
			 * if(this.oldMousePosition!=null && this.currentMousePosition!=null) {
			 * System.out.println("\t first window corner ["+this.oldMousePosition[0]+", "
			 * +this.oldMousePosition[1]+"]"+" - ["+p[0]+","+p[1]+"]");
			 * System.out.println("\t second window corner ["+this.currentMousePosition[0]
			 * +", "+this.currentMousePosition[1]+"]");
			 * }
			 * System.out.println("\t mouse coordinates ["+mouseX+", "+mouseY+"]");
			 * 
			 * this.oldMousePosition=null;
			 * this.mouseIsDragged=false;
			 */
		}
	}

	/**
	 * Deal with mouse interaction
	 */
	public void mouseDragged() {
		if (mouseButton == LEFT && this.mouseIsDragged == false) {
			System.out.print("Mouse dragged: ");
			this.mouseIsDragged = true;
		}
		if (mouseButton == RIGHT) { // translate the window
			int deltaX = (mouseX - currentMousePosition[0]) / 2;
			int deltaY = (currentMousePosition[1] - mouseY) / 2;
			this.shiftX = this.shiftX + deltaX;
			this.shiftY = this.shiftY + deltaY;

			this.currentMousePosition = new int[] { mouseX, mouseY };
		}
	}

	/**
	 * Given a cell (x, y) on a regular integer grid whose cells have a given
	 * 'cellSize', returns the corresponding pixel on the screen
	 */
	public int[] getPixel(double x, double y) {
		double f = 0.9;
		int vShift = 30;

		int i = (int) ((f * GraphViewer.sizeX) * ((x - GraphViewer.xmin) / this.layoutWidth)); // scale with respect to
																								// the canvas dimension
		int j = (int) ((f * GraphViewer.sizeY) * ((y - GraphViewer.ymin) / this.layoutHeight));
		j = GraphViewer.sizeY - j; // y = H - py;
		i = i + (int) (GraphViewer.sizeX * (1. - f) / 2.);
		j = j - (int) (GraphViewer.sizeY * (1. - f) / 2.) + vShift;

		int[] res = new int[] { i, j };
		return res;
	}

	/**
	 * Main function for drawing the graph
	 */
	public void draw() {
		this.background(255); // set the color of background (clean the background)

		this.drawLayout(); // draw the planar layout of the graph

		// draw buttons and options
		this.drawOptions();
		this.exportJson.draw();
	}

	/**
	 * Show user options on the screen
	 */
	public void drawOptions() {
		String label = GraphViewer.userOptionsAll;
		int posX = 0;
		int posY = 0;
		int textHeight = 40;
		this.textSize(12);

		// this.stroke(edgeColor, edgeOpacity);
		this.fill(grayColor[0], grayColor[1], grayColor[2]);
		this.rect((float) posX, (float) posY, this.width, textHeight); // fill a gray rectangle
		this.fill(0, 0, 0);
		this.text(label, (float) posX + 2, (float) posY + 10); // draw the text
	}

	/**
	 * Draw an integer label in a square
	 */
	public void drawLabel(String label, int x, int y) {
		int delta = 1;
		if (label.length() > 2)
			delta = 0;

		int[] pos = this.getPixel(x - 1, y - 1);
		this.text(label, pos[0] + delta, pos[1] - delta); // draw the text
	}

	/**
	 * Draw a black point
	 */
	public void drawBlackPoint(int x, int y) {
		this.stroke(0);
		this.fill(0);
		int[] pos = this.getPixel(x, y);
		this.ellipse(pos[0], pos[1], 3, 3);
	}

	/**
	 * Draw a black point
	 */
	public void drawWhitePoint(int x, int y) {
		this.stroke(0);
		this.fill(255, 255, 255);
		int[] pos = this.getPixel(x, y);
		this.ellipse(pos[0], pos[1], 4, 4);
	}

	/**
	 * Draw a black segment (x1, y1) - (x2, y2) on the grid
	 */
	public void drawSegment(int x1, int y1, int x2, int y2) {
		this.stroke(50);
		this.fill(200, 200, 200);
		int[] pos1 = this.getPixel(x1, y1);
		int[] pos2 = this.getPixel(x2, y2);
		this.line(pos1[0], pos1[1], pos2[0], pos2[1]);
	}

	/**
	 * Draw a grid layout of the graph
	 */
	public void drawLayout() {
		if (graph == null)
			return;

		// draw the edges
		for (Edge edge : graph.edges) { // iterate over all nodes
			Point pU = edge.a;
			Point pV = edge.b;
			this.drawSegment(pU.x, pU.y, pV.x, pV.y);

		}

		// draw the vertices
		for (Point sommet : graph.vertices) {
			this.drawBlackPoint(sommet.x, sommet.y);
		}
	}

	public static void main(String[] args) {
		System.out.println("Visual tools for the \"CG:SHOP 2022 Competition\"");
		if (args.length < 1) {
			System.out.println("Error: one argument required: input file in JSON format");
			System.exit(0);
		}

		HashGraphWithInfo graph = null;

		/** name of the input file storing the instance of the problem */
		String inputFile;
		/** input graph */

		inputFile = args[0];
		System.out.println("Input file: " + inputFile);

		if (inputFile.endsWith(".json") == true) {
			graph = IO.loadInput(inputFile); // read the input Json file
			System.out.println(graph);
			// instance.print(); // only for small instances (for checking the IO class)
		} else {
			System.out.println("Error: wrong input format");
			System.out.println("Supported input format: JSON format");
			System.exit(0);
		}

		// Initialize the Processing viewer

		for (Point sommet : graph.vertices) {
			GraphViewer.xmin = min(GraphViewer.xmin, sommet.x);
			GraphViewer.xmax = max(GraphViewer.xmax, sommet.x);
			GraphViewer.ymin = min(GraphViewer.ymin, sommet.y);
			GraphViewer.ymax = max(GraphViewer.ymax, sommet.y);
		}

		System.out.println(xmin + " " + xmax + " " + ymin + " " + ymax);

		double ratio = (GraphViewer.ymax - GraphViewer.ymin) / ((double) (GraphViewer.xmax - GraphViewer.xmin));
		if (ratio > 1) {
			GraphViewer.sizeY = 900; // width of the frame
			GraphViewer.sizeX = (int) (GraphViewer.sizeY / ratio); // height of the frame
		} else {
			GraphViewer.sizeX = 900; // width of the frame
			GraphViewer.sizeY = (int) (GraphViewer.sizeX * ratio); // height of the frame
		}

		GraphViewer.graph = graph;

		PApplet.main(new String[] { "GraphViewer" }); // launch the Processing viewer
	}

}
