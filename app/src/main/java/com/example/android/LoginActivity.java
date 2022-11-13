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

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Registration");

        Log.i(ACTIVITY_NAME,"In OnCreate()");
    }

    public Boolean validate(String username,String password){
        if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,R.string.psswrd_error_message, Toast.LENGTH_SHORT).show();
            return false;
        }else if(username.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            Toast.makeText(LoginActivity.this,R.string.email_error_message, Toast.LENGTH_SHORT).show();
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


    public void logInAction(View view){
        EditText usernameET = (EditText) findViewById(R.id.usernametext);
        String emailText = usernameET.getText().toString();
        EditText passwordET=(EditText) findViewById(R.id.passwordtext);
        String passwordText = passwordET.getText().toString();
        //need to check if user exists in database
        if(validate(emailText,passwordText)){
            //check
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void goToRegisterActivity(View view){
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}