package ACO;

import java.util.List;

import graph.Graph;
import graph.Node;

import java.util.ArrayList;

/**
 * The Cycle class represents a cycle in the Ant Colony Optimization algorithm.
 */
public class Cycle {
    /**
     * The list of nodes representing the cycle path.
     */
    public List<Node> path;

    /**
     * The cost of the cycle.
     */
    public int cost;


    /**
     * Constructs a Cycle object with the given path and graph.
     *
     * @param path  the list of nodes representing the cycle path
     * @param graph the graph representing the problem
     */
    public Cycle(List<Node> path, Graph graph) {
        this.path = new ArrayList<>(path);
        this.cost = calculateWeight(path, graph);
    }

    /**
     * Constructs a Cycle object by copying the path and cost from another Cycle object.
     *
     * @param other the other Cycle object to copy from
     */
    public Cycle(Cycle other) {
        this.path = new ArrayList<>(other.path);
        this.cost = other.cost;
    }

    /**
     * Calculates the weight (total edge weight) of the cycle based on the given path and graph.
     *
     * @param path  the list of nodes representing the cycle path
     * @param graph the graph representing the problem
     * @return the weight of the cycle
     */
    public int calculateWeight(List<Node> path, Graph graph) {
        int weight = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            weight += graph.getEdgeWeight(path.get(i), path.get(i + 1));
        }
        return weight;
    }

    /**
     * Returns the path of the cycle.
     *
     * @return the path of the cycle
     */
    public List<Node> getPath() {
        return path;
    }

    /**
     * Returns the cost of the cycle.
     *
     * @return the cost of the cycle
     */
    public int getCost() {
        return cost;
    }

    /**
     * Returns the total weight of the cycle.
     *
     * @return the total weight of the cycle
     */
    public int getTotalWeight() {
        return cost;
    }

    /**
     * Returns a string representation of the cycle in the format "{node1,node2,...,nodeN}:cost".
     *
     * @return a string representation of the cycle
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < path.size() - 1; i++) {
            sb.append(path.get(i).getId());
            if (i < path.size() - 2) {
                sb.append(",");
            }
        }
        sb.append("}:");
        sb.append(cost);
        return sb.toString();
    }

    /**
     * Checks if this cycle is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the cycles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cycle other = (Cycle) obj;
        return path.equals(other.path) && cost == other.cost;
    }

}
