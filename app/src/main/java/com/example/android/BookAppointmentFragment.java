package com.example.android;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookAppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookAppointmentFragment extends Fragment {

    private TextView monthDayText;
    private ListView hourListView;
    private TextView dayOfWeekTV;
    private Spinner courseSpinner;
    String coursePreference;
    List<String> courseList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookAppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookAppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookAppointmentFragment newInstance(String param1, String param2) {
        BookAppointmentFragment fragment = new BookAppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView=inflater.inflate(R.layout.fragment_book_appointment, container, false);
        courseSpinner=inflatedView.findViewById(R.id.courseSelectionSpinner);
        get_a_course(courseSpinner);

        monthDayText=inflatedView.findViewById(R.id.monthDayText);
        hourListView=inflatedView.findViewById(R.id.hourListView);
        dayOfWeekTV=inflatedView.findViewById(R.id.dayOfWeekTV);
        CalendarUtils.selectedDate = LocalDate.now();
        setDayView();

        Button nextBtn=inflatedView.findViewById(R.id.nextDayBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
                setDayView();
            }
        });
        Button prevBtn=inflatedView.findViewById(R.id.prevDayBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
                setDayView();
            }
        });

        hourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),EventLayoutActivity.class);
                String time=((HourEvent)hourListView.getItemAtPosition(i)).getTime().toString();
                intent.putExtra("time",time);
                intent.putExtra("course",coursePreference);
                startActivity(intent);
            }
        });

        return inflatedView;
    }

    @Override
    public void onResume(){
        super.onResume();
        setDayView();
    }

    private void setDayView() {
        monthDayText.setText(CalendarUtils.monthDayFromDate(CalendarUtils.selectedDate));
        String dayOfWeek =CalendarUtils.selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        setHourAdapter();
    }
    private void setHourAdapter() {
        HourAdapter hourAdapter = new HourAdapter(getActivity().getApplicationContext(),hourEventList());
        hourListView.setAdapter(hourAdapter);
    }
//    ArrayList<HourEvent> allHourEventslist;
    private ArrayList<HourEvent> hourEventList() {
        ArrayList<HourEvent> allHourEventslist=new ArrayList<>();
        for(int hour=8;hour<17;hour++){
            LocalTime time= LocalTime.of(hour,0);
            ArrayList<Event> events=Event.eventsForDateAndTime(CalendarUtils.selectedDate,time);
            HourEvent hourEvent=new HourEvent(time,events);
            ArrayList<Event> hourEventCheck=hourEvent.getEvents();
            if(hourEventCheck.size()==0) {
                allHourEventslist.add(hourEvent);
            }
        }
        return allHourEventslist;
    }

    public void get_a_course(Spinner courseSpinner) {
//        Log.i(ACTIVITY_NAME,"get a year");
        courseList = Arrays.asList(getResources().getStringArray(R.array.courses_list));
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter( getContext(), android.R.layout.simple_spinner_dropdown_item,courseList);
        courseSpinner.setAdapter(adapter);
        courseSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView <?> adapterView,
                                               View view, int i, long l) {
                        coursePreference=courseList.get(i);
                    }
                    @Override
                    public void onNothingSelected(AdapterView <?> adapterView) {
                    }
                });
    }

}