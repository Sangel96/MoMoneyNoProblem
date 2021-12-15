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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Singleton;
import com.example.momoneynoproblem.scanner.scanner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddTransaction extends AppCompatActivity {
    // add different request code for every activity we are starting
    public static final int SECOND_ACTIVITY_RECUEST_CODE = 0;

    public FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    public RadioGroup radioGroup;
    public RadioButton incomeRadioButton = null;
    public RadioButton expenseRadioButton = null;
    public RadioButton radioSourceButton;

    public Button addButton = null;
    public Button scanButton = null;
    public EditText amountEditText = null;
    public EditText TranIDEdit = null;
    public EditText DateEdit = null;
    public EditText StoreNameEdit = null;
    //public  EditText EditAccountId= null;

    public String transaction_type = "";
    public String transaction_source_type = "";
    public Spinner transactionSourceTypeSpinner = null;
    public Transaction1 trans;

    private static final String[] paths = {"Travel", "Shopping", "Sports", "Other", "Rent"};

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
        //TranIDEdit = findViewById(R.id.TranIDEdit);
        scanButton = findViewById(R.id.scanButton);
        amountEditText = findViewById(R.id.amountEditText);
        DateEdit = findViewById(R.id.DateEdit);
        StoreNameEdit = findViewById(R.id.StoreNameEdit);
        //EditAccountId = findViewById(R.id.EditAccountId);


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
                        Singleton.getInstance().setTransType((String) parent.getItemAtPosition(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(AddTransaction.this, "Please choose source type.",
                                Toast.LENGTH_SHORT).show();

                    }
                });


        //adds functionality to Add Transaction
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String trans_type = transaction_type.trim();
                String trans_sourceType = Singleton.getInstance().getTransType();
                String amount = amountEditText.getText().toString().trim();
                String date = DateEdit.getText().toString().trim();
                String storeName = StoreNameEdit.getText().toString().trim();
                //String transID = Singleton.getInstance().getTransID();
                String accountId = Singleton.getInstance().getUserID();

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
                    addDatatoFirebase(amount, String.valueOf(radioSourceButton.getText()), trans_sourceType, date, storeName, "0", accountId);
                    //sets the Transaction Type to null because the value doesn't need to be stored after passing it to the method above
//
                }
            }
        });

        ActivityResultLauncher startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                // opens activity to scanner and returns the parse values
                DateEdit.setText(result.getData().getStringExtra("date"));

                amountEditText.setText(result.getData().getStringExtra("amount"));
            }
        });

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddTransaction.this, scanner.class);
                startForResult.launch(intent);
            }
        });

        //mAuth = FirebaseAuth.getInstance();  //Obtaining an instance of FireBase to be used later
    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//        if(requestCode == SECOND_ACTIVITY_RECUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                //Get string data from Intent
//                String amount = data.getStringExtra("amount");
//                String date= data.getStringExtra("date");
//
//                // set text view with string
//                TextView textView = (TextView) findViewById(R.id.textView);
//                textView.setText(amount);
//                textView.setText(date);
//
//            }
//        }
//    }


    private void addDatatoFirebase(String amount, String transaction_type,
                                   String transaction_source_type, String date, String storeName,
                                   String transID, String accountId) {
        // set data in our object class.
        Transaction1 trans = new Transaction1();
        trans.setTransaction_type(transaction_type);
        trans.setTransaction_source_type(transaction_source_type);
        trans.setAmount(amount);
        trans.setDate(date);
        trans.setStoreName(storeName);
//      trans.setTransID(transID);
        trans.setAccountId(accountId);
        Toast.makeText(this, accountId, Toast.LENGTH_SHORT).show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().
                child("Transactions");
        // we are use add value event listener method which is called with database reference.
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                Singleton.getInstance().setTransID(String.valueOf( snapshot.getChildrenCount() + 1));
                trans.setTransID(Singleton.getInstance().getTransID());
                databaseReference.child(Singleton.getInstance().getTransID()).setValue(trans);
          // after adding this data we are showing toast message.
                Log.i("AddTranscation:", trans.toString());
                Toast.makeText(AddTransaction.this, "data added", Toast.LENGTH_SHORT).show();

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