package com.example.android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Subclass of {@link ArrayAdapter} to be attached to {@link List}s that contains {@link HourEvent}s
 */
public class HourAdapter extends ArrayAdapter<HourEvent> {
    /**
     * Call super's constructor with given parameters
     * @param context
     * @param hourEvents {@link List} to apply the adapter to
     */
    public HourAdapter(@NonNull Context context, List<HourEvent> hourEvents) {
        super(context, 0, hourEvents);
    }

    /**
     * Sets the hour and events of {@code convertView} based on the {@code position} in the list
     * the adapter is attached to. If no {@code convertView} is provided, one will be created from
     * {@link R.layout#hour_cell}
     * @param position  {@link Integer} index into the list the adapter is attached to
     * @param convertView {@link View} to set the contents of
     * @param parent The parent {@link ViewGroup} of the {@code convertView}
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        HourEvent event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell, parent, false);
        }
        setHour(convertView, event.time);
        setEvents(convertView, event.events);

        return convertView;
    }

    /**
     * Sets the time of the {@link TextView} in {@code convertView}
     * @param convertView {@link View} to set contents of
     * @param time {@link LocalTime} time to set the {@link TextView}s contents to
     */
    private void setHour(View convertView, LocalTime time)
    {
        TextView timeTV = convertView.findViewById(R.id.timeTV);
        timeTV.setText(CalendarUtils.formattedShortTime(time));
    }

    /**
     * Sets the events of the {@link TextView}s in {@code convertView}
     * @param convertView {@link View} to set contents of
     * @param events {@link ArrayList} of {@link Event}s to set the {@link TextView}s contents to
     */
    private void setEvents(View convertView, ArrayList<Event> events)
    {
        TextView event1 = convertView.findViewById(R.id.event1);
        TextView event2 = convertView.findViewById(R.id.event2);
        TextView event3 = convertView.findViewById(R.id.event3);
        Log.i("events list size",String.valueOf(events.size()));

        if(events.size() == 0) {
            hideEvent(event1);
            hideEvent(event2);
            hideEvent(event3);
        }
        else if(events.size() == 1) {
            setEvent(event1, events.get(0));
            hideEvent(event2);
            hideEvent(event3);
        }
        else if(events.size() == 2) {
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            hideEvent(event3);
        }
        else if(events.size() == 3) {
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            setEvent(event3, events.get(2));
        }
        else {
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            event3.setVisibility(View.VISIBLE);
            String eventsNotShown = String.valueOf(events.size() - 2);
            eventsNotShown += " More Events";
            event3.setText(eventsNotShown);
        }
    }

    /**
     * Sets the event of the {@link TextView}
     * @param textView {@link TextView} to set contents of
     * @param event {@link Event} to set the {@code textView}s contents to
     */
    private void setEvent(TextView textView, Event event) {
        textView.setText(event.getName());
        textView.setVisibility(View.VISIBLE);
    }

    /**
     * Make the given text view invisible
     * @param tv {@link TextView} to be made invisible
     */
    private void hideEvent(TextView tv)
    {
        tv.setVisibility(View.INVISIBLE);
    }

}