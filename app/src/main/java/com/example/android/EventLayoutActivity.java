package com.example.android;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class EventLayoutActivity extends AppCompatActivity {
    protected String Activity_Name="EventLayoutActivity";
    private EditText eventNameET;
    private EditText eventDescET;
    private TextView eventDateTV, eventTimeTV, eventCourseTV;
    private LocalTime time;
    private String tempTime;
    String eventCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_layout);
        Bundle extras=getIntent().getExtras();

        tempTime=extras.getString("time");
        Log.i(Activity_Name,tempTime);
        time=LocalTime.parse(tempTime,DateTimeFormatter.ISO_TIME);
        eventCourse=extras.getString("course");

        eventNameET = findViewById(R.id.eventNameET);
        eventDescET=findViewById(R.id.eventDescET);
        eventCourseTV=findViewById(R.id.eventCourseTV);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
        Log.i(Activity_Name,String.valueOf(time));
        eventCourseTV.setText("Course: "+eventCourse);
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        String eventDesc=eventDescET.getText().toString();
        Event newEvent = new Event(eventName, eventDesc,eventCourse,CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        Log.i("evenlayoutactivity list size",String.valueOf(Event.eventsList.size()));
        finish();
    }


}