package com.example.android;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

/***
 * TODO: Make RecyclerView Generic so we dont have to create an adapter for Everything we use
 */

public class RecyclerViewAdapter_AddStudent extends  RecyclerView.Adapter<RecyclerViewAdapter_AddStudent.MyViewHolder> {
    Context ctx;
    ArrayList<String> addStudent;

    public RecyclerViewAdapter_AddStudent(Context ctx, ArrayList<String> addstudent )
    {
        this.ctx = ctx;
        this.addStudent = addstudent;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter_AddStudent.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        //Can take in inflater as custom input
        View view = inflater.inflate(R.layout.users_layout_form, parent, false);
        Chip deleteEntryChip=view.findViewById(R.id.AddGroup);
        deleteEntryChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteEntry(view);
            }
        });
        return new RecyclerViewAdapter_AddStudent.MyViewHolder(view);
    }
    public void DeleteEntry(View view)
    {
        Chip elementDelete = (Chip) (view);
        String element  = elementDelete.getText().toString();
        boolean removed = false;
        int i = 0 ;
//        Log.i(FRAGMENT_NAME, element);

        while(!removed && i< CreateGroupFragment.user.size()) {
            if (CreateGroupFragment.user.get(i).compareTo(elementDelete.getText().toString()) == 0) {
                CreateGroupFragment.user.remove(i);
                removed = true;
                this.notifyItemRemoved(i);
                this.notifyItemRangeChanged(i, CreateGroupFragment.user.size() - i);
            }
            i++;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_AddStudent.MyViewHolder holder, int position) {
        holder.chipview.setText(addStudent.get(position));

    }

    @Override
    public int getItemCount() {

        return addStudent.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //Can take in View as custom item
        Chip chipview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chipview = itemView.findViewById(R.id.AddGroup);

        }
    }
}