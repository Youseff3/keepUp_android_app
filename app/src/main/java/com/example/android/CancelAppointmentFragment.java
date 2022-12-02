package com.example.android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CancelAppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This fragment provides a View to cancel appointments
 */
public class CancelAppointmentFragment extends Fragment {
    ArrayList<String> all_appointments=new ArrayList<String>();
    String appointmentToCancel;
    Event cancelledApp;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor
     */
    public CancelAppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CancelAppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancelAppointmentFragment newInstance(String param1, String param2) {
        CancelAppointmentFragment fragment = new CancelAppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Stores fragment parameters in {@link CancelAppointmentFragment#mParam1} and
     * {@link CancelAppointmentFragment#mParam2}
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Sets up view for fragment and populates appointment spinner with appointments
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView=inflater.inflate(R.layout.fragment_cancel_appointment, container, false);

        Spinner appSpinner =inflatedView.findViewById(R.id.cancelAppSpinner);
        get_an_appointment(appSpinner);
        Button cancelBtn=inflatedView.findViewById(R.id.cancelAppBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event.eventsList.remove(cancelledApp);
                getActivity().onBackPressed();
            }
        });

        return inflatedView;
    }

    /**
     * Populates {@code appSpinner} with appointments and stores the appointment
     * selected through the spinner in {@link CancelAppointmentFragment#appointmentToCancel}
     * @param appSpinner {@link Spinner} spinner to be populated with appointments
     */
    public void get_an_appointment(Spinner appSpinner) {
        // Get all appointments from the database
        for(Event event:Event.eventsList){
            String temp=event.getCourse()+", "+event.getName()+", "+event.getDate()+", "+event.getTime();
            all_appointments.add(temp);
        }

        // Make a handler for the city list.
        ArrayAdapter<CharSequence> adapter =new ArrayAdapter( getContext(),
                android.R.layout.simple_spinner_dropdown_item,all_appointments);
        appSpinner.setAdapter(adapter);
        appSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                        appointmentToCancel=all_appointments.get(i);
                        cancelledApp=Event.eventsList.get(i);
//                        Event.eventsList.remove(cancelledApp);
                    }
                    @Override
                    public void onNothingSelected(AdapterView <?> adapterView) {
                    }
                });
    }
}