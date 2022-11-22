package com.example.android;


import android.util.Log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date,LocalTime time) {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            int eventHour=event.time.getHour();
            int cellHour=time.getHour();
//            Log.i("eventHour",String.valueOf(eventHour));
//            Log.i("cellHour",String.valueOf(cellHour));


            if(event.getDate().equals(date) && eventHour==cellHour) {
                events.add(event);
                Log.i("eventHour", String.valueOf(eventHour));
                Log.i("cellHour", String.valueOf(cellHour));
            }
        }

        return events;
    }


    private String name;
    private LocalDate date;
    private LocalTime time;
    private String desc;
    private String course;

    public Event(String name, String desc,String course,LocalDate date, LocalTime time)
    {
        this.name = name;
        this.desc=desc;
        this.date = date;
        this.time = time;
        this.course=course;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getCourse() {
        return course;
    }
    public void setCourse(String desc) {
        this.course = course;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
}
