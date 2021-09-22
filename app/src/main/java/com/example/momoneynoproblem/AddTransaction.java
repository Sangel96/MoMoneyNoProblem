package com.example.momoneynoproblem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTransaction extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public FirebaseDatabase firebaseDatabase;

    CheckBox income_checkBox;
    CheckBox expenses_checkBox;

    Button addTransactionButton;
    Button addPhotoButton;
    EditText editTextAmount = null;
    Spinner transactionSourceTypeSpinner = null;

    String transaction_type = "";
    String transaction_source_type = "";

    private static final String[] paths = {"Salary", "Rent", "Cloths", "Gifts", "Shopping", "Eating out", "Entertainment", "Fuel", "Holiday",
            "Kids", "Sports", "Travel", "Other sources"};
    private Object Spinner;

    public DatabaseReference databaseReference;
    private Object transaction1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        addTransactionButton = findViewById(R.id.addTransactionButton);
        addPhotoButton = findViewById(R.id.addPhotoButton);
        editTextAmount = findViewById(R.id.editTextAmount);
        addTransactionButton = findViewById((R.id.addTransactionButton));
        transactionSourceTypeSpinner = findViewById((R.id.transactionSourceTypeSpinner));

        // below line is used to get reference for our database.
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Transaction");

        transactionSourceTypeSpinner = (android.widget.Spinner)
                findViewById((R.id.transactionSourceTypeSpinner));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddTransaction.this,
                android.R.layout.simple_list_item_1, paths);
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

                    }
                });
        mAuth = FirebaseAuth.getInstance();

        //adds functionality to Add Transaction
//        addTransactionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String amount = editTextAmount.getText().toString();
//                String income = transaction_type;
//                String expenses = transaction_source_type;
//               // String income = income_checkBox.getText().toString();
//                //String expenses = income_checkBox.getText().toString();
//
//                for (int i = 0; i <= editTextAmount.getText().toString().length() - 1; i++) {
//                    if (!Character.isDigit(editTextAmount.getText().toString().charAt(i))) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(AddTransaction.this);
//                        builder.setMessage("Make sure to enter numeric value")
//                                .setNegativeButton("Retry", null)
//                                .create()
//                                .show();
//
//                        return;
//                    }
//                }
//
//
//                if (!income_checkBox.isChecked() & !expenses_checkBox.isChecked()) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(AddTransaction.this);
//                    builder.setMessage("Make sure to select the transaction type")
//                            .setNegativeButton("Retry", null)
//                            .create()
//                            .show();
//
//                    return;
//                } else {
//
//                    if (income_checkBox.isChecked()) {
//                        transaction_type = "income";
//                    } else {
//                        transaction_type = "expense";
//                    }
//                }
//
//
//                if (transaction_source_type.equals("")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(AddTransaction.this);
//                    builder.setMessage("Make sure to select the transaction source type")
//                            .setNegativeButton("Retry", null)
//                            .create()
//                            .show();
//
//                } else {
//                    if (transaction_type.equals("income")) {
//                        if (!transaction_source_type.equals("Salary") & !transaction_source_type.equals("Rent") &
//                                !transaction_source_type.equals("Other sources")) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(AddTransaction.this);
//                            builder.setMessage("Make sure to select valid income transaction source type")
//                                    .setNegativeButton("Retry", null)
//                                    .create()
//                                    .show();
//
//                        }
//                    } else {
//                        if (transaction_source_type.equals("Salary") || transaction_source_type.equals("Rent")) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(AddTransaction.this);
//                            builder.setMessage("Make sure to select valid Expense transaction source type")
//                                    .setNegativeButton("Retry", null)
//                                    .create()
//                                    .show();
//
//                        }
//                    }
//                }
//
//
//            }
//        });
        String amount = editTextAmount.getText().toString();
        String income = transaction_type.trim();
        String expenses = transaction_source_type.trim();
       // String income = income_checkBox.getText().toString();
       // String expenses = income_checkBox.getText().toString();

        com.example.momoneynoproblem.transaction1 trans = new transaction1("1000","Income","Salary");
        databaseReference.push().setValue(trans);
        Toast.makeText(AddTransaction.this,"Data inserted!", Toast.LENGTH_SHORT).show();

    }
}

