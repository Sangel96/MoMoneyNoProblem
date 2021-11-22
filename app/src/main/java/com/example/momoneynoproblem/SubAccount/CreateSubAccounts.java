package com.example.momoneynoproblem.SubAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.momoneynoproblem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateSubAccounts extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public String subAccountName;
    public String reasonsForSubAccount;
    public DatabaseReference mDatabase;
    public EditText subAccountNameInput;
    public EditText reasonsForSubAccountInput;
    public FirebaseDatabase firebaseDatabase;
    public ImageView submitButton;
    public DatabaseReference databaseReference;
    public SubAccount subAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sub_accounts2);
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("SubAccounts");
        subAccountNameInput = (EditText) findViewById(R.id.subAccountNameInput);
        reasonsForSubAccountInput = (EditText) findViewById(R.id.reasonsSubAccount);

        submitButton = (ImageView) findViewById(R.id.enterbuttontextaddsubaccount);


        submitButton.setOnClickListener(view -> {
            insertSubAccount();
        });

        mAuth = FirebaseAuth.getInstance(); //Obtaining an instance of FireBase to be used later
    }

    private void insertSubAccount() {
        subAccountNameInput = (EditText) findViewById(R.id.subAccountNameInput);
        reasonsForSubAccountInput = (EditText) findViewById(R.id.reasonsSubAccount);
        subAccountName = subAccountNameInput.getText().toString().trim();
        reasonsForSubAccount = reasonsForSubAccountInput.getText().toString().trim();
        SubAccount newSubAccount = new SubAccount(subAccountName,reasonsForSubAccount);
        databaseReference.push().setValue(newSubAccount);
        Toast.makeText(CreateSubAccounts.this, "Successfully added Sub-Account", Toast.LENGTH_SHORT).show();
    }

    private void addDatatoFirebase(String subAccountName, String reasonsForSubAccount) {
        // below 3 lines of code is used to set
        // data in our object class.
        SubAccount subAccount = new SubAccount();
//        System.out.println(subAccountName);
//        System.out.println(reasonsForSubAccount);
        subAccount.setSubAccountName(subAccountName);
        subAccount.setReasonsSubAccountName(reasonsForSubAccount);
        DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference().child("SubAccounts");
        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
//                SubAccount subAccount = new SubAccount(subAccountName, reasonsForSubAccount);
                DatabaseReference subAccountChild = databaseReference.push();
                databaseReference.child(subAccountChild.getKey()).setValue(subAccount);

                // after adding this data we are showing toast message.
                Toast.makeText(CreateSubAccounts.this, "data added", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(CreateSubAccounts.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }

        });
    }
}