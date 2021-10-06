package com.example.momoneynoproblem;

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
import com.example.momoneynoproblem.Main;
import com.example.momoneynoproblem.MainActivity;
import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.UserSignUp.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class transaction extends AppCompatActivity {
    FirebaseAuth mAuth;

    Button add_transaction;
    Button manage_transaction;
    private Object transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        add_transaction = (Button) findViewById(R.id.add_transaction);
        manage_transaction = (Button) findViewById(R.id.manage_transaction);
        add_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(transaction.this, AddTransaction.class);
                startActivity(j);
            }
        });


        manage_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(transaction.this,ManageTransaction.class);
                startActivity(i);

            }
        });
    }
}

