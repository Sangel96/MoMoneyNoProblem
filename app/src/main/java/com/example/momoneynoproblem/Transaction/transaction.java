package com.example.momoneynoproblem.Transaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Singleton;
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

public class transaction extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] subAccountArray = new String[]{};
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public DatabaseReference firebaseDatabase;
    public DatabaseReference databaseReference;

    Button add_transaction;
    Button manage_transaction;
    //private Object transaction;

    // Spinner for select account
    // public Spinner SubAccountSpinner = null;
    //public static final ArrayList<String> SubAccounts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        add_transaction =(Button) findViewById(R.id.add_transaction);
        manage_transaction =  (Button) findViewById(R.id.manage_transaction);
        //Spinner SubAccountSpinner = (Spinner) findViewById((R.id.SubAccountSpinner));



        FirebaseDatabase.getInstance().getReference().child("SubAccounts")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        //String[] subAccountArray = new String[]{};
                        ArrayList<String> subAccountArrayList = new ArrayList<>();

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            if (snapshot1.child("user_ID").getValue(String.class).compareTo(Singleton.getInstance().getUserID()) == 0) {
                                SubAccount subAccount = new SubAccount();
                                subAccount = snapshot1.getValue(SubAccount.class);
                                assert subAccount != null;
                                subAccountArrayList.add(subAccount.getSubAccountName());
                            }
                        }
                        subAccountArray = subAccountArrayList.toArray(subAccountArray);
                        Spinner SubAccountSpinner = (Spinner) findViewById((R.id.SubAccountSpinner));
                        ArrayAdapter<String> accountsAdapter = new ArrayAdapter<String>(transaction.this,
                                android.R.layout.simple_spinner_item, subAccountArray);

                        accountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        SubAccountSpinner.setAdapter(accountsAdapter);
                        SubAccountSpinner.setOnItemSelectedListener(transaction.this);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        add_transaction.setOnClickListener(v -> {
            Intent j = new Intent(transaction.this, AddTransaction.class);
            startActivity(j);
        });

        manage_transaction.setOnClickListener(v -> {
            Intent i= new Intent(transaction.this, ManageTransaction.class);
            startActivity(i);

        });
    }


    // To show the selected item in the spinner at the bottom of the screen
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

