package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a graph.
 * 
 */
public class Graph implements GraphInterface {
    private List<Node> nodes;
    private List<Edge> edges;
    private int totalWeight;
    private Map<Node, List<Node>> adjacencyMap;
    
    /**
     * The nest of the graph.
     */
    public int nest;

    /**
     * Constructs a graph with the specified nest and additional parameters.
     *
     * @param n   the nest value
     * @param n1  the n1 value
     * @param a   the a value
     */
    public Graph(int n, int n1, int a) {
        nest = n1;
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        adjacencyMap = new HashMap<>();
    }

    /**
     * Constructs a graph with the specified nest.
     *
     * @param n   the nest value
     * @param n1  the n1 value
     */
    public Graph(int n, int n1) {
        nest = n1;
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        adjacencyMap = new HashMap<>();
    }

    /**
     * Constructs an empty graph.
     */
    public Graph() {
    }

    /**
     * Imports the weights for the graph from a matrix.
     *
     * @param n       the nest value
     * @param n1      the n1 value
     * @param weights the weight matrix
     * @return the imported graph
     */
    public Graph importWeights(int n, int n1, int[][] weights) {
        for (int i = 1; i <= n; i++) {
            Node node = new Node(i);
            this.addNode(node);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int weight = weights[i][j];
                if (weight != 0) {
                    Node source = this.getNodeById(i + 1);
                    Node target = this.getNodeById(j + 1);
                    Edge edge = new Edge(source, target, weight);
                    this.addEdge(edge);
                }
            }
        }
        return this;
    }

    /**
     * Sets the graph using random values.
     *
     * @param n   the nest value
     * @param n1  the n1 value
     * @param a   the a value
     * @return the generated graph
     */
    public Graph setGraphr(int n, int n1, int a) {
        Graph graph = new Graph(n, n1, a);
        List<Node> nodes = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            Node node = new Node(i);
            graph.addNode(node);
            nodes.add(node);
        }

        Random random = new Random();
        for (int i = 0; i < n; i++) {
            Node source = nodes.get(i);
            Node target = nodes.get((i + 1) % n);
            int weight = random.nextInt(a) + 1;
            Edge edge = new Edge(source, target, weight);
            Edge edge2 = new Edge(target, source, weight);
            graph.addEdge(edge);
            graph.addEdge(edge2);
        }

        int numExtraEdges = random.nextInt(n * (n - 1) / 2 - n + 1);
        while (numExtraEdges > 0) {
            Node source = nodes.get(random.nextInt(n));
            Node target = nodes.get(random.nextInt(n));
            if (source != target && !graph.hasEdge(source, target)) {
                int weight = random.nextInt(a) + 1;
                Edge edge = new Edge(source, target, weight);
                Edge edge2 = new Edge(target, source, weight);
                graph.addEdge(edge);
                graph.addEdge(edge2);
                numExtraEdges--;
            }
        }

        return graph;
    }

    /**
     * Gets the total weight of the graph.
     *
     * @return the total weight of the graph
     */
    public int getTotalWeight() {
        if (totalWeight == 0) {
            for (Edge edge : edges) {
                totalWeight += edge.getWeight();
            }
            totalWeight = totalWeight / 2;
        }
        return this.totalWeight;
    }

    @Override
    public Node getNest() {
        Node node = new Node(nest);
        return node;
    }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public void addNode(Node node) {
        nodes.add(node);
    }

    @Override
    public void addEdge(Edge edge) {
        edges.add(edge);

        Node source = edge.getSource();
        Node target = edge.getTarget();

        List<Node> sourceAdjacentNodes = adjacencyMap.getOrDefault(source, new ArrayList<>());
        sourceAdjacentNodes.add(target);
        adjacencyMap.put(source, sourceAdjacentNodes);
    }

    @Override
    public boolean hasEdge(Node source, Node target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Node> getAdjacentNodes(Graph graph, Node node) {
        List<Node> adjacentNodes = new ArrayList<>();
        adjacentNodes = graph.adjacencyMap.get(node);
        return adjacentNodes;
    }

    @Override
    public Node getNodeById(int id) {
        for (Node node : nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

    @Override
    public List<Node> getNonVisitedAdjacentNodes(Node node, List<Node> path) {
        List<Node> nonVisitedNodes = new ArrayList<>(adjacencyMap.getOrDefault(node, new ArrayList<>()));
        for (int i = 0; i < path.size(); i++) {
            nonVisitedNodes.remove(path.get(i));
        }
        return nonVisitedNodes;
    }

    @Override
    public Edge getEdge(Node source, Node target) {
        Edge result = null;
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                result = edge;
            }
        }
        return result;
    }

    @Override
    public int getEdgeWeight(Node source, Node target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                return edge.getWeight();
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            sb.append("\t ");
            for (int j = 0; j < nodes.size(); j++) {
                if (i == j) {
                    sb.append("0");
                } else {
                    Node source = nodes.get(i);
                    Node target = nodes.get(j);
                    int weight = getEdgeWeight(source, target);
                    sb.append(weight);
                }
                sb.append(j != nodes.size() - 1 ? " " : " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
