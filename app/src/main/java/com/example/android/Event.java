package com.example.android;


import android.util.Log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Represents an event, which stores the event name, description, date, time and course it belongs to
 */
public class Event {
    public static ArrayList<Event> eventsList = new ArrayList<>();

    /**
     * Returns an {@link ArrayList} of {@link Event} events that match the date time and course given
     * by {@code date}, {@code time} and {@code course}
     * @param date {@link LocalDate} date to filter list by
     * @param time {@link LocalTime} time to filter list by
     * @param course {@link String} course to filter list by
     * @return
     */
    public static ArrayList<Event> eventsForDateAndTime(LocalDate date,LocalTime time,String course) {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            int eventHour=event.time.getHour();
            int cellHour=time.getHour();
//            Log.i("eventHour",String.valueOf(eventHour));
//            Log.i("cellHour",String.valueOf(cellHour));
            if(event.getDate().equals(date) && eventHour==cellHour && event.getCourse().equals(course)) {
                events.add(event);
//                Log.i("eventHour", String.valueOf(eventHour));
//                Log.i("cellHour", String.valueOf(cellHour));
            }
        }

        return events;
    }


    private String name;
    private LocalDate date;
    private LocalTime time;
    private String desc;
    private String course;

    /**
     * Creates an {@link Event} with given parameters
     * @param name {@link String} event name
     * @param desc {@link String} event description
     * @param course {@link String} course the event is for
     * @param date {@link LocalDate} event date
     * @param time {@link LocalTime} event time
     */
    public Event(String name, String desc,String course,LocalDate date, LocalTime time)
    {
        this.name = name;
        this.desc= desc;
        this.date = date;
        this.time = time;
        this.course=course;
    }

    /**
     * Returns the event name
     * @return {@link String} event name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the event name
     * @param name {@link String} event name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the event description
     * @return {@link String} event description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the event description
     * @param desc {@link String} event description
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Returns the course the event is for
     * @return {@link String} course the event is for
     */
    public String getCourse() {
        return course;
    }

    /**
     * Sets the course the event is for
     * @param desc {@link String} course the event is for
     */
    public void setCourse(String desc) {
        this.course = course;
    }

    /**
     * Returns the event date
     * @return {@link LocalDate} event date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the event date
     * @param date {@link LocalDate} event date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the event time
     * @return {@link LocalTime} event time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Sets the event time
     * @param time {@link LocalTime} event time
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }
}
