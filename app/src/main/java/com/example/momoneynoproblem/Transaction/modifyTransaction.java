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

import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Singleton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class modifyTransaction extends AppCompatActivity {

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

    private static final String[] paths = {"Travel", "Shopping", "Sports", "Other", "Rent"};

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
       // EditAccountId = findViewById(R.id.EditAccountId);

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
                        //transaction_source_type = (String)
                               // parent.getItemAtPosition(position);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(modifyTransaction.this, "Please choose source type.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

        modifyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String ID = TranIDEdit.getText().toString().trim();
                //final String accountId = EditAccountId.getText().toString().trim();
                String amount = amountEditText.getText().toString().trim();
                String date = DateEdit.getText().toString().trim();
                String storename = StoreNameEdit.getText().toString().trim();
                String trans_type = transaction_type.trim();
                String trans_sourceType = transaction_source_type.trim();
                //String trans_sourceType = Singleton.getInstance().getTransType();

                String accountId = Singleton.getInstance().getUserID();


                if (TextUtils.isEmpty(ID)) {
                    TranIDEdit.setError("Please enter The Transaction ID!");
                } else
                    {
                    Transaction1 transaction1 = new Transaction1(amount,transaction_type, transaction_source_type,ID,date, storename,accountId);
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
            }
        });
        //Toast.makeText(modifyTransaction.this,"Your Data is Successfully Updated",Toast.LENGTH_SHORT).show();

    }
}



