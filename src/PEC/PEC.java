package PEC;

import java.util.PriorityQueue;

/**
 * The PEC (Priority Event Queue) class represents a priority queue of events.
 * It is used to schedule and retrieve events based on their priority (time).
 */
public class PEC {
    private PriorityQueue<Event> eventQueue;

    /**
     * Constructs an empty PEC (Priority Event Queue).
     */
    public PEC() {
        eventQueue = new PriorityQueue<>();
    }

    /**
     * Schedules an event by adding it to the event queue.
     *
     * @param event the event to be scheduled
     */
    public void scheduleEvent(Event event) {
        eventQueue.add(event);
    }

    /**
     * Retrieves and removes the next event from the event queue.
     *
     * @return the next event in the queue, or null if the queue is empty
     */
    public Event getNextEvent() {
        return eventQueue.poll();
    }

    /**
     * Checks if the event queue is empty.
     *
     * @return true if the event queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return eventQueue.isEmpty();
    }

    /**
     * Gets the size of the event queue.
     *
     * @return the size of the event queue
     */
    public int getSize() {
        return eventQueue.size();
    }
}
