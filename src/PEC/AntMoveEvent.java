package PEC;

import java.util.List;
import java.util.Random;

import ACO.Ant;
import ACO.Cycle;
import ACO.SimulationData;
import graph.Edge;
import graph.Graph;
import graph.Node;
import main.InputTreater;

/**
 * The AntMoveEvent class represents an event of an ant moving in the Ant Colony Optimization algorithm.
 * It extends the Event class.
 */
public class AntMoveEvent extends Event {
    /**
     * The ant that is moving.
     */
    public Ant ant;
     /**
     * The graph representing the problem.
     */
    public Graph graph;

    /**
     * Constructs an AntMoveEvent object with the given time, ant, and graph.
     *
     * @param time  the time at which the event occurs
     * @param ant   the ant that is moving
     * @param graph the graph representing the problem
     */
    public AntMoveEvent(Double time, Ant ant, Graph graph) {
        super(time);
        this.ant = ant;
        this.graph = graph;
    }

    /**
     * Calculates the travel time for an ant to move from the current node to its destination.
     *
     * @param current the current node
     * @param delta   the delta parameter for travel time calculation
     * @return the travel time
     */
    public double getTravelTime(Node current, Double delta) {
        double edgeWeight = graph.getEdgeWeight(current, ant.destination);
        double meanTraversalTime = delta * edgeWeight;
        double uniform = new Random().nextDouble();
        double lambda = 1 / meanTraversalTime;
        double exponential = -Math.log(1 - uniform) / lambda;
        return exponential;
    }

    /**
     * Builds the next AntMoveEvent based on the current time, input parameters, and the PEC event queue.
     * Updates the ant's destination and returns the scheduled time for the next event.
     *
     * @param currentTime  the current time
     * @param parameters   the input parameters for the algorithm
     * @param eventQueue   the PEC event queue
     * @return the scheduled time for the next event
     */
    public double buildEvent(double currentTime, InputTreater parameters, PEC eventQueue) {
        Node currentNode = ant.getCurrentNode();
        List<Node> nonVisitedNodes = graph.getNonVisitedAdjacentNodes(currentNode, ant.getPath());
        double delta = parameters.getDelta();
        double scheduledTime = 0.0;
        Node nextNode = new Node(0);

        if (ant.hamiltonian != null) {
            if (!ant.hamiltonian.path.isEmpty()) {
                nextNode = ant.hamiltonian.path.get(0);

                if (ant.hamiltonian.path.get(0).getId() != graph.getNest().getId()) {
                    Edge edge = graph.getEdge(ant.getCurrentNode(), nextNode);

                    if (edge.getPheromoneLevel() == 0) {
                        PheromoneEvaporationEvent evaporationEvent = new PheromoneEvaporationEvent(currentTime, edge,
                                parameters);
                        if (evaporationEvent.getTime() < parameters.getTau()) {
                            eventQueue.scheduleEvent(evaporationEvent);
                        }
                    }

                    edge.setPheromoneLevel(parameters, ant.hamiltonian.cost, graph.getTotalWeight());
                }
                ant.setDestination(nextNode);
                scheduledTime = currentTime + getTravelTime(currentNode, delta);
                ant.path.remove(0);
                ant.hamiltonian.path.remove(0);

                return scheduledTime;
            }

        }
        if (!nonVisitedNodes.isEmpty()) {
            double[] probabilities = calculateProbabilities(currentNode, nonVisitedNodes, parameters);
            nextNode = selectNextNode(nonVisitedNodes, probabilities);
        } else {
            List<Node> adjacentNodes = graph.getAdjacentNodes(graph, currentNode);
            nextNode = adjacentNodes.get(new Random().nextInt(adjacentNodes.size()));
        }

        ant.setDestination(nextNode);
        scheduledTime = currentTime + getTravelTime(currentNode, delta);
        return scheduledTime;
    }

    /**
     * Processes the AntMoveEvent by adding the destination node to the ant's path and checking for a Hamiltonian cycle.
     * If a cycle is found, it is added to the simulation data.
     * If the ant's path is longer than 2 nodes and the last node has no non-visited adjacent nodes, the cycle is removed from the path.
     *
     * @param data the simulation data
     */
    public void processEvent(SimulationData data) {
        ant.addToPath(ant.destination);

        if (ant.hasHamiltonianCycle(graph)) {
            ant.hamiltonian = new Cycle(ant.path, graph);
            data.addCycle(ant.hamiltonian);
            ant.hamiltonian.path.remove(0);
        }

        if (ant.getPath().size() > 2) {
            int lastIndex = ant.getPath().size() - 1;
            List<Node> previousPath = ant.getPath().subList(0, lastIndex);
            Node lastNode = previousPath.get((previousPath.size() - 1));
            if (graph.getNonVisitedAdjacentNodes(lastNode, previousPath).isEmpty()) {
                removeCycleFromPath(ant);
            }
        }
    }

    /**
     * Calculates the selection probabilities for the next node based on the current node, non-visited nodes, and input parameters.
     *
     * @param current         the current node
     * @param nonVisitedNodes the list of non-visited nodes
     * @param parameters      the input parameters for the algorithm
     * @return an array of probabilities for selecting each non-visited node
     */
    public double[] calculateProbabilities(Node current, List<Node> nonVisitedNodes, InputTreater parameters) {
        double total = 0.0;
        double[] probabilities = new double[nonVisitedNodes.size()];
        double alpha = parameters.getAlpha();
        double beta = parameters.getBeta();

        for (int i = 0; i < nonVisitedNodes.size(); i++) {
            Node nextNode = nonVisitedNodes.get(i);
            Edge edge = graph.getEdge(current, nextNode);
            double pheromoneLevel = edge.getPheromoneLevel();
            double edgeWeight = graph.getEdgeWeight(current, nextNode);

            double numerator = (pheromoneLevel + alpha) / (beta + edgeWeight);
            double denominator = 0.0;

            for (int j = 0; j < nonVisitedNodes.size(); j++) {
                Node otherNode = nonVisitedNodes.get(j);
                edge = graph.getEdge(current, otherNode);
                double otherPheromoneLevel = edge.getPheromoneLevel();
                double otherEdgeWeight = graph.getEdgeWeight(current, otherNode);

                denominator += (otherPheromoneLevel + alpha) / (beta + otherEdgeWeight);
            }

            probabilities[i] = numerator / denominator;
            total += probabilities[i];
        }

        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] /= total;
        }

        return probabilities;
    }

    /**
     * Selects the next node based on the calculated selection probabilities.
     *
     * @param nonVisitedNodes the list of non-visited nodes
     * @param probabilities   the selection probabilities for each non-visited node
     * @return the selected next node
     */
    public Node selectNextNode(List<Node> nonVisitedNodes, double[] probabilities) {
        double randomValue = new Random().nextDouble();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];

            if (randomValue <= cumulativeProbability) {
                return nonVisitedNodes.get(i);
            }
        }

        return nonVisitedNodes.get(nonVisitedNodes.size() - 1);
    }

    /**
     * Removes the cycle from the ant's path by removing nodes after the last occurrence of the last node in the path.
     *
     * @param ant the ant
     */
    public void removeCycleFromPath(Ant ant) {
        Node lastNode = ant.getPath().get(ant.getPath().size() - 1);
        ant.path.remove(ant.getPath().size() - 1);

        for (int i = (ant.path.size() - 1); i > 0; i--) {
            if (ant.path.get(i) == lastNode) {
                break;
            }
            ant.path.remove(i);
        }
    }
}
