package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class RegisterActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registration");

    }

    public Boolean validatePassword(String password){
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern password_pattern= Pattern.compile(regex);
        Matcher password_check = password_pattern.matcher(password);
        return password_check.matches();
    }

    public Boolean validateEmail(String email){
        String[] emailArray=email.split("@");
        String laurierDomain="mylaurier.ca";
        if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false;
        }else if(!emailArray[1].equals(laurierDomain)){
            return false;
        }else{
            return true;
        }
    }

    public Boolean validateRegistration(String firstName,String lastName,String email, String password,String password2){
        if(TextUtils.isEmpty(firstName)){
            Toast.makeText(RegisterActivity.this, R.string.fName_error_message, Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(lastName)){
            Toast.makeText(RegisterActivity.this, R.string.lName_error_message, Toast.LENGTH_SHORT).show();
            return false;
        }else if(!validateEmail(email)){
            Toast.makeText(RegisterActivity.this,R.string.email_error_message, Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
            Toast.makeText(RegisterActivity.this, R.string.reg_psswrd_error_message, Toast.LENGTH_SHORT).show();
            return false;
        }else if(!validatePassword(password)){
            Toast.makeText(RegisterActivity.this, R.string.regEx_psswrd_error_message, Toast.LENGTH_SHORT).show();
            return false;
        }else if (!password.equals(password2)){
            Toast.makeText(RegisterActivity.this, R.string.confirm_password_error_message,Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
    public String encryptHmacSha256(String key, String s) {
        byte[] message=s.getBytes(StandardCharsets.UTF_8);
        byte[] secretKey=key.getBytes(StandardCharsets.UTF_8);
        byte[] hmacSha256 = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
            mac.init(secretKeySpec);
            hmacSha256 = mac.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }
        String encryption = new String(hmacSha256, StandardCharsets.UTF_8);
        return encryption;
    }

    public void registerAction(View view){
        EditText firstNameET =findViewById(R.id.firstNametext);
        String firstName = firstNameET.getText().toString();
        EditText lastNameET = findViewById(R.id.lastNametext);
        String lastName = lastNameET.getText().toString();
        EditText emailET =  findViewById(R.id.emailtext);
        String email = emailET.getText().toString();
        EditText passwordET= findViewById(R.id.regPasswordtext);
        String password = passwordET.getText().toString();
        EditText confirmPasswordET = findViewById(R.id.passwordConfirmationtext);
        String confirmPassword = confirmPasswordET.getText().toString();

        if (validateRegistration(firstName,lastName,email,password,confirmPassword)){
            //check if user exists already in database. if not, add data to database
            Intent intent=new Intent(RegisterActivity.this,RegisterActivity2.class);
            startActivity(intent);
        }

    }
}