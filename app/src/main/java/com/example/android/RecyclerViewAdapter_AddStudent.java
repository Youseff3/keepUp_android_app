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

/**
 * This Activity setups up a view to add an remove students
 */
public class RecyclerViewAdapter_AddStudent extends  RecyclerView.Adapter<RecyclerViewAdapter_AddStudent.MyViewHolder> {
    Context ctx;
    ArrayList<String> information;
    int id ;

    /**
     * Creates new {@link RecyclerViewAdapter_AddStudent} and stores parameters as fields
     * @param ctx
     * @param addstudent {@link ArrayList} of students to add
     * @param id {@link Integer} 1 == delete, 2 == add
     */
    public RecyclerViewAdapter_AddStudent(Context ctx, ArrayList<String> addstudent, int id)
    {
        this.ctx = ctx;
        this.information = addstudent;
        this.id = id;
    }

    /**
     * Creates and returns a new {@link RecyclerView.ViewHolder} with {@link RecyclerViewAdapter_AddStudent#id}
     * as a parameter
     * @param parent
     * @param viewType unused
     * @return new {@link RecyclerView.ViewHolder}
     */
    @NonNull
    @Override
    public RecyclerViewAdapter_AddStudent.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        //Can take in inflater as custom input
        View view = inflater.inflate(R.layout.users_layout_form, parent, false);

        return new RecyclerViewAdapter_AddStudent.MyViewHolder(view, id );
    }


    /**
     * Sets the text of {@code holder.chipview} to that of the student at {@code position} in
     * {@link RecyclerViewAdapter_AddStudent#information}
     * @param holder
     * @param position {@link Integer} index into student list
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_AddStudent.MyViewHolder holder, int position) {
        holder.chipview.setText(information.get(position));

    }

    /**
     * Gets the number of students in the {@link RecyclerViewAdapter_AddStudent#information} list
     * @return {@link Integer} number of students
     */
    @Override
    public int getItemCount() {

        return information.size();
    }

    /**
     * A subclass of {@link RecyclerView.ViewHolder} to represent a view for an individual
     * student
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //Can take in View as custom item
        Chip chipview;


        /**
         * Creates a new {@link MyViewHolder} and adds or removes entries based on {@code id}
         * @param itemView {@link View} to represent students
         * @param id {@link Integer} 1 == remove, 2 == add
         */
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

            else if (id ==3 )
            {


                chipview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Course: " + chipview.getText());
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
