package com.example.momoneynoproblem.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.MainActivity;
import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.UserSignUp.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    FirebaseAuth mAuth;
    private EditText enterEmail;
    private Button resetPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //assign variable with respective layout
        enterEmail = findViewById(R.id.enterEmail);
        resetPassword = findViewById(R.id.resetPassword);

        mAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(view -> {
            resetPassword();     //Registers the user when they press the signup button
        });
    }

    private void resetPassword(){
        String email = enterEmail.getText().toString().trim();

        if(email.isEmpty()){
            enterEmail.setError("Email is required!");
            enterEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            enterEmail.setError("Please provide valid email!");
            enterEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ForgotPassword.this, "Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
