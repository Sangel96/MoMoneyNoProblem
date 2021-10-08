package com.example.momoneynoproblem.Goals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateGoal extends AppCompatActivity {

    //Edittexts, buttons, and Database object declared
    private EditText etName, etMonthlyLimit, etDate;
    private Button btnAddGoal, btnCancel;
    DatabaseReference goalDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        //Assign variables to its respective container name
        etName = findViewById(R.id.editTextTextPersonName4);
        etMonthlyLimit = findViewById(R.id.editTextTextPersonName5);
        etDate = findViewById(R.id.editTextTextPersonName6);
        btnAddGoal = findViewById(R.id.btnSaveGoal);
        btnCancel = findViewById(R.id.btnCancel);

        //Assign firebase instance to goalDb
        goalDb = FirebaseDatabase.getInstance().getReference().child("Goals");

        //Runs insertGoal when button is pressed
         btnAddGoal.setOnClickListener(view -> {
            insertGoal();
        });

         //Go back to add goals menu if cancel is pressed
        btnCancel.setOnClickListener(view -> {    //Redirect to login page when button is pressed
            startActivity(new Intent(CreateGoal.this, AddGoals.class));
        });
    }

    private void insertGoal() {
        //Basic variable declaration
        String name = etName.getText().toString().trim();
        double monthlyLimit = Double.parseDouble(etMonthlyLimit.getText().toString().trim());
        String date = etDate.getText().toString().trim();

        //Create new Goal object
        Goal newGoal = new Goal(name,monthlyLimit,date);

        //insert value into database
        goalDb.push().setValue(newGoal);
        Toast.makeText(CreateGoal.this, "Successfully added goal", Toast.LENGTH_SHORT).show();
    }
}