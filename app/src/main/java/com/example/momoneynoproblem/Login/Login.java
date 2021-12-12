package com.example.momoneynoproblem.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.MainActivity;
import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Singleton;
import com.example.momoneynoproblem.UserSignUp.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//import com.example.momoneynoproblem.Main;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;


    private EditText username_input, password_input;
    private ImageView signup, signin, forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_input = (EditText) findViewById(R.id.username_input);
        password_input = (EditText) findViewById(R.id.password_input);
        forgotPassword = (ImageView) findViewById(R.id.forgotPassword);
        signin = (ImageView) findViewById(R.id.signin);
        signup = (ImageView) findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();

        //adds functionality to login
        signin.setOnClickListener(view -> {
            login();
        });

        //adds functionality to sign up
        signup.setOnClickListener(view -> {
            startActivity(new Intent(this, UserSignUp.class));
        });

        forgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(this, ForgotPassword.class));
        });
    }

    private void login() {
        String email = username_input.getText().toString().trim();
        String password = password_input.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "One or more fields is empty", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Singleton.getInstance().setEmail(email);
                        Toast.makeText(Login.this, "User logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                    } else {
                        Toast.makeText(Login.this, "Login failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
