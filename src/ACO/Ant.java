package ACO;

import java.util.ArrayList;
import java.util.List;

import graph.Graph;
import graph.Node;

/**
 * Represents an ant in an Ant Colony Optimization algorithm.
 */
public class Ant {

    /**
     * The ID of the ant.
     */
    public int antId;

    /**
     * The path followed by the ant.
     */
    public List<Node> path;

    /**
     * The destination node for the ant.
     */
    public Node destination;

    /**
     * The Hamiltonian cycle found by the ant.
     */
    public Cycle hamiltonian;

    /**
     * Constructs a new instance of the Ant class with the specified ID.
     *
     * @param antId the ID of the ant
     */
    public Ant(int antId) {
        this.antId = antId;
        this.path = new ArrayList<>();
    }

    /**
     * Sets the destination node for the ant.
     *
     * @param newDestination the new destination node
     */
    public void setDestination(Node newDestination) {
        destination = newDestination;
    }

    /**
     * Retrieves the ID of the ant.
     *
     * @return the ID of the ant
     */
    public int getAntId() {
        return antId;
    }

    /**
     * Retrieves the path followed by the ant.
     *
     * @return the path followed by the ant
     */
    public List<Node> getPath() {
        return path;
    }

    /**
     * Retrieves the current node the ant is on.
     *
     * @return the current node
     */
    public Node getCurrentNode() {
        return path.get(path.size() - 1);
    }

    /**
     * Clears the path followed by the ant, except for the first node.
     *
     * @return the cleared path
     */
    public List<Node> clearPath() {
        return path.subList(0, 1);
    }

    /**
     * Adds a node to the path followed by the ant.
     *
     * @param node the node to add to the path
     */
    public void addToPath(Node node) {
        path.add(node);
    }

    /**
     * Checks if the ant has found a Hamiltonian cycle in the given graph.
     *
     * @param graph the graph to check for a Hamiltonian cycle
     * @return true if a Hamiltonian cycle is found, false otherwise
     */
    public boolean hasHamiltonianCycle(Graph graph) {
        return (path.size() > 2 && path.get(0).getId() == path.get(path.size() - 1).getId()
                && (path.size() - 1) == graph.getNodes().size());
    }

    /**
     * Returns a string representation of the ant.
     *
     * @return a string representation of the ant
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ant ID: ").append(antId).append("\n");
        sb.append("Destination: ").append(destination).append("\n");

        return sb.toString();
    }
}
