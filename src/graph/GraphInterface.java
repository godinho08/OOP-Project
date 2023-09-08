package graph;

import java.util.List;

/**
 * The GraphInterface provides an interface for a graph.
 */
public interface GraphInterface {
    /**
     * Returns the nest node of the graph.
     *
     * @return the nest node
     */
    Node getNest();

    /**
     * Returns a list of all nodes in the graph.
     *
     * @return a list of nodes
     */
    List<Node> getNodes();

    /**
     * Returns a list of all edges in the graph.
     *
     * @return a list of edges
     */
    List<Edge> getEdges();

    /**
     * Adds a node to the graph.
     *
     * @param node the node to add
     */
    void addNode(Node node);

    /**
     * Adds an edge to the graph.
     *
     * @param edge the edge to add
     */
    void addEdge(Edge edge);

    /**
     * Checks if an edge exists between the source and target nodes.
     *
     * @param source the source node
     * @param target the target node
     * @return true if the edge exists, false otherwise
     */
    boolean hasEdge(Node source, Node target);

    /**
     * Returns a list of adjacent nodes for a given node in the graph.
     *
     * @param graph the graph
     * @param node  the node to get the adjacent nodes for
     * @return a list of adjacent nodes
     */
    List<Node> getAdjacentNodes(Graph graph, Node node);

    /**
     * Returns the node with the specified ID.
     *
     * @param id the ID of the node
     * @return the node with the specified ID, or null if not found
     */
    Node getNodeById(int id);

    /**
     * Returns a list of non-visited adjacent nodes for a given node and path.
     *
     * @param node the node to get the non-visited adjacent nodes for
     * @param path the current path
     * @return a list of non-visited adjacent nodes
     */
    List<Node> getNonVisitedAdjacentNodes(Node node, List<Node> path);

    /**
     * Returns the edge between the source and target nodes.
     *
     * @param source the source node
     * @param target the target node
     * @return the edge between the source and target nodes, or null if not found
     */
    Edge getEdge(Node source, Node target);

    /**
     * Returns the weight of the edge between the source and target nodes.
     *
     * @param source the source node
     * @param target the target node
     * @return the weight of the edge between the source and target nodes
     */
    int getEdgeWeight(Node source, Node target);
}
