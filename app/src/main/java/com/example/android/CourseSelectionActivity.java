package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CourseSelectionActivity extends AppCompatActivity {
    List<String> courseList;
    List<String> coursePreference;
    TextView courseTV;
    private static final int[] idArray={R.id.courseBtn1,R.id.courseBtn2,R.id.courseBtn3,R.id.courseBtn4};
    private Switch[] switch_buttons=new Switch[idArray.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection);
        setTitle("Course Selection");

        courseTV=findViewById(R.id.courseTextView);
    }

    public  ArrayList<ArrayList<String>> getCourseInfo(Elements elements){
        ArrayList<ArrayList<String>> CourseInfo=new ArrayList<ArrayList<String>>();
        int i=0;
        while(i<elements.size()){
            String[] temp=elements.get(i).select("h3").text().split(":");
            if (temp.length>1){
                ArrayList<String> val=new ArrayList<String>();
                String code=temp[0].trim();
                String title=temp[1].trim();
                String desc=elements.get(i).select("p").first().text().trim();
                val.add(code);
                val.add(title);
                val.add(desc);
                CourseInfo.add(val);
            }
            i+=1;
        }
        return CourseInfo;
    }

    public void goToMain(View view){
        Intent intent=new Intent(CourseSelectionActivity.this,MainActivity.class);
        startActivity(intent);
    }
}