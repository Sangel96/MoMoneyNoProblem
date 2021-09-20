package com.example.momoneynoproblem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class transaction extends AppCompatActivity {
    Button add_transaction;
    private Object transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        add_transaction = (Button) findViewById(R.id.Add_transaction);
        add_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(transaction.this,AddTransaction.class);
                startActivity(i);

            }
        });
    }
}

