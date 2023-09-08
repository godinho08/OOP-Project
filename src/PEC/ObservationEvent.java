package PEC;

import java.util.List;

import ACO.Cycle;
import ACO.SimulationData;

/**
 * Represents an observation event in the simulation.
 * It is triggered at a specific time to print observations and simulation data.
 */
public class ObservationEvent extends Event {
    private int seqNumber;

    /**
     * Constructs an observation event with the given time and sequence number.
     *
     * @param time      the time of the observation event
     * @param seqNumber the sequence number of the observation event
     */
    public ObservationEvent(double time, int seqNumber) {
        super(time);
        this.seqNumber = seqNumber;
    }

    /**
     * Prints the observations and simulation data to the console.
     *
     * @param data the simulation data containing the observed information
     */
    public void printObservations(SimulationData data) {
        System.out.println("Observation " + seqNumber + ":");
        System.out.println("\t Present instant: " + this.getTime());
        System.out.println("\t Number of move events: " + data.getMEvents());
        System.out.println("\t Number of evaporation events: " + data.getEEvents());

        System.out.println("\t Top candidate cycles:");
        List<Cycle> topCycles = data.getTop5();
        if (topCycles.isEmpty()) {
            System.out.println("\t\t{}");
        } else {
            for (Cycle cycle : topCycles) {
                System.out.println("\t\t" + cycle);
            }
        }

        System.out.println("\t Best Hamiltonian cycle:");
        Cycle bestCycle = data.getBest();
        if (bestCycle == null) {
            System.out.println("\t\t{}");
        } else {
            System.out.println("\t\t" + bestCycle);
        }
        System.out.println("");
    }
}
