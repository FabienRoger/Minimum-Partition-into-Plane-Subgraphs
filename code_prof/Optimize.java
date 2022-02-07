/**
 * Main program for running the algorithm that optimize the Minimum plane partition problem
 * 
 * @author Luca Castelli Aleardi (Ecole Polytechnique, dec 2021)
 */
public class Optimize {

	public static void main(String[] args) {
		System.out.println("Test program for the \"CG:SHOP 2022 Competition\"");
		if(args.length<1) {
			System.out.println("Error: one argument required: input file in JSON format");
			System.exit(0);
		}

		/** name of the input file storing the instance of the problem */
		String inputFile=args[0];
		/** input instance */
		Instance instance=null;
		/** An algorithm for computing a Minimum Partition into plane sub-graphs */
		MyBestAlgorithm algo=null;
		/** Solution of the input problem */
		Solution sol=null;
		
		if(inputFile.endsWith(".json")==true) {
			instance=IO.loadInputInstance(inputFile); // read the input Json file
			System.out.println(instance);
		}
		else {
			System.out.println("Error: wrong input format");
			System.out.println("Supported input format: JSON format");
			System.exit(0);
		}
		
		algo=new MyBestAlgorithm(instance); // define here your best algorithm
		sol=algo.run(); // run the algorithm and compute a solution (TO BE COMPLETED)
		boolean check=sol.isValid(); // check whether the solution is valid (TO BE COMPLETED)
		
		if(check==false)
			throw new Error("Error: the solution is not valid");
		
		IO.saveSolutionToJSON(sol, "output.json"); // export the solution to JSON format
		IO.checkOutputFile("output.json", instance); // check the output format
	}

}
