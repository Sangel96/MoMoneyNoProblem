package com.example.momoneynoproblem.UserSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.Login.Login;
import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.UserSignUp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignUp extends AppCompatActivity {

    FirebaseAuth mAuth;

    private EditText etName, etEmail, etPassword, etRePassword;
    private ImageView tvLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Assign each variable with its respective layout names
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRePassword = (EditText) findViewById(R.id.etRePassword);
        btnSignUp = (ImageView) findViewById(R.id.btnSignUp);
        tvLogin = (ImageView) findViewById(R.id.tvLogin);

        btnSignUp.setOnClickListener(view -> {
            registerUser();     //Registers the user when they press the signup button
                });

        mAuth = FirebaseAuth.getInstance(); //Obtaining an instance of FireBase to be used later

        tvLogin.setOnClickListener(view -> {    //Redirect to login page when button is pressed
            startActivity(new Intent(UserSignUp.this, Login.class));
        });
    }

    private void registerUser() {
        //converts all field to String and remove spaces (trim())
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String rePassword = etRePassword.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || //Checks for empty field(s)
                TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
            Toast.makeText(this, "One or more fields is empty", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(rePassword)) { //Checks for matching passwords
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            //Firebase function that registers the user using email/pass
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {  //Registration feedback
                        User user = new User(name, email);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UserSignUp.this, "User successfully registered", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(UserSignUp.this, "Registration failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        startActivity(new Intent(UserSignUp.this, Login.class));
                    } else {
                        Toast.makeText(UserSignUp.this, "Registration failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
