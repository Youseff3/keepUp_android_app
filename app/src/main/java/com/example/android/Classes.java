package com.example.android;

import java.util.ArrayList;

public class Classes {

    ArrayList<String> coursenames;

    public Classes(ArrayList<String> coursename)
    {

        this.coursenames = coursename;

    }

    public ArrayList<String> getCoursename() {
        return coursenames;
    }

}
