package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class AdminMainActivity extends AppCompatActivity {
    Button adminClassBtn;
    Button adminAppointmentBtn;
    Button adminGroupsBtn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Bundle extras=getIntent().getExtras();

//        Bundle extras=getIntent().getExtras();
//        userID = extras.getString("userID");

        FragmentManager fragmentManager =getSupportFragmentManager();

//        adminClassBtn=findViewById(R.id.admin_class_button);
//        adminClassBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragmentManager.beginTransaction()
//                        .replace(R.id.adminFragmentContainerView,AdminClassFragment.class,null)
//                        .setReorderingAllowed(true)
//                        .addToBackStack("tempBackStack")
//                        .commit();
//            }
//        });
        adminGroupsBtn=findViewById(R.id.admin_groups_button);
        adminGroupsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.adminFragmentContainerView,AdminGroupFragment.class,extras)
                        .setReorderingAllowed(true)
                        .addToBackStack("tempBackStack")
                        .commit();
            }
        });
        adminAppointmentBtn=findViewById(R.id.admin_schedule_button);
        adminAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.adminFragmentContainerView,AdminAppointmentFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("tempBackStack")
                        .commit();
            }
        });
    }
}