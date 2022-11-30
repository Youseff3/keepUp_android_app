package com.example.android;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;

/***
 * TODO: Make RecyclerView Generic so we dont have to create an adapter for Everything we use
 */

public class RecyclerViewAdapter_AddStudent extends  RecyclerView.Adapter<RecyclerViewAdapter_AddStudent.MyViewHolder> {
    Context ctx;
    ArrayList<String> addStudent;
    int id ;

    public RecyclerViewAdapter_AddStudent(Context ctx, ArrayList<String> addstudent, int id)
    {
        this.ctx = ctx;
        this.addStudent = addstudent;
        this.id = id;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter_AddStudent.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        //Can take in inflater as custom input
        View view = inflater.inflate(R.layout.users_layout_form, parent, false);

        return new RecyclerViewAdapter_AddStudent.MyViewHolder(view, id );
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


        public MyViewHolder(@NonNull View itemView, int id ) {
            super(itemView);
            chipview = itemView.findViewById(R.id.AddGroup);
            if (id ==1 ) {
                chipview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CreateGroupFragment.DeleteEntry(view);
                    }
                });
            }
            else if (id == 2)
            {
                chipview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Student name: " + chipview.getText());
                        builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    } });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });
            }


        }
    }
}