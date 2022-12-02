package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

/**
 * This Activity setups up a view to act as the "Loading" screen
 */
public class LoadingScreen extends AppCompatActivity {

    /**
     * Sets up "Loading Screen" view and switches to {@link LoginActivity} view after
     * 3000ms
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingScreen.this, LoginActivity.class));

            }
        }, 3000);


    }
}