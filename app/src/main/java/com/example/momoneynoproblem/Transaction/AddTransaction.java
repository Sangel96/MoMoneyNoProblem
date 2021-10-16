package com.example.momoneynoproblem.Transaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AddTransaction extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    public RadioGroup radioGroup;
    public RadioButton incomeRadioButton = null;
    public RadioButton expenseRadioButton = null;
    public RadioButton radioSourceButton;

    public Button addButton = null;
    public Button scanButton = null;
    public EditText amountEditText = null;
    public EditText DateEdit = null;
    public EditText StoreNameEdit = null;

    public String transaction_type = "";
    public String transaction_source_type = "";
    public Spinner transactionSourceTypeSpinner = null;
    public Transaction1 trans;
    private static final String[] paths = {"Salary", "Rent", "Cloths", "Gifts", "Shopping",
            "Eating out", "Entertainment", "Fuel", "Holiday",
            "Kids", "Sports", "Travel", "Other sources"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Transactions");
        radioGroup = findViewById(R.id.radioGroup);
        incomeRadioButton = findViewById(R.id.incomeRadioButton);
        expenseRadioButton = findViewById(R.id.expenseRadioButton);
        addButton = findViewById(R.id.addButton);
        scanButton = findViewById(R.id.scanButton);
        amountEditText = findViewById(R.id.amountEditText);
        DateEdit = findViewById(R.id.DateEdit);
        StoreNameEdit = findViewById(R.id.StoreNameEdit);


        // implement spinner
        transactionSourceTypeSpinner = findViewById((R.id.transactionSourceTypeSpinner));
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddTransaction.this,
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
                        Toast.makeText(AddTransaction.this, "Please choose sour type.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

        //adds functionality to Add Transaction
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text from our edittext fields.
                String trans_type = transaction_type.trim();
                String trans_sourceType = transaction_source_type.trim();
                String amount = amountEditText.getText().toString().trim();
                String date = DateEdit.getText().toString().trim();
                String storeName = StoreNameEdit.getText().toString().trim();

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioSourceButton = findViewById(selectedId);

                Toast.makeText(AddTransaction.this,
                        radioSourceButton.getText(), Toast.LENGTH_SHORT).show();

                // below line is for checking weather the edittext and RadioButton fields are empty or not.
                if ((!incomeRadioButton.isChecked() & !expenseRadioButton.isChecked()) ||
                        (incomeRadioButton.isChecked() & expenseRadioButton.isChecked())) {
                    Toast.makeText(AddTransaction.this,
                            "Please check any of the transaction type(income or expenses.",
                            Toast.LENGTH_SHORT).show();
                    if (TextUtils.isEmpty(amount))
                        // if the text amount are empty then show message.
                        Toast.makeText(AddTransaction.this, "Please insert the Amount.",
                                Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add data to our database.
                    addDatatoFirebase(amount, trans_type, trans_sourceType,date, storeName);
                }

            }
        });

        mAuth = FirebaseAuth.getInstance();  //Obtaining an instance of FireBase to be used later
    }

    private void addDatatoFirebase(String amount, String transaction_type,
                                   String transaction_source_type, String date, String storeName) {
        // set data in our object class.
        Transaction1 trans = new Transaction1();
        trans.setTransaction_type(transaction_type);
        trans.setTransaction_source_type(transaction_source_type);
        trans.setAmount(amount);
        trans.setDate(date);
        trans.setStoreName(storeName);
        DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference().
                child("Transactions");
        // we are use add value event listener method which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
//
                DatabaseReference transChild = databaseReference.push();
                //databaseReference.child(transChild.getKey()).setValue(trans);
                databaseReference.child("date").setValue(trans);

                // after adding this data we are showing toast message.
                Toast.makeText(AddTransaction.this, "data added", Toast.LENGTH_SHORT).show();
                amountEditText.setText("");
                DateEdit.setText("");
                StoreNameEdit.setText("");

                //System.exit(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(AddTransaction.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }

        });

    }
}


