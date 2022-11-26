package com.example.android;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassFragment extends Fragment {
    protected static final String FRAGMENT_NAME="GroupFragment";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected static int[] colorIcon = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private  ViewClassAdpater adapter;
    private final ArrayList<String> Studentclass = new ArrayList<String>();
    private ListView Classlist;
    TextView noclassinfo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String UserID  = "userID";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static ClassFragment newInstance(String param1, String param2) {
        ClassFragment fragment = new ClassFragment();
        Bundle args = new Bundle();
        args.putString(UserID, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private class ViewClassAdpater extends ArrayAdapter<String> {
        public ViewClassAdpater(@NonNull Context context, int resource) {
            super(context, resource);
        }

        public int getCount() {
            return Studentclass.size();
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ClassFragment.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.group_list_view, null);
            TextView message = result.findViewById(R.id.GroupNameLabel);
            ImageView deleteButton = result.findViewById(R.id.RemoveGroup);
            ImageView classIcon = result.findViewById(R.id.GroupIconDisplay);
            TextView groupDescription = result.findViewById(R.id.GroupDescriptionLabel);
            CardView cardGroup = result.findViewById(R.id.CardGroup);
            ImageView EditIcon = result.findViewById(R.id.EditIcon);
            EditIcon.setVisibility(View.INVISIBLE);





            deleteButton.setTag(position);
            classIcon.setTag(position);
            cardGroup.setTag(position);

            message.setText(Studentclass.get(position));
            groupDescription.setText("Description of course");
            classIcon.setImageResource(R.drawable.class_icon);
            classIcon.setColorFilter((colorIcon[ (int)Math.floor(Math.random()*(colorIcon.length))]));

            ImageView removeClassIV= result.findViewById(R.id.RemoveGroup);
            removeClassIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RemoveClass(view);
                }
            });



            return result;
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.i(FRAGMENT_NAME, "Bundle Received");
            mParam1 = getArguments().getString(UserID);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View test=inflater.inflate(R.layout.fragment_group, container, false);
        Classlist= test.findViewById(R.id.GroupinformationList);
        TextView nogroupinfo = test.findViewById(R.id.NoGroupinfo);
        TextView addclassBtn= test.findViewById(R.id.BannerText);
        addclassBtn.setText("Add a class");
        adapter = new ViewClassAdpater(this.getContext(), 0);
        Classlist.setAdapter(adapter);
        nogroupinfo.setText(R.string.noClassAdded);
        Classlist.setVisibility(View.VISIBLE);

        CardView btn=test.findViewById(R.id.Banner);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView,CreateGroupFragment.class,getArguments()) //TODO: Change to Add Class Fragment
                        .setReorderingAllowed(true)
                        .addToBackStack("tempBackStack")
                        .commit();
            }
        });

        Log.i(FRAGMENT_NAME, "This is the param "+ mParam1);

        db.collection("user").whereEqualTo(FieldPath.documentId(), "bcb2SnD4BuS7NhVKHjgo7djiheV2")  // ERR; Main activity is not passing the dat
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DisplayRegClasses((ArrayList<String>) document.get("courses"));
                            }
                        } else {
                            Log.w(FRAGMENT_NAME, "Error getting documents.", task.getException());
                        }
                    }
                });


        return test;
    }

    public void DisplayRegClasses(ArrayList<String> studentClasses )
    {

        for (String course : studentClasses)
        {
            Studentclass.add(course);
        }
        Log.i(FRAGMENT_NAME, "Courses: " + Studentclass.size());
        adapter.notifyDataSetChanged();
    }


    public void RemoveClass(View view )
    {
        int positionitemToDelete = (int) view.getTag();
        String coursename = Studentclass.get(positionitemToDelete);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this.getContext());

        builder.setTitle("Are you sure you want to be removed from:" +  coursename);
        builder.setPositiveButton(R.string.Yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Studentclass.remove(positionitemToDelete);
                        adapter.notifyDataSetChanged();
                        if (Studentclass.size() == 0 )
                        {


                            noclassinfo.setVisibility(View.VISIBLE);
                            Classlist.setVisibility(View.INVISIBLE);
                        }
                        deletefromdatabase(coursename, mParam1 );
                    } });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    } });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void deletefromdatabase(String coursename, String userID )
    {
        DocumentReference groupsRef = db.collection("user").document(userID);
        groupsRef.update("courses", FieldValue.arrayRemove(coursename));

    }

}