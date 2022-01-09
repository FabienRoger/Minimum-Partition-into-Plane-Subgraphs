import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GenerateAllOuputsWithResearch {

    static String prefix = "";

    private static int generateSolution(String inputFile, double lw, double cw) {
        HashGraphWithInfo graph = IO.loadInput(inputFile);
        GraphSolution solution = new GraphSolution(graph);
        Optimizer optimizer = new DsaturOptimizerWithHeuristic(lw, cw, true, true);
        optimizer.solve(solution);

        if (!CheckSolution.check(solution)) {
            System.out.println("No soluion found");
            return -1;
        }

        String filename = prefix + "outputs/output" + graph.id + ".json";
        IO.saveSolutionToJSON(solution, filename); // export the solution in JSON format
        IO.checkOutputFile(filename, solution); // check output format of the solution

        return solution.nbColors;
    }

    public static void main(String[] args) {
        File folder = new File(prefix + "instances");
        File[] listOfFiles = folder.listFiles();
        for (double lw = 0; lw < 10; lw += 2) {
            for (double cw = 0; cw < 10; cw += 2) {
                ArrayList<String> results = new ArrayList<>();
                for (File file : listOfFiles) {
                    if (!file.isFile()) {
                        continue;
                    }
                    // if (file.getName().startsWith("reecn")) {
                    // continue;
                    // }
                    String inputFile = prefix + "instances/" + file.getName();

                    long start = System.currentTimeMillis();
                    int colors = generateSolution(inputFile, lw, cw);
                    results.add("Solution for " + file.getName()
                            + " generated with " + colors + " colors"
                            + " in " + (System.currentTimeMillis() - start) + "ms" + lw + " " + cw);
                }
                String result = "";
                for (String s : results) {
                    System.out.println(s);
                    result += s + "\n";
                }
                String outputpath = "outputs/summary" + lw + " " + cw + ".txt";
                File summaryFile = new File(outputpath);
                try {
                    summaryFile.createNewFile();
                    FileWriter summaryFileWriter = new FileWriter(outputpath);
                    summaryFileWriter.write(result);
                    summaryFileWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }

    }
}
