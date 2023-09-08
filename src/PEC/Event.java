package PEC;

/**
 * The abstract base class for events in the PEC (Priority Event Queue).
 * Events represent specific points in time within a simulation.
 */
public abstract class Event implements Comparable<Event> {
    private Double time;

    /**
     * Constructs an event with the specified time.
     *
     * @param time the time of the event
     */
    public Event(Double time) {
        this.time = time;
    }

    /**
     * Sets the time of the event.
     *
     * @param time the time of the event
     */
    public void setTime(Double time) {
        this.time = time;
    }

    /**
     * Retrieves the time of the event.
     *
     * @return the time of the event
     */
    public Double getTime() {
        return time;
    }

    /**
     * Compares this event with another event based on their respective times.
     * Events are ordered in ascending order of time.
     *
     * @param other the event to compare with
     * @return a negative integer if this event is scheduled before the other event,
     *         zero if both events are scheduled at the same time,
     *         a positive integer if this event is scheduled after the other event
     */
    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }
}
