package com.example.android;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Represents an hour time slot and stores the time and list of {@link Event} events
 */
class HourEvent{
    LocalTime time;
    ArrayList<Event> events;

    /**
     * Creates an {@link HourEvent} from the given parameters
     * @param time
     * @param events
     */
    public HourEvent(LocalTime time, ArrayList<Event> events) {
        this.time = time;
        this.events = events;
    }

    /**
     * Returns the hour event time
     * @return {@link LocalTime} hour event time
     */
    public LocalTime getTime()
    {
        return time;
    }

    /**
     * Sets the hour event time
     * @param time {@link LocalTime} hour event time
     */
    public void setTime(LocalTime time)
    {
        this.time = time;
    }

    /**
     * Returns the hour events
     * @return {@link ArrayList} of {@link Event} hour events
     */
    public ArrayList<Event> getEvents()
    {
        return events;
    }

    /**
     * Sets the hour events
     * @param events {@link ArrayList} of {@link Event} events
     */
    public void setEvents(ArrayList<Event> events)
    {
        this.events = events;
    }
}


