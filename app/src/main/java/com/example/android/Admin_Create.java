package com.example.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Admin_Create extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String ACTIVITY_NAME = "ADMINCREATE" ;
    private static String instituion_address= "@gmail.com"; //TODO: Change to WLU.CA
    ArrayList<String> instructor_names = new ArrayList<String>();
    String instructor_field = "instructor";
    ArrayList<String> courses = new ArrayList<String>();
    ArrayAdapter<CharSequence> adapter;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    protected static HashMap added_instructors =new HashMap<String, Boolean>();


    protected static RecyclerViewAdapter_AddStudent courses_taught_adapter;
    protected RecyclerView CourseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        adapter =new ArrayAdapter( this, android.R.layout.simple_spinner_dropdown_item,instructor_names);
        Spinner Instructors = findViewById(R.id.InstructorSpinner);
        Button CreateAccount = findViewById(R.id.CreateAdminAccount);
        CourseList = findViewById(R.id.CourseInstructorTeaches);
        Instructors.setAdapter(adapter);
        courses_taught_adapter = new RecyclerViewAdapter_AddStudent(this,courses , 3);
        CourseList.setAdapter(courses_taught_adapter);
        CourseList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        PopulateInstructors();

        Instructors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                courses.clear();
                courses_taught_adapter.notifyDataSetChanged();
                String instructor_email = generateEmail(instructor_names.get(i));
                PrintToast(instructor_email);
                generateCoursesTaught(instructor_names.get(i));


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String instructor_info = instructor_names.get(Instructors.getSelectedItemPosition())  ;
              String email = generateEmail(instructor_info);
              String[] temp = instructor_info.split(" ", 3 );
              String first_name = temp[1];
              String last_name = temp[2];

               registerAdmin(email, first_name, last_name, instructor_info );


            }
        });



    }

    public String generateEmail(String instructor_name )
    {
        String test_account = "Mr. Saleem Ibrahim";
        String instructor_email = "saleemibramza@gmail.com";
        if (!instructor_name.equals(test_account)) {
            String[] temp = instructor_name.split(" ", 2);
            String temp2 = temp[1];
            String instructor_full_name = temp2.replace("-", "");
            String[] temp3 = instructor_full_name.split(" ", 2);
             instructor_email = temp3[0].charAt(0) + temp3[1] + instituion_address;
        }


            return instructor_email.toLowerCase();



    }

    public void PopulateInstructors()
    {
        db.collection("courses")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("instructor") != null &&  !document.get("instructor").equals("NA") && !added_instructors.containsKey(document.get("instructor"))
                                && document.get("instructor_id") == null) {
                                    String instructor_name = (String) document.get(instructor_field);
                                    instructor_names.add (instructor_name) ;
                                    added_instructors.put(instructor_name, true);
                                    adapter.notifyDataSetChanged();
                                }


                            }

                            //Toast.makeText(getActivity(), "Page Refreshed ", Toast.LENGTH_LONG).show();
                        } else {
                            Log.w(ACTIVITY_NAME, "Error getting documents or no changes yet.", task.getException());
                        }
                    }

                });
    }

    public void PrintToast(String message )
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void registerAdmin(String email, String first_name, String last_name, String name_in_db)
    {
        int rand = (int)(Math.random()*(90000-10000+1)+10000);
        String temp_password = " "+rand;
        mAuth.createUserWithEmailAndPassword(email, temp_password)
         .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(ACTIVITY_NAME, "New instructor created");
                FirebaseUser user = mAuth.getCurrentUser();
                String userID = user.getUid();
                String role = "Instructor";
                CollectionReference users = db.collection("user");

                Map<String, Object> currUser = new HashMap<>();
                currUser.put("first_name", first_name);
                currUser.put("last_name", last_name);
                currUser.put("email", email);
                currUser.put("role", role );
                users.document(userID).set(currUser);

                SendPasswordResetEmail(email);
                notifyAccountCreated(email, temp_password);
                update_course_table(userID, name_in_db );



            } else {
                // If sign in fails, display a message to the user.
                Log.w(ACTIVITY_NAME, "createUserWithEmail:failure", task.getException());

            }
        }

    });
    }

    public void generateCoursesTaught(String instrucor_name)
    {

        db.collection("courses").whereEqualTo("instructor", instrucor_name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               String course  =  (String) document.get("code") + document.get("section")+ "  "+ document.get("term") + " " + document.get("title");
                               courses.add(course );
                               courses_taught_adapter.notifyItemInserted(courses.size());



                            }

                            //Toast.makeText(getActivity(), "Page Refreshed ", Toast.LENGTH_LONG).show();
                        } else {
                            Log.w(ACTIVITY_NAME, "Error getting documents or no changes yet.", task.getException());
                        }
                    }

                });


    }

    public void SendPasswordResetEmail(String email)
    {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(ACTIVITY_NAME, "Email sent.");
                            PrintToast("Email sent to: " +email);
                        }
                    }
                });
    }

    public void notifyAccountCreated(String email, String password)
    {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("Your account has been created,a password reset email has been sent to your email " + email+ " if you wish to change your credentials. Please note: Do not share your password with anyone");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    } });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void update_course_table(String userID, String instructor_name)
    {
        ArrayList<String> documentpath = new ArrayList<String>();
        db.collection("courses").whereEqualTo("instructor", instructor_name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                documentpath.add(document.getId());
                            }

                            complete_update(documentpath, userID);



                            //Toast.makeText(getActivity(), "Page Refreshed ", Toast.LENGTH_LONG).show();
                        } else {
                            Log.w(ACTIVITY_NAME, "Error getting documents or no changes yet.", task.getException());
                        }
                    }

                });


    }
    public void complete_update(ArrayList<String> docs, String instructor_id) {

        for (int i = 0; i < docs.size(); i++) {
            DocumentReference courses_tobeupdated = db.collection("courses").document(docs.get(i));
            courses_tobeupdated
                    .update("instructor_id",instructor_id )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(ACTIVITY_NAME, "DocumentSnapshot successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(ACTIVITY_NAME, "Error updating document", e);
                        }
                    });
        }

        finish();
    }

}