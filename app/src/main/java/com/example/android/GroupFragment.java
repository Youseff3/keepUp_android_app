package com.example.android;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import com.google.firebase.firestore.EventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


/**
 * Todo: Create a temp group and check for duplicates
 */
public class GroupFragment extends Fragment {
    protected static final String FRAGMENT_NAME="GroupFragment";
    protected static int colorIcon[] = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

    private static final int[] memberchip={R.id.Chip1,R.id.Chip2,R.id.Chip3};
    private final Chip[] switch_buttons=new Chip[memberchip.length];

    private ViewGroupsAdapter adapter;
    private ArrayList<GroupsInformation> groups = new ArrayList<GroupsInformation>();
    private ListView GroupLists;

    private View result;
    //DocumentReference docRef;
    private TextView message;
    private ImageView GroupIcon;
    private   TextView GroupDescription;
    private   GroupsInformation info;
    private CardView CardGroup;
    private LayoutInflater inflater;
    private  TextView nogroupinfo;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static class GroupsInformation
    {
        private  String  id;  //Primary Key
        private String NameofGroup;
        private String GroupDescription;

        public  ArrayList<String> members;

        private GroupsInformation(String id , String NameofGroup, String GroupDescription, ArrayList<String> members) {
            this.id = id;
            this.NameofGroup = NameofGroup ;
            this.GroupDescription =GroupDescription;
            this.members = members;

        }
        public String  getid()
        {
            return this.id;
        }

        public ArrayList<String> getMembers() {return this.members;}

        public String getNameofGroup()
        {
            return this.NameofGroup;
        }
        public String getGroupDescription()
        {
            return this.GroupDescription;
        }
    }
    private class ViewGroupsAdapter extends ArrayAdapter<String> {

        public ViewGroupsAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        public int getCount() {
            return groups.size();
        }

        public GroupsInformation fetchitem(int position) {
            return groups.get(position);
        }

        @SuppressLint("ViewHolder")
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = GroupFragment.this.getLayoutInflater();
            result = inflater.inflate(R.layout.group_list_view, null);

            message =  result.findViewById(R.id.GroupNameLabel);
            ImageView deleteButton = result.findViewById(R.id.RemoveGroup);
            ImageView Editbutton = result.findViewById(R.id.EditIcon);
            GroupIcon =  result.findViewById(R.id.GroupIconDisplay);
            GroupDescription = result.findViewById(R.id.GroupDescriptionLabel);
            CardGroup = result.findViewById(R.id.CardGroup);


            info  = fetchitem(position);

            deleteButton.setTag(position);
            GroupIcon.setTag(position);
            CardGroup.setTag(position);
            Editbutton.setTag(position);

            message.setText(info.getNameofGroup());
            GroupDescription.setText(info.getGroupDescription());
            GroupIcon.setColorFilter((colorIcon[ (int)Math.floor(Math.random()*(colorIcon.length))]));

