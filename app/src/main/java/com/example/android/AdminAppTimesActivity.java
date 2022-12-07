package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * This Activity setups up a view to display an appointment times screen
 */
public class AdminAppTimesActivity extends AppCompatActivity {

    /**
     * Sets up the view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_app_times);
    }
}