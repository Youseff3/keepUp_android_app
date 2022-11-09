package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class RegisterActivity2 extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="Register2Activity";
    List<String> termList;
    List<String> yearList;
    List<String> courseList;
    TextView yearTV;
    TextView courseTV;
    Spinner yearSpin;
//    Spinner courseSpin;

    private static final int[] idArray={R.id.courseBtn1,R.id.courseBtn2,R.id.courseBtn3,R.id.courseBtn4};
    private Switch[] switch_buttons=new Switch[idArray.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        yearTV=findViewById(R.id.yearTextView);
        courseTV=findViewById(R.id.courseTextView);
        yearSpin=findViewById(R.id.yearSpinner);
//        courseSpin=findViewById(R.id.courseSpinner);

        get_a_term(yearTV,yearSpin,courseTV);
        get_a_year(courseTV);
//        get_a_course();
    }

    public void get_a_term(TextView yearTV,Spinner yearSpin,TextView courseTV) {
        termList = Arrays.asList(getResources().getStringArray(R.array.terms_list));
        courseList = Arrays.asList(getResources().getStringArray(R.array.courses_list));
        final Spinner termSpinner = findViewById(R.id.termSpinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource( this, R.array.terms_list,
                        android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(adapter);
        termSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView <?> adapterView,
                                               View view, int i, long l) {
                        if(termList.get(i).equals("None")){
                            yearTV.setVisibility(View.INVISIBLE);
                            yearSpin.setVisibility(View.INVISIBLE);
                            courseTV.setVisibility(View.INVISIBLE);
                            for (int j=0;j<courseList.size()-1;j++){
                                switch_buttons[j]=(Switch)findViewById(idArray[j]);
                                switch_buttons[j].setVisibility(View.INVISIBLE);
                            }
//                            courseSpin.setVisibility(View.INVISIBLE);
                        }else {
                            yearTV.setVisibility(View.VISIBLE);
                            yearSpin.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView <?> adapterView) {
                    }
                });
    }
    public void get_a_year(TextView courseTV) {
        yearList = Arrays.asList(getResources().getStringArray(R.array.years_list));
        courseList = Arrays.asList(getResources().getStringArray(R.array.courses_list));

        final Spinner yearSpinner = findViewById(R.id.yearSpinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource( this, R.array.years_list,
                        android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
        yearSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView <?> adapterView,
                                               View view, int i, long l) {
                        if(yearList.get(i).equals("None")){
                            courseTV.setVisibility(View.INVISIBLE);
                            for (int j=0;j<courseList.size()-1;j++){
                                switch_buttons[j]=(Switch)findViewById(idArray[j]);
                                switch_buttons[j].setVisibility(View.INVISIBLE);
                            }
                        }else {
                            courseTV.setVisibility(View.VISIBLE);

                            for (int j=0;j<courseList.size()-1;j++){
                                switch_buttons[j]=(Switch)findViewById(idArray[j]);
                                switch_buttons[j].setVisibility(View.VISIBLE);
                                switch_buttons[j].setText(courseList.get(j+1));
                            }

                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView <?> adapterView) {

                    }
                });
    }
//    public void get_a_course() {
//        // Get the list of cities.
//        courseList = Arrays.asList(getResources().getStringArray(R.array.courses_list));
//        // Make a handler for the city list.
//        final Spinner courseSpinner = findViewById(R.id.courseSpinner);
//        ArrayAdapter<CharSequence> adapter =
//                ArrayAdapter.createFromResource( this, R.array.courses_list,
//                        android.R.layout.simple_spinner_dropdown_item);
//        courseSpinner.setAdapter(adapter);
//        courseSpinner.setOnItemSelectedListener(
//                new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView <?> adapterView,
//                                               View view, int i, long l) {
//
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView <?> adapterView) {
//                    }
//                });
//    }

    public void goToMain(View view){
        Intent intent=new Intent(RegisterActivity2.this,MainActivity.class);
        startActivity(intent);
    }
}