            CardView cardGroupCV=result.findViewById(R.id.CardGroup);
            cardGroupCV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowGroupInfo(view);
                }
            });

            ImageView removeGroupIV=result.findViewById(R.id.RemoveGroup);
            removeGroupIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteGroupEntry(view);
                }
            });

            ImageView editIconIV=result.findViewById(R.id.EditIcon);
            editIconIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditGroups(view);
                }
            });

            return result;
        }

    }
    public void DeleteGroupEntry(View view){

        int positionitemToDelete = (int) view.getTag();
        GroupsInformation groupdel = groups.get(positionitemToDelete);
        String GroupName = groups.get(positionitemToDelete).NameofGroup;


        AlertDialog.Builder builder =
                new AlertDialog.Builder(this.getContext());
        builder.setTitle("Are you sure you want to be removed from: " + GroupName);
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        groups.remove(positionitemToDelete);
                        adapter.notifyDataSetChanged();
                        if (groups.size() == 0 )
                        {
                            nogroupinfo.setVisibility(View.VISIBLE);
                            GroupLists.setVisibility(View.INVISIBLE);
                        }
                        deletefromdatabase(groupdel.id, "saleem" ); //TODO: change mparam2 to username
                    } });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    } });
        AlertDialog dialog = builder.create();
        dialog.show();


    }


    /****
     * Displays Group information
     */
    public void DisplayGroupInfo()
    {


       db.collection("groups").whereArrayContains("members", "saleem") //TODO: change value to username
                .get()

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String groupName = document.getString("name");
                                String groupDesc = document.getString("description");
                                Log.i( " Array information for members : ", "  " +  document.get("members") ) ;

                                groups.add(new GroupsInformation(document.getId(), groupName, groupDesc, (ArrayList<String>) document.get("members")));
                                adapter.notifyDataSetChanged();
                                if (groups.size()>0 )
                                {
                                    GroupLists.setVisibility(View.VISIBLE);
                                    nogroupinfo.setVisibility(View.INVISIBLE);


                                }
                            }
                        } else {
                            Log.w(FRAGMENT_NAME, "Error getting documents.", task.getException());
                        }
                    }
                });




    }


    public void ShowGroupInfo(View view)
    {

        LayoutInflater inflater = this.getLayoutInflater();
        final View views = inflater.inflate(R.layout.groups_custom_dialog_box, null);

        Log.i(FRAGMENT_NAME, "List Item Clicked");
        int positionitem= (int) view.getTag();
        GroupsInformation group = groups.get(positionitem);

        TextView GroupName = views.findViewById(R.id.ViewGroupName);
        TextView GroupDesc = views.findViewById(R.id.ViewGroupDesc);
        TextView Instructor = views.findViewById(R.id.ViewGroupInstructor);
        for (int i =0; i < group.members.size(); i++)
        {
            switch_buttons[i]= views.findViewById(memberchip[i]);
            switch_buttons[i].setVisibility(View.VISIBLE);
            switch_buttons[i].setText(group.members.get(i));

        }


        GroupName.setText(group.NameofGroup);
        GroupDesc.setText(group.GroupDescription);
        Instructor.setText("Instructor: Mawlood-Yunis"); // Todo: Let the firebase query the course instructor data
        AlertDialog.Builder customDialog =  new AlertDialog.Builder(this.getContext());
        customDialog.setView(views)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        Dialog dialog = customDialog.create();
        dialog.show();

    }


    /****
     * TODO: Start Email Intent to email instructor directly, would need instructor email from firebase
     * @param view
     */
    public void EmailInstructor(View view)
    {

    }

    /***
     * TODO: Start EmailGroup Intent to email groups directly, would need user email from firebase
     * @param view
     */

    public void EmailGroup(View view )
    {

    }

    public void EditGroups(View view )
    {

        LayoutInflater inflater = this.getLayoutInflater();
        final View views = inflater.inflate(R.layout.custom_dialog_edit_groupinfo, null);

        Log.i(FRAGMENT_NAME, "List Item Clicked");
        int positionitem= (int) view.getTag();
        GroupsInformation group = groups.get(positionitem);



        AlertDialog.Builder customDialog =  new AlertDialog.Builder(this.getContext());
        customDialog.setView(views)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText NewGroupName = views.findViewById(R.id.GroupNameEdit);
                        EditText NewGroupDesc = views.findViewById(R.id.GroupDescEdit);
                        group.NameofGroup = NewGroupName.getText().toString();
                        group.GroupDescription = NewGroupDesc.getText().toString();
                        adapter.notifyDataSetChanged();


                        updateDatabase(group.getid(), group.NameofGroup, group.GroupDescription);

                    }

                });
        Dialog dialog = customDialog.create();
        dialog.show();


    }




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String UserID  = "userID";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(UserID, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(UserID);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        groups.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View test=inflater.inflate(R.layout.fragment_group, container, false);
//        FragmentManager fragmentManager =getSupportFragmentManager();

        GroupLists = test.findViewById(R.id.GroupinformationList);
        nogroupinfo = test.findViewById(R.id.NoGroupinfo);

        adapter = new ViewGroupsAdapter(this.getContext(), 0 );
        GroupLists.setAdapter(adapter);
        nogroupinfo.setText("No groups available! ");
        nogroupinfo.setVisibility(View.VISIBLE);
        GroupLists.setVisibility(View.INVISIBLE);





        CardView btn=test.findViewById(R.id.Banner);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView,CreateGroupFragment.class,getArguments())
                        .setReorderingAllowed(true)
                        .addToBackStack("tempBackStack")
                        .commit();
            }
        });
        DisplayGroupInfo();
        return test;
    }

    public boolean updateDatabase(String id, String groupname, String groupdescription)
    {
        Log.i(FRAGMENT_NAME, "Message id: " + id);
        DocumentReference groupRef = db.collection("groups").document(id);
        groupRef
                .update("name", groupname)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(FRAGMENT_NAME, "Group name successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FRAGMENT_NAME, "Error updating document id:" + id, e);

                    }
                });

        groupRef
                .update("description", groupdescription)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(FRAGMENT_NAME, "Group Desc successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FRAGMENT_NAME, "Error updating document", e);
                    }
                });
        return true;
    }
    public void deletefromdatabase(String groupid, String username)
    {
        DocumentReference groupsRef = db.collection("groups").document(groupid);
        Log.i(FRAGMENT_NAME, "Group id " + groupid + " username " + username);
         groupsRef.update("members", FieldValue.arrayRemove(username));





        /***
         * TODO before submission: Check if the group has only one member, delete the document if so
         */
    }



}