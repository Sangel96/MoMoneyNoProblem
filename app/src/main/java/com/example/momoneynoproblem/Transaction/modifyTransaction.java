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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Singleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class modifyTransaction extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    public RadioGroup radioGroup;
    public RadioButton incomeRadioButton;
    public RadioButton expenseRadioButton;
    public RadioButton radioSourceButton;

    public Button modify_Button = null;
    public Button delete_Button = null;


    public String transaction_type = "";
    public String transaction_source_type = "";
    public Spinner transactionSourceTypeSpinner;


    public EditText amountEditText = null;
    public EditText TranIDEdit = null;
    public EditText DateEdit = null;
    public EditText StoreNameEdit = null;

    private static final String[] paths = {"Travel", "Shopping", "Sports", "Other", "Rent"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_transaction);

        databaseReference = FirebaseDatabase.getInstance().getReference("Transactions");

        radioGroup = findViewById(R.id.radioGroup);
        incomeRadioButton = findViewById(R.id.incomeRadioButton);
        expenseRadioButton = findViewById(R.id.expenseRadioButton);
        modify_Button = (Button) findViewById(R.id.modify_Button);
        delete_Button = (Button) findViewById(R.id.delete_Button);

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
                        Singleton.getInstance().setTransType((String) parent.getItemAtPosition(position));

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(modifyTransaction.this, "Please choose source type.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
        DateEdit.setText(getIntent().getStringExtra("date"));
        StoreNameEdit.setText(getIntent().getStringExtra("storeName"));
        amountEditText.setText(getIntent().getStringExtra("amount"));
        TranIDEdit.setText(getIntent().getStringExtra("transID"));


        modify_Button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String ID = TranIDEdit.getText().toString().trim();
                String amount = amountEditText.getText().toString().trim();
                String date = DateEdit.getText().toString().trim();
                String storename = StoreNameEdit.getText().toString().trim();

                String trans_type = transaction_type.toString().trim();
                String trans_sourceType = transaction_source_type.toString().trim();

                String accountId = Singleton.getInstance().getUserID();


                    HashMap hashMap = new HashMap();
                    hashMap.put("amount", amount);
                    hashMap.put("date", date);
                    hashMap.put("storeName", storename);
                    hashMap.put("transID", ID);
                    hashMap.put("transaction_source_type", trans_sourceType);
                    hashMap.put("transaction_type", trans_type);
                    hashMap.put("accountId", accountId);

                    databaseReference.child(ID).setValue(hashMap);


            }
        });

        delete_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = TranIDEdit.getText().toString().trim();

                String amount = amountEditText.getText().toString().trim();
                String date = DateEdit.getText().toString().trim();
                String storename = StoreNameEdit.getText().toString().trim();
                String trans_type = transaction_type.trim();
                String trans_sourceType = transaction_source_type.trim();

                HashMap hashMap = new HashMap();
                hashMap.put("amount", amount);
                hashMap.put("date", date);
                hashMap.put("storeName", storename);
                hashMap.put("transID", ID);
                hashMap.put("transaction_source_type", trans_sourceType);
                hashMap.put("transaction_type", trans_type);
                hashMap.put("accountId", "-" + Singleton.getInstance().getUserID());

                databaseReference.child(ID).setValue(hashMap);


            }

        });

    }
}






