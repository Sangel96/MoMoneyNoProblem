package com.example.momoneynoproblem.DescribeBudgetAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.momoneynoproblem.R;

public class EmergencyButton extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_button);

        Button emergency_button = findViewById(R.id.EmergencyButtonID); //Initializes Emergency Button
        emergency_button.setOnClickListener(view -> {
            startActivity(new Intent(this,AskSymptoms.class));
        });

    }
}