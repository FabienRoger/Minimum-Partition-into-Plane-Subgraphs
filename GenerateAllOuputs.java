import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GenerateAllOuputs {

    static String inputPrefix = "";
    static String outputPrefix = "";
    static String startWithPrefix = "";

    private static int generateSolution(String inputFile) {
        HashGraphWithInfo graph = IO.loadInput(inputFile);
        GraphSolution solution = new GraphSolution(graph);
        Optimizer optimizer = new DsaturOptimizer();
        optimizer.solve(solution);

        if (!CheckSolution.check(solution)) {
            System.out.println("No soluion found");
            return -1;
        }

        String filename = outputPrefix + "outputs/output" + graph.id + ".json";
        IO.saveSolutionToJSON(solution, filename); // export the solution in JSON format
        IO.checkOutputFile(filename, solution); // check output format of the solution

        return solution.nbColors;
    }

    public static void main(String[] args) {
        startWithPrefix = args[0];
        outputPrefix = args[1];

        File folder = new File(inputPrefix + "instances");
        File[] listOfFiles = folder.listFiles();

        ArrayList<String> results = new ArrayList<>();
        for (File file : listOfFiles) {
            try {
                if (!file.isFile()) {
                    continue;
                }
                if (!file.getName().startsWith(startWithPrefix)) {
                    continue;
                }
                String inputFile = inputPrefix + "instances/" + file.getName();

                long start = System.currentTimeMillis();
                int colors = generateSolution(inputFile);
                results.add("Solution for " + file.getName()
                        + " generated with " + colors + " colors"
                        + " in " + (System.currentTimeMillis() - start) + "ms");
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        String result = "";
        for (String s : results) {
            System.out.println(s);
            result += s + "\n";
        }

        File summaryFile = new File(outputPrefix + "outputs/summary.txt");
        try {
            summaryFile.createNewFile();
            FileWriter summaryFileWriter = new FileWriter(outputPrefix + "outputs/summary.txt");
            summaryFileWriter.write(result);
            summaryFileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
