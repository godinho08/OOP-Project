package main;

import ACO.AntColony;
import graph.Graph;

/**
 * The main class that executes the program.
 */
public class Main {
    /**
     * The entry point of the program.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        InputTreater inputTreater = new InputTreater();
        if (!inputTreater.processInput(args))
            return;

        Graph graph = null;
        int n = inputTreater.getN();
        int n1 = inputTreater.getN1();
        int a = inputTreater.getA();
        int[][] weights = inputTreater.getWeights();

        if (a > 0) {
            graph = new Graph().setGraphr(n, n1, a);
        } else if (weights != null) {
            graph = new Graph(n, n1).importWeights(n, n1, weights);
        }

        /**
         * Prints the input parameters and the graph.
         */
        System.out.println("Input parameters:");
        System.out.println(inputTreater);
        System.out.println("Graph:");
        System.out.println(graph);

        int numAnts = inputTreater.getNu();
        AntColony antColony = new AntColony(numAnts, graph, inputTreater);
        antColony.simulate(inputTreater.getTau());
    }
}
