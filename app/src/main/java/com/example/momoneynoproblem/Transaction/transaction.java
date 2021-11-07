package com.example.momoneynoproblem.Transaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.google.firebase.auth.FirebaseAuth;

public class transaction extends AppCompatActivity {
    FirebaseAuth mAuth;

    ImageView add_transaction;
    ImageView manage_transaction;
    private Object transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        add_transaction = (ImageView) findViewById(R.id.add_transaction);
        manage_transaction = (ImageView) findViewById(R.id.manage_transaction);
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
                Intent i= new Intent(transaction.this, ManageTransaction.class);
                startActivity(i);

            }
        });
    }
}

