package com.example.android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This fragment provides a View to select courses based on term and course level
 */
public class AddClassFragment extends Fragment {
    String FRAGMENT_NAME="AddClassFragment";
    Spinner levelSpinner;
    Spinner courseSpinner;
    Button addCourseBtn;
    ArrayList<String> levelList;
    String levelPref;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> courses;
    ArrayList<String> courseSelection;
    ArrayList<String> allCourses;
    ArrayList<String> allCourses2;

    String termPref;

    String chosenCourse;


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
    public AddClassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddClassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddClassFragment newInstance(String param1, String param2) {
        AddClassFragment fragment = new AddClassFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Stores fragment parameters in {@link AddClassFragment#mParam1}
     * and {@link AddClassFragment#mParam2}
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
     * Inflates {@link R.layout#fragment_add_class}. Then populates {@link AddClassFragment#courses} and
     * {@link AddClassFragment#termPref} from the database based on the logged in user. {@link AddClassFragment#levelSpinner},
     * {@link AddClassFragment#courseSpinner} and {@link AddClassFragment#addCourseBtn} are then inflated
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView=inflater.inflate(R.layout.fragment_add_class, container, false);

        DocumentReference docRef = db.collection("user").document(MainActivity.UserId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        courses=(ArrayList<String>)document.get("courses");
                        termPref = document.getData().get("termPref").toString();

                        levelSpinner=inflatedView.findViewById(R.id.LevelSelectionSpinner);
                        courseSpinner=inflatedView.findViewById(R.id.addCourseSpinner);
                        addCourseBtn=inflatedView.findViewById(R.id.addClassAndSaveBtn);
                        get_a_level(levelSpinner, courseSpinner);


                        addCourseBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                writeCourseToDatabase(chosenCourse);
                            }
                        });
                    } else {
                        Log.d(FRAGMENT_NAME, "No such document");
                    }
                } else {
                    Log.d(FRAGMENT_NAME, "get failed with ", task.getException());
                }
            }
        });



        return inflatedView;
    }

    /**
     * Populates {@code levelSpinner} with the course levels. Populates
     * {@code courseSpinner} with the courses that are of the selected course level.
     * Calls {@link AddClassFragment#getAllCourses}
     * @param levelSpinner {@link Spinner} to be populated by course levels
     * @param courseSpinner {@link Spinner} to be populated by courses
     */
    public void get_a_level(Spinner levelSpinner,Spinner courseSpinner) {
//        Log.i(ACTIVITY_NAME,"get a year");
        levelList=new ArrayList<String>();
        levelList.add("100");
        levelList.add("200");
        levelList.add("300");
        levelList.add("400");
        ArrayAdapter<CharSequence> adapter =
                new ArrayAdapter( getContext(),
                        android.R.layout.simple_spinner_dropdown_item,levelList);
        levelSpinner.setAdapter(adapter);
        levelSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView <?> adapterView,
                                               View view, int i, long l) {
                        levelPref=levelList.get(i);
                        courseSelection=new ArrayList<String>();

                        getAllCourses(termPref,levelPref,courseSpinner,courses);
                    }
                    @Override
                    public void onNothingSelected(AdapterView <?> adapterView) {
                    }
                });
    }

    /**
     * Populates {@code courseSpinner} with classes based on {@code termPreference}, {@code levelPreference}
     * and excludes courses that are in {@code courses}
     * @param termPreference {@link String} school term to discriminate by for course selection
     * @param levelPreference {@link String} course level to discriminate by for course selection
     * @param courseSpinner {@link Spinner} to be populated by course
     * @param courses {@link ArrayList} of courses already selected
     */
    public void getAllCourses(String termPreference,String levelPreference,Spinner courseSpinner, ArrayList<String> courses)
    {
        allCourses=new ArrayList<String>();
        allCourses2=new ArrayList<String>();

        Log.i(FRAGMENT_NAME, " IN POPCOURSES");
        db.collection("courses").orderBy("code")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentChange document : task.getResult().getDocumentChanges()) {
                                ArrayList<String> crs=new ArrayList<String>();
                                String courseName = document.getDocument().getString("code");
                                String courseSection = document.getDocument().getString("section");
                                String courseTerm=document.getDocument().getString("term");
                                String courseTitle=document.getDocument().getString("title");


                                if (courseName.substring(2, 3).equals(levelPreference.substring(0,1)) && courseTerm.equals(termPreference) && !courses.contains(courseName+" "+courseSection)) {
                                    allCourses.add(courseName+" "+courseSection);
                                    allCourses2.add(courseName+" "+courseSection + ": "+courseTitle);
                                }

                            }

                            ArrayAdapter<CharSequence> adapter2 =
                                    new ArrayAdapter( getContext(),
                                            android.R.layout.simple_spinner_dropdown_item,allCourses2);
                            courseSpinner.setAdapter(adapter2);
                            courseSpinner.setOnItemSelectedListener(
                                    new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView <?> adapterView,
                                                                   View view, int i, long l) {
                                            chosenCourse=allCourses.get(i);
                                            Log.i(FRAGMENT_NAME,chosenCourse);
                                        }
                                        @Override
                                        public void onNothingSelected(AdapterView <?> adapterView) {
                                        }
                                    });
                        } else {
                            Log.w(FRAGMENT_NAME, "Error getting documents or no changes yet.", task.getException());
                        }
                    }
                });
    }

    /**
     * Adds course to user/courses in database
     * @param crs {@link String} course to add to database
     */
    public void writeCourseToDatabase(String crs){
        Log.i(FRAGMENT_NAME, MainActivity.UserId);
        Log.i(FRAGMENT_NAME, crs);

        Log.i(FRAGMENT_NAME, "I am here in write");

        db.collection("user").document(MainActivity.UserId).update("courses", FieldValue.arrayUnion(crs));

        getFragmentManager().popBackStack();
    }

    /**
     * Calls {@link Fragment#onStop()}
     */
    @Override
    public void onStop() {
        super.onStop();

    }
}