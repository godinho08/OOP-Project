package graph;

import main.InputTreater;
/**
 * The Ege class represents an edge between two nodes.
 */
public class Edge {
    private Node source;
    private Node target;
    private int weight;
    private double pheromoneLevel;

    /**
     * Constructs an Edge between two nodes with a specified weight.
     *
     * @param source The source node of the edge.
     * @param target The target node of the edge.
     * @param weight The weight of the edge.
     */
    public Edge(Node source, Node target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        this.pheromoneLevel = 0;
    }

    /**
     * Returns the pheromone level of the edge.
     *
     * @return The pheromone level.
     */
    public double getPheromoneLevel() {
        return pheromoneLevel;
    }

    /**
     * Returns the source node of the edge.
     *
     * @return The source node.
     */
    public Node getSource() {
        return source;
    }

    /**
     * Returns the weight of the edge.
     *
     * @return The weight.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the pheromone level of the edge based on the input parameters.
     *
     * @param inputTreater The input treater containing the gamma value.
     * @param cycleWeight  The weight of the cycle.
     * @param graphWeight  The weight of the graph.
     */
    public void setPheromoneLevel(InputTreater inputTreater, int cycleWeight, int graphWeight) {
        double gamma = inputTreater.getGamma();
        double pheromoneLevel = (gamma * graphWeight) / cycleWeight;
        this.pheromoneLevel += pheromoneLevel;
    }

    /**
     * Evaporates the pheromone level of the edge based on the input parameter.
     *
     * @param inputTreater The input treater containing the rho value.
     */
    public void evaporatePheromones(InputTreater inputTreater) {
        double rho = inputTreater.getRho();
        this.pheromoneLevel -= rho;
        if (this.pheromoneLevel < 0) {
            this.pheromoneLevel = 0;
        }
    }

    /**
     * Returns the target node of the edge.
     *
     * @return The target node.
     */
    public Node getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Edge: " + source.getId() + " -> " + target.getId() + ", Weight: " + weight;
    }
}
