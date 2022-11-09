package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public Boolean validateRegistration(String email, String password,String password2){
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, R.string.psswrd_error_message, Toast.LENGTH_SHORT).show();
            return false;
        }else if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(RegisterActivity.this,R.string.email_error_message, Toast.LENGTH_SHORT).show();
            return false;
        }else if (!password.equals(password2)){
            Toast.makeText(RegisterActivity.this, R.string.confirm_password_error_message,Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    public void registerAction(View view){
        EditText firstNameET = (EditText) findViewById(R.id.firstNametext);
        String firstName = firstNameET.getText().toString();
        EditText lastNameET = (EditText) findViewById(R.id.lastNametext);
        String lastName = lastNameET.getText().toString();
        EditText emailET = (EditText) findViewById(R.id.emailtext);
        String email = emailET.getText().toString();
        EditText passwordET=(EditText) findViewById(R.id.regPasswordtext);
        String password = passwordET.getText().toString();
        EditText confirmPasswordET = (EditText) findViewById(R.id.passwordConfirmationtext);
        String confirmPassword = confirmPasswordET.getText().toString();

        if (validateRegistration(email,password,confirmPassword)){
            //Add data to database
            Intent intent=new Intent(RegisterActivity.this,RegisterActivity2.class);
            startActivity(intent);
        }

    }
}