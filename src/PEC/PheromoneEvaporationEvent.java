package PEC;

import java.util.Random;

import graph.Edge;
import main.InputTreater;

/**
 * The PheromoneEvaporationEvent class represents an event for pheromone evaporation on an edge.
 * It extends the Event class.
 */
public class PheromoneEvaporationEvent extends Event {
    
    /*
    * The edge where the evaporation will take place
    */
    public Edge edge;

    /**
     * Constructs a PheromoneEvaporationEvent with the specified time, edge, and input parameters.
     *
     * @param time  the time at which the event occurs
     * @param edge  the edge on which the pheromone evaporation event occurs
     * @param input the input parameters
     */
    public PheromoneEvaporationEvent(double time, Edge edge, InputTreater input) {
        super(time);
        this.setTime(time + getEvaporationTime(input));
        this.edge = edge;
    }

    /**
     * Calculates the evaporation time based on the input parameters.
     *
     * @param input the input parameters
     * @return the evaporation time
     */
    public double getEvaporationTime(InputTreater input) {
        double eta = input.getEta();

        double meanTraversalTime = eta;
        double uniform = new Random().nextDouble();
        double lambda = 1 / meanTraversalTime;
        double exponential = -Math.log(1 - uniform) / lambda;
        return exponential;
    }
}
