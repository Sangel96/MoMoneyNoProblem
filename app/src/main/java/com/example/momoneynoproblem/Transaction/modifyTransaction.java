package com.example.momoneynoproblem.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class modifyTransaction extends AppCompatActivity {
    private static com.example.momoneynoproblem.Transaction.Model module;
    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public DatabaseReference firebaseDatabase;
    public DatabaseReference databaseReference;

    public RadioGroup radioGroup;
    public RadioButton incomeRadioButton;
    public RadioButton expenseRadioButton;
    public RadioButton radioSourceButton;

    public Button modifyButton;
    public EditText amountEditText, TranIDEdit, DateEdit, StoreNameEdit;
    public String transaction_type = "";
    public String transaction_source_type = "";
    public Spinner transactionSourceTypeSpinner;



    //grabs extra data from intent
    //Intent intent = getIntent();
    //public Transaction1 trans;
    Model m1 = new Model() ;
    private static final String[] paths = {"Salary", "Rent", "Cloths", "Gifts", "Shopping",
            "Eating out", "Entertainment", "Fuel", "Holiday",
            "Kids", "Sports", "Travel", "Other sources"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_transaction);

        databaseReference = FirebaseDatabase.getInstance().getReference("Transactions");

        radioGroup = findViewById(R.id.radioGroup);
        incomeRadioButton = findViewById(R.id.incomeRadioButton);
        expenseRadioButton = findViewById(R.id.expenseRadioButton);
        modifyButton = (Button) findViewById(R.id.modify_Button);
        TranIDEdit = (EditText) findViewById(R.id.TranIDEdit);
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        DateEdit = (EditText) findViewById(R.id.DateEdit);
        StoreNameEdit = (EditText) findViewById(R.id.StoreNameEdit);


        //final String str = m1.getTransID();

        //TranIDEdit.setText(str);
        //TranIDEdit.setEnabled(false);


        // implement spinner
        transactionSourceTypeSpinner = (android.widget.Spinner) findViewById(R.id.transactionSourceTypeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(modifyTransaction.this,
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
                        Toast.makeText(modifyTransaction.this, "Please choose source type.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Transaction1 transactions = snapshot.child(str).getValue(Transaction1.class);
//                amountEditText.setText(transactions.getAmount());
//                DateEdit.setText(transactions.getDate());
//                StoreNameEdit.setText(transactions.getStoreName());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateArrayList();
                Intent intent = new Intent(modifyTransaction.this, showData.class);
                startActivity(intent);

                // Model m1 = new Model(TranIDEdit.toString().trim(), String transaction_type, String transaction_source_type, String amount, String date, String storeName);
            }
        });
    }

//    private void Cleartext() {
//        TranIDEdit.setText("");
//        amountEditText.setText("");
//        DateEdit.setText("");
//        StoreNameEdit.setText("");
//        TranIDEdit.requestFocus();
//    }

    private void updateArrayList() {
        final String ID = TranIDEdit.getText().toString().trim();
        final String amount = amountEditText.getText().toString().trim();
        final String date = DateEdit.getText().toString().trim();
        final String storename = StoreNameEdit.getText().toString().trim();
        final String trans_type = transaction_type.trim();
        final String trans_sourceType = transaction_source_type.trim();

        //        public Transaction1(String amount,String transaction_type, String transaction_source_type,
        //                        String transID, String date, String storeName) {
        Transaction1 transaction1 = new Transaction1("500", "Income", "Salary", "T001", "11-1-21", "Target");
        databaseReference.push().setValue(transaction1);

//    private void updateArrayList() {
//        final String ID = TranIDEdit.getText().toString().trim();
//        final String amount = amountEditText.getText().toString().trim();
//        final String date = DateEdit.getText().toString().trim();
//        final String storename = StoreNameEdit.getText().toString().trim();
//        final String trans_type = transaction_type.trim();
//        final String trans_sourceType = transaction_source_type.trim();
//
//        if (TextUtils.isEmpty(ID)) {
//            TranIDEdit.setError("Please enter your ID!");
//        } else if (TextUtils.isEmpty((amount))) {
//            amountEditText.setError("Please enter new amount!");
//        } else if (TextUtils.isEmpty((date))) {
//            DateEdit.setError("Please enter the new date!");
//        } else if (TextUtils.isEmpty((storename))) {
//            StoreNameEdit.setError("Please enter the new store name!");
//        } else {
//
//            Transaction1 transaction1 = new Transaction1(ID, amount, date, storename);
//            databaseReference.child("Transactions").child(ID).
//                    addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            databaseReference = FirebaseDatabase.getInstance().getReference();
//                            databaseReference.child("Transactions").child(ID)
//                                    .child(amount).setValue(amount);
//                            databaseReference.child("Transactions").child(ID)
//                                    .child(date).setValue(date);
//                            databaseReference.child("Transactions").child(ID)
//                                    .child(storename).setValue(storename);
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//            Toast.makeText(this, "Transaction is updated", Toast.LENGTH_SHORT).show();
//            Cleartext();
        //    }

    }
}



