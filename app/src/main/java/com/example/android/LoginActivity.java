package com.example.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME="LoginActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Registration");

        mAuth = FirebaseAuth.getInstance();
        Log.i(ACTIVITY_NAME,"In OnCreate()");
    }

    @Override
    public void onStart() {
        super.onStart();
//        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
    }

//    private void reload() {
//        Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
////        startActivity(intent);
//    }

    public void logInAction(View view){
        EditText usernameET = (EditText) findViewById(R.id.usernametext);
        String email = usernameET.getText().toString();
        EditText passwordET=(EditText) findViewById(R.id.passwordtext);
        String password = passwordET.getText().toString();
        //need to check if user exists in database
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(ACTIVITY_NAME, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID = user.getUid();
                            updateUI(user, userID);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(ACTIVITY_NAME, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user, String userID) {

        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    public void goToRegisterActivity(View view){
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

//    public Boolean validate(String username,String password){
//        if (TextUtils.isEmpty(password)){
//            Toast.makeText(LoginActivity.this,R.string.psswrd_error_message, Toast.LENGTH_SHORT).show();
//            return false;
//        }else if(username.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(username).matches()){
//            Toast.makeText(LoginActivity.this,R.string.email_error_message, Toast.LENGTH_SHORT).show();
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    public String encryptHmacSha256(String key, String s) {
//        byte[] message=s.getBytes(StandardCharsets.UTF_8);
//        byte[] secretKey=key.getBytes(StandardCharsets.UTF_8);
//        byte[] hmacSha256 = null;
//        try {
//            Mac mac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
//            mac.init(secretKeySpec);
//            hmacSha256 = mac.doFinal(message);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to calculate hmac-sha256", e);
//        }
//        String encryption = new String(hmacSha256, StandardCharsets.UTF_8);
//        return encryption;
//    }



//    public void logInAction(View view){
//        EditText usernameET = (EditText) findViewById(R.id.usernametext);
//        String emailText = usernameET.getText().toString();
//        EditText passwordET=(EditText) findViewById(R.id.passwordtext);
//        String passwordText = passwordET.getText().toString();
//        //need to check if user exists in database
//        if(validate(emailText,passwordText)){
//            //check
//            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(intent);
//        }
//    }


}