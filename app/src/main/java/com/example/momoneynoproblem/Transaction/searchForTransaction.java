package com.example.momoneynoproblem.Transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

import com.google.firebase.auth.FirebaseAuth;

public class searchForTransaction extends AppCompatActivity {
    FirebaseAuth mAuth;

    Button daily_Button;
    Button monthly_Button;
    Button Yearly_Button;
    private Object searchForTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_transaction);

        daily_Button = (Button) findViewById(R.id.daily_Button);
        monthly_Button = (Button) findViewById(R.id.monthly_Button);
        Yearly_Button = (Button) findViewById(R.id.Yearly_Button);

        daily_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent j = new Intent(searchForTransaction.this, AddTransaction.class);
                //startActivity(j);
            }
        });


        monthly_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i= new Intent(searchForTransaction.this,ManageTransaction.class);
                //startActivity(i);

            }
        });
    }
}



