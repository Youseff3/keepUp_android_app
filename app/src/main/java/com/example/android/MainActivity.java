package com.example.android;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.android.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

////////////////////// Example Code for interacting with Firestore //////////////////////////////////

//        listView = (ListView) findViewById(R.id.testListView);

//        FirebaseDB fbdb = new FirebaseDB();

        // OnCompleteListener for specific document
//        OnCompleteListener onCompleteListener = new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = (DocumentSnapshot) task.getResult();
//                    if (document.exists()) {
//                        Map<String, Object> data = document.getData();
//                        Log.d(ACTIVITY_NAME, "DocumentSnapshot data: " + document.getData());
//                        final ArrayList<String> list = new ArrayList <>();
//                        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1, list);
//                        listView.setAdapter(adapter);
//                        for (Object value : data.values()) {
//                            list.add(value.toString());
//                        }
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        Log.d(ACTIVITY_NAME, "No such document");
//                    }
//                } else {
//                    Log.d(ACTIVITY_NAME, "get failed with ", task.getException());
//                }
//            }
//        };

        // OnCompleteListener for document where field is equal to specific value
//        OnCompleteListener onCompleteListener = new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    final ArrayList<String> list = new ArrayList <>();
//                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1, list);
//                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                        Map<String, Object> data = doc.getData();
//                        listView.setAdapter(adapter);
//                        for (Object value : data.values()) {
//                            Log.d("FIREBASE", value.toString());
//                            list.add(value.toString());
//                        }
//                    }
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Log.d(ACTIVITY_NAME, "Error getting documents:  ", task.getException());
//                }
//            }
//        };

//        fbdb.getDocumentFromCollection("groups", "group10", onCompleteListener);
//        fbdb.getDocumentFromCollectionWhereEqualTo("appointment", "course", "CP470", onCompleteListener);

//        Map<String, Object> data = new HashMap<>();
//        data.put("course", "CP470");
//        data.put("day", "2022-10-05");
//        data.put("time", "7:30PM");
//        data.put("title", "Give me a B");
//        data.put("userid", "5");
//        fbdb.insertIntoDocument("appointment", "myAppointment", data);
    }
}