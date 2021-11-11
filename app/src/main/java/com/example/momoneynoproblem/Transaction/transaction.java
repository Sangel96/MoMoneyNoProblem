package com.example.momoneynoproblem.Transaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.SubAccount.SelectSubAccount;
import com.example.momoneynoproblem.SubAccount.SubAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class transaction extends AppCompatActivity {
    String[] subAccountArray = new String[]{};
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public DatabaseReference firebaseDatabase;
    public DatabaseReference databaseReference;

    ImageView add_transaction;
    ImageView manage_transaction;
    private Object transaction;

    // Spinner for select account
    public Spinner SubAccountSpinner = null;
    public static final ArrayList<String> SubAccounts = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        add_transaction = (ImageView) findViewById(R.id.add_transaction);
        manage_transaction = (ImageView) findViewById(R.id.manage_transaction);

        FirebaseDatabase.getInstance().getReference().child("SubAccounts")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //String[] subAccountArray = new String[]{};
                        ArrayList<String> subAccountArrayList = new ArrayList<>();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            SubAccount subAccount = new SubAccount();
                            subAccount = snapshot1.getValue(SubAccount.class);
                            subAccountArrayList.add(subAccount.getSubAccountName());
                        }
                        subAccountArray = subAccountArrayList.toArray(subAccountArray);
                        Spinner SubAccountSpinner = (Spinner) findViewById((R.id.SubAccountSpinner));
                        ArrayAdapter<String> accountsAdapter = new ArrayAdapter<String>(transaction.this,
                                android.R.layout.simple_spinner_item, subAccountArray);

                        accountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        SubAccountSpinner.setAdapter(accountsAdapter);

                    }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

        add_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(transaction.this, AddTransaction.class);
                startActivity(j);
            }
        });

        manage_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(transaction.this, ManageTransaction.class);
                startActivity(i);

            }
        });
    }
}

