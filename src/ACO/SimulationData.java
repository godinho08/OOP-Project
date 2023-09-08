package ACO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The SimulationData class represents data collected during an ACO simulation.
 */
public class SimulationData {
    private int mevents;
    private int eevents;
    private List<Cycle> cycles;

    /**
     * Constructs a SimulationData object with initial values.
     */
    public SimulationData() {
        mevents = 0;
        eevents = 0;
        cycles = new ArrayList<>();
    }

    /**
     * Increases the count of move events.
     */
    public void increaseMEvents() {
        mevents++;
    }

    /**
     * Increases the count of evaporation events.
     */
    public void increaseEEvents() {
        eevents++;
    }

    /**
     * Adds a cycle to the simulation data.
     * If the cycle is already present, it is not added.
     * Orders the cycles by cost and keeps only the top 6.
     *
     * @param cycle the cycle to add
     */
    public void addCycle(Cycle cycle) {
        if (isCycleAlreadyPresent(cycle)) {
            return;
        }
        cycles.add(new Cycle(cycle));
        orderCyclesByCost();
        if (cycles.size() > 6) {
            cycles.remove(cycles.size() - 1);
        }
    }

    /**
     * Returns the count of move events.
     *
     * @return the count of move events
     */
    public int getMEvents() {
        return mevents;
    }

    /**
     * Returns the count of evaporation events.
     *
     * @return the count of evaporation events
     */
    public int getEEvents() {
        return eevents;
    }

    /**
     * Returns the best cycle in the simulation data.
     * If there are no cycles, returns null.
     *
     * @return the best cycle, or null if no cycles exist
     */
    public Cycle getBest() {
        if (cycles.isEmpty()) {
            return null;
        }

        Cycle bestCycle = cycles.get(0);
        for (int i = 1; i < cycles.size(); i++) {
            Cycle currentCycle = cycles.get(i);
            if (currentCycle.getCost() < bestCycle.getCost()) {
                bestCycle = currentCycle;
            }
        }

        return bestCycle;
    }

    /**
     * Returns a list of the top 5 cycles in the simulation data.
     * If there are 1 or fewer cycles, returns an empty list.
     *
     * @return a list of the top 5 cycles, or an empty list if there are 1 or fewer cycles
     */
    public List<Cycle> getTop5() {
        if (cycles.size() <= 1) {
            return new ArrayList<>();
        }

        List<Cycle> top5Cycles = new ArrayList<>(cycles);
        top5Cycles.remove(getBest());

        return top5Cycles;
    }

    /**
     * Orders the cycles in the simulation data by their cost in ascending order.
     */
    public void orderCyclesByCost() {
        Collections.sort(cycles, Comparator.comparingDouble(Cycle::getCost));
    }

    /**
     * Checks if a cycle is already present in the simulation data.
     *
     * @param newCycle the cycle to check
     * @return true if the cycle is already present, false otherwise
     */
    public boolean isCycleAlreadyPresent(Cycle newCycle) {
        for (Cycle cycle : cycles) {
            if (cycle.equals(newCycle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of cycles in the simulation data.
     *
     * @return a list of cycles
     */
    public List<Cycle> getCycles() {
        return cycles;
    }
}
