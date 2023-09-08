package ACO;

import java.util.ArrayList;
import java.util.List;

import PEC.AntMoveEvent;
import PEC.Event;
import PEC.ObservationEvent;
import PEC.PEC;
import PEC.PheromoneEvaporationEvent;
import graph.Graph;
import main.InputTreater;

/**
 * The AntColony class represents an ant colony that performs the Ant Colony Optimization algorithm.
 */
public class AntColony {
    private int numAnts;
    private List<Ant> ants;
    private Graph graph;
    private InputTreater parameters;
    private SimulationData simulationData;

    /**
     * Constructs an AntColony object with the specified number of ants, graph, and input treater.
     *
     * @param numAnts      the number of ants in the colony
     * @param graph        the graph representing the problem
     * @param inputTreater the input treater for algorithm parameters
     */
    public AntColony(int numAnts, Graph graph, InputTreater inputTreater) {
        this.numAnts = numAnts;
        this.ants = new ArrayList<>();
        this.graph = graph;
        this.parameters = inputTreater;
        initialize();
    }

    /**
     * Initializes the ant colony by creating ants and adding them to the nest node.
     */
    public void initialize() {
        for (int i = 0; i < numAnts; i++) {
            Ant ant = new Ant(i);
            ant.addToPath(graph.getNest());
            ant.antId = i + 1;
            ants.add(ant);
        }
    }

    /**
     * Simulates the ant colony algorithm for a given time.
     *
     * @param time the simulation time
     */
    public void simulate(Double time) {
        PEC eventQueue = new PEC();
        double clockTime = 0;
        double scheduledTime = 0;

        for (Ant ant : ants) {
            AntMoveEvent antMoveEvent = new AntMoveEvent(clockTime, ant, graph);

            scheduledTime = antMoveEvent.buildEvent(clockTime, parameters, eventQueue);
            eventQueue.scheduleEvent(new AntMoveEvent(scheduledTime, ant, graph));
        }

        for (int i = 1; i <= 20; i++) {
            double eventTime = i * parameters.getTau() / 20;
            ObservationEvent newObs = new ObservationEvent(eventTime, i);
            eventQueue.scheduleEvent(newObs);
        }

        simulationData = new SimulationData();

        do {
            if (!eventQueue.isEmpty()) {
                Event event = eventQueue.getNextEvent();
                clockTime = event.getTime();

                if (event instanceof AntMoveEvent) {
                    AntMoveEvent antMoveEvent = (AntMoveEvent) event;

                    simulationData.increaseMEvents();

                    antMoveEvent.processEvent(simulationData);

                    if (antMoveEvent.ant.hamiltonian != null) {
                        scheduledTime = antMoveEvent.buildEvent(clockTime, parameters, eventQueue);
                        if (scheduledTime < time) {
                            eventQueue.scheduleEvent(new AntMoveEvent(scheduledTime, antMoveEvent.ant, graph));
                        }
                    } else {
                        scheduledTime = antMoveEvent.buildEvent(clockTime, parameters, eventQueue);
                        if (scheduledTime < time) {
                            eventQueue.scheduleEvent(new AntMoveEvent(scheduledTime, antMoveEvent.ant, graph));
                        }
                    }
                } else if (event instanceof PheromoneEvaporationEvent) {
                    PheromoneEvaporationEvent evaporationEvent = (PheromoneEvaporationEvent) event;

                    simulationData.increaseEEvents();

                    evaporationEvent.edge.evaporatePheromones(parameters);
                    if (evaporationEvent.edge.getPheromoneLevel() > 0) {
                        PheromoneEvaporationEvent event1 = new PheromoneEvaporationEvent(clockTime,
                                evaporationEvent.edge, parameters);
                        if (event1.getTime() < time) {
                            eventQueue.scheduleEvent(event1);
                        }
                    }
                } else if (event instanceof ObservationEvent) {
                    ObservationEvent obsEvent = (ObservationEvent) event;
                    obsEvent.printObservations(simulationData);
                }
            }
        } while (!eventQueue.isEmpty());
    }
}
