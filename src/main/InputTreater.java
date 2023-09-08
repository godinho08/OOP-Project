package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class handles the input parameters for the program.
 */
public class InputTreater {
    private int n;
    private int n1;
    private int a;
    private double alpha;
    private double beta;
    private double delta;
    private double eta;
    private double rho;
    private double gamma;
    private int nu;
    private double tau;
    private int[][] weights;

    /**
     * Processes the input arguments provided to the program.
     *
     * @param args the input arguments
     * @return {@code true} if the input is valid and processed successfully, {@code false} otherwise
     */
    protected boolean processInput(String[] args) {
        // Check if there are sufficient arguments
        if (args.length < 2) {
            System.out.println("Insufficient arguments.");
            return false;
        }

        if (args[0].equals("-r")) {
            // Check if there are sufficient arguments for generating the graph
            if (args.length < 12) {
                System.out.println("Insufficient arguments for generating graph.");
                return false;
            }

            // Extract and parse the input parameters
            n = Integer.parseInt(args[1]);
            a = Integer.parseInt(args[2]);
            n1 = Integer.parseInt(args[3]);
            alpha = Double.parseDouble(args[4]);
            beta = Double.parseDouble(args[5]);
            delta = Double.parseDouble(args[6]);
            eta = Double.parseDouble(args[7]);
            rho = Double.parseDouble(args[8]);
            gamma = Double.parseDouble(args[9]);
            nu = (int) Double.parseDouble(args[10]);
            tau = Double.parseDouble(args[11]);

            return true;
        } else if (args[0].equals("-f")) {
            // Check if an input file is specified
            if (args.length < 2) {
                System.out.println("No input file specified.");
                return false;
            }

            String inputFile = args[1];
            return readInputFromFile(inputFile);
        } else {
            System.out.println("Invalid command.");
            return false;
        }
    }

    /**
     * Reads input parameters from a file.
     *
     * @param inputFile the path to the input file
     * @return {@code true} if the file is read successfully, {@code false} otherwise
     */
    protected boolean readInputFromFile(String inputFile) {
        try {
            File file = new File(inputFile);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Read the parameters line and extract the values
            String parametersLine = reader.readLine();
            String[] parameters = parametersLine.split(" ");
            n = Integer.parseInt(parameters[0]);
            n1 = Integer.parseInt(parameters[1]);
            alpha = Double.parseDouble(parameters[2]);
            beta = Double.parseDouble(parameters[3]);
            delta = Double.parseDouble(parameters[4]);
            eta = Double.parseDouble(parameters[5]);
            rho = Double.parseDouble(parameters[6]);
            gamma = Double.parseDouble(parameters[7]);
            nu = (int) Double.parseDouble(parameters[8]);
            tau = Double.parseDouble(parameters[9]);
            a = -1;

            // Read the weights matrix
            weights = new int[n][n];
            for (int i = 0; i < n; i++) {
                String[] weightsLine = reader.readLine().split(" ");
                for (int j = 0; j < n; j++) {
                    weights[i][j] = Integer.parseInt(weightsLine[j]);
                }
            }

            reader.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error occurred while reading the input file.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the number of nodes in the graph.
     *
     * @return the number of nodes
     */
    public int getN() {
        return n;
    }

    /**
     * Retrieves the nest node.
     *
     * @return the nest node
     */
    public int getN1() {
        return n1;
    }

    /**
     * Retrieves the value of parameter A.
     *
     * @return the value of parameter A
     */
    public int getA() {
        return a;
    }

    /**
     * Retrieves the value of parameter Beta.
     *
     * @return the value of parameter Beta
     */
    public double getBeta() {
        return beta;
    }

    /**
     * Retrieves the value of parameter Delta.
     *
     * @return the value of parameter Delta
     */
    public double getDelta() {
        return delta;
    }

    /**
     * Retrieves the value of parameter Alpha.
     *
     * @return the value of parameter Alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Retrieves the value of parameter Eta.
     *
     * @return the value of parameter Eta
     */
    public double getEta() {
        return eta;
    }

    /**
     * Retrieves the value of parameter Rho.
     *
     * @return the value of parameter Rho
     */
    public double getRho() {
        return rho;
    }

    /**
     * Retrieves the value of parameter Gamma.
     *
     * @return the value of parameter Gamma
     */
    public double getGamma() {
        return gamma;
    }

    /**
     * Retrieves the value of parameter Tau.
     *
     * @return the value of parameter Tau
     */
    public double getTau() {
        return tau;
    }

    /**
     * Retrieves the ant colony size.
     *
     * @return the ant colony size
     */
    public int getNu() {
        return nu;
    }

    /**
     * Retrieves the weights matrix for the graph.
     *
     * @return the weights matrix
     */
    public int[][] getWeights() {
        return weights;
    }

    /**
     * Generates a formatted string representation of the input parameters.
     *
     * @return the formatted string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t   ").append(n).append("\t : number of nodes in the graph\n");
        sb.append("\t\t   ").append(n1).append("\t : the nest node\n");
        sb.append("\t\t   ").append(alpha).append("\t : alpha, ant move event\n");
        sb.append("\t\t   ").append(beta).append("\t : beta, ant move event\n");
        sb.append("\t\t   ").append(delta).append("\t : delta, ant move event\n");
        sb.append("\t\t   ").append(eta).append("\t : eta, pheromone evaporation event\n");
        sb.append("\t\t   ").append(rho).append("\t : rho, pheromone evaporation event\n");
        sb.append("\t\t   ").append(gamma).append("\t : pheromone level\n");
        sb.append("\t\t   ").append(nu).append("\t : ant colony size\n");
        sb.append("\t\t   ").append(tau).append(" : final instant\n");
        return sb.toString();
    }
}
