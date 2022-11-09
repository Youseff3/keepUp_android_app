package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME,"In OnCreate()");
    }

    public Boolean validate(String username,String password){
        if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,R.string.psswrd_error_message, Toast.LENGTH_SHORT).show();
            return false;
        }else if(username.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            Toast.makeText(LoginActivity.this,R.string.email_error_message, Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    public void logInAction(View view){
//        SharedPreferences sharedPrefs = getSharedPreferences("MySharedPref",MODE_PRIVATE);
//        SharedPreferences.Editor myEdit = sharedPrefs.edit();
        EditText usernameET = (EditText) findViewById(R.id.usernametext);
        String emailText = usernameET.getText().toString();
        EditText passwordET=(EditText) findViewById(R.id.passwordtext);
        String passwordText = passwordET.getText().toString();



        if(validate(emailText,passwordText)){
//            myEdit.putString("UserEmail",emailText);
//            myEdit.commit();
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void goToRegisterActivity(View view){
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}