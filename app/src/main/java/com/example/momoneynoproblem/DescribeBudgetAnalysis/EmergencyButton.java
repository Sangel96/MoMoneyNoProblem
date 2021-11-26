package com.example.momoneynoproblem.DescribeBudgetAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.momoneynoproblem.R;

public class EmergencyButton extends AppCompatActivity {
    ImageView emergency_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_button);

        ImageView emergency_button = findViewById(R.id.emergencyButtonText); //Initializes Emergency Button
        emergency_button.setOnClickListener(view -> {
            startActivity(new Intent(this,AskSymptoms.class));
        });

    }
}