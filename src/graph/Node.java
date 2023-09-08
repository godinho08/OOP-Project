package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Node class represents a node in a graph.
 */
public class Node {
    private int id;
    private List<Node> adjacentNodes;

    /**
     * Constructs a Node with the specified ID.
     *
     * @param id the ID of the node
     */
    public Node(int id) {
        this.id = id;
        this.adjacentNodes = new ArrayList<>();
    }

    /**
     * Returns the ID of the node.
     *
     * @return the ID of the node
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the list of adjacent nodes.
     *
     * @return the list of adjacent nodes
     */
    public List<Node> getAdjacentNodes() {
        return adjacentNodes;
    }

    /**
     * Adds an adjacent node to the current node.
     *
     * @param node the adjacent node to add
     */
    public void addAdjacentNode(Node node) {
        adjacentNodes.add(node);
    }

    /**
     * Checks if the current node is equal to the specified object.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node otherNode = (Node) obj;
        return id == otherNode.id;
    }

    /**
     * Returns the hash code value for the current node.
     *
     * @return the hash code value for the current node
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

  
}
