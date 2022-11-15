package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import javax.net.ssl.HttpsURLConnection;

public class RegisterActivity2 extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="Register2Activity";
    List<String> termList;
    List<String> yearList;
    List<String> levelList;
    String termPreference;
    String yearPreference;
    ArrayList<String> levelPreference;
    TextView yearTV;
    TextView levelTV;
    TextView termTV;
    Spinner yearSpin;
    Spinner termSpin;

    private static final int[] idArray2={R.id.levelBtn1,R.id.levelBtn2,R.id.levelBtn3,R.id.levelBtn4};
    private Switch[] level_switch_buttons=new Switch[idArray2.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        setTitle("Registration");

        levelPreference=new ArrayList<String>();
        yearTV=findViewById(R.id.yearTextView);
        levelTV=findViewById(R.id.levelTextView);
        termTV=findViewById(R.id.termTextView);
        yearSpin=findViewById(R.id.yearSpinner);
        termSpin=findViewById(R.id.termSpinner);

        yearTV.setVisibility(View.VISIBLE);
        yearSpin.setVisibility(View.VISIBLE);
        get_a_year(yearSpin);
//        termTV.setVisibility(View.VISIBLE);
//        termSpin.setVisibility(View.VISIBLE);
//        get_a_term(courseTV,levelTV,termTV,termSpin);
        new GetAllContent().execute();

//        Ion.with(getApplicationContext()).load("https://bohr.wlu.ca/courses/courseSchedules.php").asString().setCallback(new FutureCallback<String>() {
//            @Override
//            public void onCompleted(Exception e, String result) {
//                termList=getAllTerms(result);
//                termTV.setVisibility(View.VISIBLE);
//                termSpin.setVisibility(View.VISIBLE);
//                get_a_term(courseTV,levelTV,termSpin);
//            }
//        });

        Switch switch_btn1 = findViewById(R.id.levelBtn1);
        switch_btn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                levelPreference.add("1");
                }else{
                    if(levelPreference.contains("1")){
                        levelPreference.remove(Integer.valueOf("1"));
                    }
                }
            }
        });
        Switch switch_btn2 = findViewById(R.id.levelBtn2);
        switch_btn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    levelPreference.add("2");
                }else{
                    if(levelPreference.contains("2")){
                        levelPreference.remove(Integer.valueOf("2"));
                    }
                }
            }
        });
        Switch switch_btn3 = findViewById(R.id.levelBtn3);
        switch_btn3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    levelPreference.add("3");
                }else{
                    if(levelPreference.contains("3")){
                        levelPreference.remove(Integer.valueOf("3"));
                    }
                }
            }
        });
        Switch switch_btn4 = findViewById(R.id.levelBtn4);
        switch_btn4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    levelPreference.add("4");
                }else{
                    if(levelPreference.contains("4")){
                        levelPreference.remove(Integer.valueOf("4"));
                    }
                }
            }
        });

    }
    public void get_a_year(Spinner yearSpinner) {
//        Log.i(ACTIVITY_NAME,"get a year");
        yearList = Arrays.asList(getResources().getStringArray(R.array.years_list));
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource( this, R.array.years_list,
                        android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
        yearSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView <?> adapterView,
                                               View view, int i, long l) {
                        yearPreference=yearList.get(i);
                    }
                    @Override
                    public void onNothingSelected(AdapterView <?> adapterView) {
                    }
                });
    }

    public void get_a_term(TextView levelTV,TextView termTV,Spinner termSpinner) {
//        termList = Arrays.asList(getResources().getStringArray(R.array.terms_list));
        levelList = Arrays.asList(getResources().getStringArray(R.array.levels_list));
        ArrayAdapter<CharSequence> adapter =
                new ArrayAdapter( this,
                        android.R.layout.simple_spinner_dropdown_item,termList);
        termSpinner.setAdapter(adapter);
        termSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView <?> adapterView,
                                               View view, int i, long l) {
//                        new GetAllContent().execute();
                        termPreference=Integer.toString(i+1);
                        levelTV.setVisibility(View.VISIBLE);
                        for (int j=0;j<levelList.size();j++){
                            level_switch_buttons[j]=(Switch)findViewById(idArray2[j]);
                            level_switch_buttons[j].setVisibility(View.VISIBLE);
                            level_switch_buttons[j].setText(levelList.get(j));
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView <?> adapterView) {
                    }
                });
    }

    public class GetAllContent extends AsyncTask<String,Integer,String> {
        String contents;
        private String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
            Log.i(ACTIVITY_NAME,"check2");

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
        public void getAllTerms(String contents){
            termList=new ArrayList<String>();
            Document doc = Jsoup.parse(contents);
            Elements terms=doc.select("div#tab-contents > section > h2");
            int  i=0;
            for(Element element:terms){
                String term=element.text();
                termList.add(term.trim());
            }
        }
        @Override
        protected void onPostExecute(String a) {
            Log.i(ACTIVITY_NAME,"we out here");
            getAllTerms(contents);
            termTV.setVisibility(View.VISIBLE);
            termSpin.setVisibility(View.VISIBLE);
            get_a_term(levelTV,termTV,termSpin);
        }
        @Override
        protected String doInBackground(String... strings) {
            try{
//                Log.i(ACTIVITY_NAME,"url connect");
                URL url = new URL("https://bohr.wlu.ca/courses/courseSchedules.php");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
//                Log.i(ACTIVITY_NAME,"url connect");
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream in = conn.getInputStream();
                try{
                contents = convertStreamToString(in);
//                Log.i(ACTIVITY_NAME,contents);
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


    public void goToCourseSelection(View view){
        Intent intent=new Intent(RegisterActivity2.this,CourseSelectionActivity.class);
        intent.putExtra("year",yearPreference);
        intent.putExtra("term",termPreference);
        intent.putExtra("level",levelPreference);
        startActivity(intent);
    }
}