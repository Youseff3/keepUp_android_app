package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;


public class CourseSelectionActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="CourseSelectionActivity";
    String termPref;
    String yearPref;
    ArrayList<String> levelPref;
    ArrayList<ArrayList<String>> coursePreference;
    ArrayList<String> chosenCourses;
    TextView courseTV;
    private static final int[] idArray={
            R.id.courseBtn1,R.id.courseBtn2,R.id.courseBtn3,R.id.courseBtn4,R.id.courseBtn5,R.id.courseBtn6,R.id.courseBtn7,R.id.courseBtn8, R.id.courseBtn9,R.id.courseBtn10,
            R.id.courseBtn11,R.id.courseBtn12, R.id.courseBtn13,R.id.courseBtn14,R.id.courseBtn15,R.id.courseBtn16,R.id.courseBtn17,R.id.courseBtn18,R.id.courseBtn19,R.id.courseBtn20,
            R.id.courseBtn21,R.id.courseBtn22, R.id.courseBtn23,R.id.courseBtn24,R.id.courseBtn25,R.id.courseBtn26, R.id.courseBtn27,R.id.courseBtn28,R.id.courseBtn29,R.id.courseBtn30,
            R.id.courseBtn31,R.id.courseBtn32,R.id.courseBtn33,R.id.courseBtn34,R.id.courseBtn35,R.id.courseBtn36,R.id.courseBtn37,R.id.courseBtn38, R.id.courseBtn39,R.id.courseBtn40};
    private Switch[] switch_buttons=new Switch[idArray.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection);
        setTitle("Course Selection");
        courseTV=findViewById(R.id.courseTextView);
        chosenCourses=new ArrayList<String>();

        Bundle extras=getIntent().getExtras();
        termPref=extras.getString("term");
        yearPref=extras.getString("year");
        levelPref=(ArrayList<String>) getIntent().getSerializableExtra("level");
        Log.i(ACTIVITY_NAME,termPref);
        Log.i(ACTIVITY_NAME,yearPref);
        Log.i(ACTIVITY_NAME,String.valueOf(levelPref));

        new GetAllCourses(termPref,levelPref).execute();

        for (int x=0;x<idArray.length;x++){
            switch_buttons[x]=(Switch)findViewById(idArray[x]);
            int finalX = x;
            switch_buttons[x].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if(checked){
                        chosenCourses.add(switch_buttons[finalX].getText().toString());
                    }else{
                        if(chosenCourses.contains(switch_buttons[finalX].getText().toString())){
                            chosenCourses.remove(switch_buttons[finalX].getText().toString());
                        }
                    }
                    Log.i(ACTIVITY_NAME,String.valueOf(chosenCourses));
                }
            });
        }

//        Switch course_btn1 = findViewById(R.id.courseBtn1);
//        course_btn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                if(checked){
//                    chosenCourses.add(course_btn1.getText().toString());
//                }else{
//                    if(chosenCourses.contains(course_btn1.getText().toString())){
//                        chosenCourses.remove(course_btn1.getText().toString());
//                    }
//                }
//            }
//        });


    }

    public class GetAllCourses extends AsyncTask<String,Integer,String> {
        String contents;
        protected String term;
        protected ArrayList<String> level;

        GetAllCourses(String term,ArrayList<String> level){
            this.term=term;
            this.level=level;
        }
        private String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
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

        @Override
        protected void onPostExecute(String a) {
            coursePreference=new ArrayList<ArrayList<String>>();
            Document doc = Jsoup.parse(contents);
            if(this.term.equals("1")) {
                Elements term1_courses = doc.select("div#tab-contents > section#term1 > section");
                ArrayList<ArrayList<String>> term1CourseInfo = getCourseInfo(term1_courses);
                for (String lev:this.level){
                    int courseIndex=0;
                    while(courseIndex<term1CourseInfo.size()){
                        if(lev.equals(term1CourseInfo.get(courseIndex).get(0).substring(2,3))
                                && term1CourseInfo.get(courseIndex).get(0).substring(0,2).equals("CP")){
                            coursePreference.add(term1CourseInfo.get(courseIndex));
                        }
                        courseIndex+=1;
                    }
                }
            }else if(this.term.equals("2")) {
                Elements term2_courses = doc.select("div#tab-contents > section#term2 > section");
                ArrayList<ArrayList<String>> term2CourseInfo = getCourseInfo(term2_courses);
                for (String lev:this.level){
                    int courseIndex=0;
                    while(courseIndex<term2CourseInfo.size()){
                        if(lev.equals(term2CourseInfo.get(courseIndex).get(0).substring(2,3))
                                && term2CourseInfo.get(courseIndex).get(0).substring(0,2).equals("CP")){
                            coursePreference.add(term2CourseInfo.get(courseIndex));
                        }
                        courseIndex+=1;
                    }
                }
            }else if(this.term.equals("3")) {
                Elements term3_courses = doc.select("div#tab-contents > section#term3 > section");
                ArrayList<ArrayList<String>> term3CourseInfo = getCourseInfo(term3_courses);
                for (String lev:this.level){
                    int courseIndex=0;
                    while(courseIndex<term3CourseInfo.size()){
                        if(lev.equals(term3CourseInfo.get(courseIndex).get(0).substring(2,3))
                                && term3CourseInfo.get(courseIndex).get(0).substring(0,2).equals("CP")){
                            coursePreference.add(term3CourseInfo.get(courseIndex));
                        }
                        courseIndex+=1;
                    }
                }
            }else if(this.term.equals("4")) {
                Elements term4_courses = doc.select("div#tab-contents > section#term4 > section");
                ArrayList<ArrayList<String>> term4CourseInfo = getCourseInfo(term4_courses);
                for (String lev:this.level){
                    int courseIndex=0;
                    while(courseIndex<term4CourseInfo.size()){
                        if(lev.equals(term4CourseInfo.get(courseIndex).get(0).substring(2,3))
                                && term4CourseInfo.get(courseIndex).get(0).substring(0,2).equals("CP")){
                            coursePreference.add(term4CourseInfo.get(courseIndex));
                        }
                        courseIndex+=1;
                    }
                }
            }

            for (int j=0;j<coursePreference.size();j++){
                switch_buttons[j]=(Switch)findViewById(idArray[j]);
                switch_buttons[j].setVisibility(View.VISIBLE);
                switch_buttons[j].setText(coursePreference.get(j).get(0));
            }
        }
        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("https://bohr.wlu.ca/courses/courseSchedules.php");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream in = conn.getInputStream();
                try{
                    contents = convertStreamToString(in);
                }
                finally{
                    in.close();
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public void goToMain(View view){
        Intent intent=new Intent(CourseSelectionActivity.this,MainActivity.class);
        intent.putExtra("term",termPref);
        intent.putExtra("year",yearPref);
        intent.putExtra("level",levelPref);
        intent.putExtra("courses",chosenCourses);
        startActivity(intent);
    }
}