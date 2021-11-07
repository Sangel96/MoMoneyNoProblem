package com.example.momoneynoproblem.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class modifyTransaction extends AppCompatActivity {
    private static com.example.momoneynoproblem.Transaction.Model module;
    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public DatabaseReference firebaseDatabase;
    public DatabaseReference databaseReference;

    public EditText input1, input2, input3;

    public RadioGroup radioGroup;
    public RadioButton incomeRadioButton;
    public RadioButton expenseRadioButton;
    public RadioButton radioSourceButton;

    public Button modifyButton;
    public EditText amountEditText, TranIDEdit, DateEdit, StoreNameEdit;
    public String transaction_type = "";
    public String transaction_source_type = "";
    public Spinner transactionSourceTypeSpinner;

    //Model m1 = new Model() ;
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

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ID = TranIDEdit.getText().toString().trim();
                final String amount = amountEditText.getText().toString().trim();
                final String date = DateEdit.getText().toString().trim();
                final String storename = StoreNameEdit.getText().toString().trim();
                final String trans_type = transaction_type.trim();
                final String trans_sourceType = transaction_source_type.trim();
                if (TextUtils.isEmpty(ID)) {
                    TranIDEdit.setError("Please enter your ID!");
                } else if (TextUtils.isEmpty((amount))) {
                    amountEditText.setError("Please enter new amount!");
                } else if (TextUtils.isEmpty((date))) {
                    DateEdit.setError("Please enter the new date!");
                } else if (TextUtils.isEmpty((storename))) {
                    StoreNameEdit.setError("Please enter the new store name!");
                } else {

                    Transaction1 transaction1 = new Transaction1(amount,transaction_type, transaction_source_type,ID,date, storename);
                    HashMap hashMap = new HashMap();
                    hashMap.put("amount", amount);
                    hashMap.put("date", date);
                    hashMap.put("storeName", storename);
                    hashMap.put("transID", ID);
                    hashMap.put("transaction_source_type", transaction_source_type);
                    hashMap.put("transaction_type", transaction_type);

                    databaseReference.child(ID).setValue(hashMap);

                    //                    databaseReference.child("Transactions").addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    databaseReference = FirebaseDatabase.getInstance().getReference("Transactions");
//                                    databaseReference.child(ID)
//                                            .child(amount).setValue(transaction1.getAmount());
//                                    databaseReference.child(ID)
//                                            .child(date).setValue(transaction1.getDate());
//                                    databaseReference.child(ID)
//                                            .child(storename).setValue(transaction1.getStoreName());
//                                    databaseReference.child(ID)
//                                            .child(transaction_type).setValue(transaction1.getTransaction_type());
//                                    databaseReference.child(ID)
//                                            .child(transaction_source_type).setValue(transaction1.getTransaction_source_type());
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//                    Toast.makeText(modifyTransaction.this,"Your Data is Successfully Updated",Toast.LENGTH_SHORT).show();
//

                }

            }
        });
    }


//        public Transaction1(String amount,String transaction_type, String transaction_source_type,
//                                String transID, String date, String storeName) {
//        Transaction1 transaction1 = new Transaction1("500", "Income", "Salary", "T001", "11-1-21", "Target");
//        databaseReference.push().setValue(transaction1);


}



