package com.example.momoneynoproblem.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageTransaction extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    public Button saveButton = null;
    public Button delete_Button = null;
    public Button modify_Button = null;
    public Button search_Button = null;
    public EditText transactionTypeEditText = null;
    public EditText amountEditText = null;
    public String transaction_source_type = "";
    public Spinner transactionSourceTypeSpinner = null;
    public Transaction1 trans;
    private static final String[] paths = {"Salary", "Rent", "Cloths", "Gifts", "Shopping",
            "Eating out", "Entertainment", "Fuel", "Holiday",
            "Kids", "Sports", "Travel", "Other sources"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_transaction);
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Transactions");
        modify_Button = (Button) findViewById(R.id.modify_Button);
        delete_Button = (Button) findViewById(R.id.delete_Button);
        search_Button = (Button) findViewById(R.id.search_Button);
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        transactionTypeEditText = (EditText) findViewById(R.id.transactionTypeEditText);

        // implement spinner
        transactionSourceTypeSpinner = (Spinner) findViewById((R.id.transactionSourceTypeSpinner));
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ManageTransaction.this,
                android.R.layout.simple_list_item_1, paths);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        transactionSourceTypeSpinner.setAdapter(adapter);

        transactionSourceTypeSpinner.setOnItemSelectedListener
                (new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View
                            view, int position, long id) {
                        Log.i("item", (String) parent.getItemAtPosition(position));
                        transaction_source_type = (String)
                                parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(ManageTransaction.this, "Please choose sour type.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

        // when click on the search button, go to the search screen
        search_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(ManageTransaction.this, searchForTransaction.class);
                startActivity(j);
            }
        });

        //adds functionality to Add Transaction
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text from our edittext fields.
                String trans_type = transactionTypeEditText.getText().toString().trim();
                String trans_sourceType = transaction_source_type.trim();
                String amount = amountEditText.getText().toString().trim();

            }

        });

        mAuth = FirebaseAuth.getInstance();  //Obtaining an instance of FireBase to be used later
    }


}