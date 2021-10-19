package com.example.momoneynoproblem.DescribeBudgetAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.momoneynoproblem.R;

public class AskSymptoms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_symptoms);
        Button enterSymptomButton = findViewById(R.id.EnterButton);
        enterSymptomButton.setOnClickListener(view -> {
            startActivity(new Intent(this,AnalysisPage.class));
        });
    }
}