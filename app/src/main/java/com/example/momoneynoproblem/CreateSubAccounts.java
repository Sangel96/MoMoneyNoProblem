package com.example.momoneynoproblem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.momoneynoproblem.UserSignUp.UserSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
    public Button submitButton;
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

        submitButton = (Button) findViewById(R.id.createSubAccountButton);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting text from our edittext fields.
                String subAccountName = subAccountNameInput.getText().toString().trim();
                String reasonSubAccount = reasonsForSubAccountInput.getText().toString().trim();
//                String address = employeeAddressEdt.getText().toString();

                // below line is for checking weather the
                // edittext fields are empty or not.
                if (TextUtils.isEmpty(subAccountName) && TextUtils.isEmpty(reasonSubAccount)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(CreateSubAccounts.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(subAccountName,reasonSubAccount);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance(); //Obtaining an instance of FireBase to be used later
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
                System.exit(0);
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