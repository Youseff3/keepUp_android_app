package com.example.android;

import java.util.ArrayList;

/**
 * Wrapper class for {@link ArrayList} of {@link String} courses
 */
public class Classes {

    ArrayList<String> coursenames;

    /**
     * Assigns {@code coursename} to {@link Classes#coursenames}
     * @param coursename {@link ArrayList} of {@link String} courses
     */
    public Classes(ArrayList<String> coursename)
    {

        this.coursenames = coursename;

    }

    /**
     * Returns {@link Classes#coursenames}
     * @return {@link ArrayList} of {@link String} courses
     */
    public ArrayList<String> getCoursename() {
        return coursenames;
    }

}