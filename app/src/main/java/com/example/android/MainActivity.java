package com.example.android;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.help:
                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showHelp()
    {

        LayoutInflater inflater = this.getLayoutInflater();
        final View views = inflater.inflate(R.layout.help_dialog_box, null);

        AlertDialog.Builder customDialog =  new AlertDialog.Builder(this);
        customDialog.setView(views)
                .setPositiveButton(R.string.Close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        Dialog dialog = customDialog.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Bundle extras=getIntent().getExtras();
        appointmentButton=findViewById(R.id.schedule_button);
        classButton=findViewById(R.id.class_button);
        groupButton=findViewById(R.id.groups_button);
        classButton.setBackgroundColor(Color.GREEN);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



//        termPref=extras.getString("term");
//        yearPref=extras.getString("year");
//        levelPref=(ArrayList<String>) getIntent().getSerializableExtra("level");
//        coursePref=(ArrayList<String>) getIntent().getSerializableExtra("courses");
//        Log.i(ACTIVITY_NAME,termPref);
//        Log.i(ACTIVITY_NAME,yearPref);
//        Log.i(ACTIVITY_NAME,String.valueOf(levelPref));
//        Log.i(ACTIVITY_NAME,String.valueOf(coursePref));

        FragmentManager fragmentManager =getSupportFragmentManager();

        classButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classButton.setBackgroundColor(Color.GREEN);
                groupButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
                appointmentButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView,ClassFragment.class,extras)
                        .setReorderingAllowed(true)
                        .addToBackStack("tempBackStack")
                        .commit();
            }
        });
        appointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
                groupButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
                appointmentButton.setBackgroundColor(Color.GREEN);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView,AppointmentFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("tempBackStack")
                        .commit();
            }
        });

        groupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
                groupButton.setBackgroundColor(Color.GREEN);
                appointmentButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView,GroupFragment.class,extras)
                        .setReorderingAllowed(true)
                        .addToBackStack("tempBackStack")
                        .commit();
            }
        });

    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventLayoutActivity.class));
    }


}