package com.example.android;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGroupFragment extends Fragment {

    protected static final String FRAGMENT_NAME="CreateGroupFragment";
    //String first_name;
    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected static final int MAXIMUM = 4;
    protected Spinner StudentSpinner;
    protected Spinner CourseSpinner;
    protected Button AddButton;
    protected LinearProgressIndicator ProgressIndicator;
    protected EditText GroupNameInput;
    protected EditText GroupDescInput;

    protected static RecyclerViewAdapter_AddStudent addstudentadapter;
    protected ArrayAdapter<CharSequence> adapter;
    protected  ArrayAdapter<CharSequence> adapter2;
    protected RecyclerView StudentList;
    public static  ArrayList<String> user = new ArrayList<String >();
    public ArrayList<String> courses = new ArrayList<>();
    public ArrayList<String> students = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "userID";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String UserId;
    private String mParam2;

    public CreateGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateGroupFragment newInstance(String param1, String param2) {
        CreateGroupFragment fragment = new CreateGroupFragment();
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
            UserId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflated_view=inflater.inflate(R.layout.fragment_create_group, container, false);




        StudentSpinner = inflated_view.findViewById(R.id.StudentList);
        CourseSpinner = inflated_view.findViewById(R.id.CourseSpinner);
        AddButton = inflated_view.findViewById(R.id.AddButton);
        StudentList = inflated_view.findViewById(R.id.UsersAdd);
        addstudentadapter = new RecyclerViewAdapter_AddStudent(this.getContext(),user );
        ProgressIndicator = inflated_view.findViewById(R.id.ProgressIndicator);
        GroupNameInput = inflated_view.findViewById(R.id.TextGroupName);
        GroupDescInput = inflated_view.findViewById(R.id.GroupDescInput);




        adapter  = new ArrayAdapter(getContext(),  android.R.layout.simple_spinner_item, students);
        adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        StudentSpinner.setAdapter(adapter);

        ProgressIndicator.setIndeterminate(false);

        StudentList.setAdapter(addstudentadapter);
        StudentList.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));

        PopulateFormDb(UserId);
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUser(view);
            }
        });

        CourseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                students.clear();
                students.add("None");
                //Have to reset existing chipview data once user selects a diff item
                boolean changed = false;
                for (int j = 1; j<user.size(); i++)
                {
                    user.remove(j);
                    changed = true;

                }
                if (changed) {
                    addstudentadapter.notifyDataSetChanged();
                }

                db.collection("user")
                        .whereArrayContains("courses", courses.get(i))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String first_name = (String) document.get("first_name");
                                        if (first_name.compareTo(user.get(0)) != 0 ) {
                                            students.add(first_name);
                                        }
                                        adapter.notifyDataSetChanged();


                                    }
                                } else {
                                    Log.d(FRAGMENT_NAME, "Error getting documents: ", task.getException());
                                }
                            }
                        });




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button saveFormbtn=inflated_view.findViewById(R.id.saveFormBtn);
        saveFormbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveForm(view);

            }
        });

        // Inflate the layout for this fragment
        return inflated_view;
    }

    public void AddUser(View view ){
        if ( addstudentadapter.getItemCount() < MAXIMUM) {
            int spinner_position = StudentSpinner.getSelectedItemPosition();

            if (spinner_position!=0) {

                String selected_item = StudentSpinner.getItemAtPosition(spinner_position).toString();
                Log.i(FRAGMENT_NAME, selected_item);
                user.add(selected_item);
                addstudentadapter.notifyDataSetChanged();


            }

        }
        else{
            Toast.makeText(this.getContext(), "You can only select a Max of 3 members", Toast.LENGTH_LONG).show();
        }


    }

    public void SaveForm(View view){

        if (!ValidateForm()) {
            String message = "Err: Form is invalid, please make sure all fields are completed";
            PrintSnackbar(message, Color.RED, Snackbar.LENGTH_LONG);
        }
        else {
            WriteToDatabase();
        }
    }
    private void PrintSnackbar(String message, int color, int duration)
    {
        Snackbar snackbar = Snackbar.make(this.getActivity().findViewById(R.id.linearLayout), message, duration)
                .setAction("Action", null);
        View snackview = snackbar.getView();
        snackview.setBackgroundColor(color);
        snackbar.show();
    }
    private boolean ValidateForm(){
        if (CourseSpinner.getSelectedItemPosition() != 0 )
            if (!(GroupNameInput.getText().toString().compareTo("")==0) )
                if (!(GroupDescInput.getText().toString().compareTo("") == 0))
                    return user.size() > 0;

        return false;
    }
    public static void DeleteEntry(View view)
    {
        Chip elementDelete = (Chip) (view);
        String element  = elementDelete.getText().toString();
        boolean removed = false;
        int i = 0 ;
        Log.i(FRAGMENT_NAME, element);

        while(!removed && i< user.size()) {
            if (user.get(i).compareTo(elementDelete.getText().toString()) == 0 && i!=0) {
                user.remove(i);
                removed = true;
                addstudentadapter.notifyItemRemoved(i);
                addstudentadapter.notifyItemRangeChanged(i, user.size() - i);

            }
            i++;
        }
    }
    public void WriteToDatabase()
    {
        Log.i(FRAGMENT_NAME, "Writing to database ");


        Map<String, Object> group = new HashMap<>();
        group.put("course", CourseSpinner.getSelectedItemPosition());
        group.put("description", GroupDescInput.getText().toString());
        group.put("name", GroupNameInput.getText().toString());
        group.put("members", user);
        ProgressIndicator.setProgressCompat(50, true);


        db.collection("groups")
                .add(group)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(FRAGMENT_NAME, "DocumentSnapshot added with ID: " + documentReference.getId());
                        ProgressIndicator.setProgressCompat(100, true);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FRAGMENT_NAME, "Error adding document", e);
                    }
                });
        Log.i(FRAGMENT_NAME, "Writing to database completed ");
        getFragmentManager().popBackStack();

    }


    /***
     * Gets the userId that is passed on and finds the name of the user to add to the list of members
     * Populates the Course Spinner with the data the user enters
     *
     * @param UserId
     */
    public void PopulateFormDb(String UserId) {
        Log.i(FRAGMENT_NAME, " " +  UserId);
        DocumentReference docRef = db.collection("user").document(UserId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user.add ( (String) document.get("first_name") ) ;
                        addstudentadapter.notifyItemInserted(user.size());
                        Log.i(FRAGMENT_NAME, "Courses : " + document.get("courses"));

                        courses.add("None");
                        ArrayList<String> dbCourses = (ArrayList<String> ) document.get("courses");

                        for (int i =0; i< dbCourses.size(); i++) {
                            courses.add(dbCourses.get(i));
                        }

                        adapter2 = new ArrayAdapter(getContext(),  android.R.layout.simple_spinner_item, courses );
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        CourseSpinner.setAdapter(adapter2);




                    } else {
                        Log.d(FRAGMENT_NAME, "No such document");
                    }
                } else {
                    Log.d(FRAGMENT_NAME, "get failed with ", task.getException());
                }
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        user.clear();
        students.clear();
    }
}