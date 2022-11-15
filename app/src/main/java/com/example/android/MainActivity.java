package com.example.android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="MainActivity";
    String yearPref;
    String termPref;
    ArrayList<String> coursePref;
    ArrayList<String> levelPref;
    Button classButton;
    Button groupButton;
    Button appointmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Bundle extras=getIntent().getExtras();
//        termPref=extras.getString("term");
//        yearPref=extras.getString("year");
//        levelPref=(ArrayList<String>) getIntent().getSerializableExtra("level");
//        coursePref=(ArrayList<String>) getIntent().getSerializableExtra("courses");
//        Log.i(ACTIVITY_NAME,termPref);
//        Log.i(ACTIVITY_NAME,yearPref);
//        Log.i(ACTIVITY_NAME,String.valueOf(levelPref));
//        Log.i(ACTIVITY_NAME,String.valueOf(coursePref));

        FragmentManager fragmentManager =getSupportFragmentManager();

        classButton=findViewById(R.id.class_button);
        classButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView,ClassFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("tempBackStack")
                        .commit();
            }
        });
        groupButton=findViewById(R.id.groups_button);
        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView,GroupFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("tempBackStack")
                        .commit();
            }
        });
        appointmentButton=findViewById(R.id.schedule_button);
        appointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView,AppointmentFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("tempBackStack")
                        .commit();
            }
        });
    }


}