package com.smilias.smarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
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

public class LogInActivity extends AppCompatActivity {
    SQLiteDatabase db;
    LocationManager locationManager;
    private FirebaseAuth mAuth;
    EditText editTEmail, editTPass;
    private static final String TAG = "LogInActivity";

    public void signUp(View view){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void logIn(View view) {
        final String email = editTEmail.getText().toString();
        final String password = editTPass.getText().toString();
        //check oti einai simplirwmena kai zitaei focus an den einai
        if (email.isEmpty()) {
            editTEmail.setError("Email is required");
            editTEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTPass.setError("Password is required");
            editTPass.requestFocus();
            return;
        }
        //log in to user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LogInActivity.this, "success.",
                                    Toast.LENGTH_SHORT).show();
                            if(user.isEmailVerified()) {
                                Intent intent1= new Intent(LogInActivity.this,MainActivity.class);
                                startActivity(intent1);
                            }else{
                                Intent intent2= new Intent(LogInActivity.this,VerifyActivity.class);
                                startActivity(intent2);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void forgetPassword(View view) {
        Intent intent=new Intent(LogInActivity.this,ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        editTEmail = findViewById(R.id.editTEmail);
        editTPass = findViewById(R.id.editTPass);
        mAuth = FirebaseAuth.getInstance();
    }
}