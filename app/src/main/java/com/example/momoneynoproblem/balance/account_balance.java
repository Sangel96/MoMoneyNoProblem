package com.example.momoneynoproblem.balance;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class account_balance extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_balance);
        ListView listView;
        listView = (ListView) findViewById(R.id.listView);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

        ArrayList<String> Account_Balance = new ArrayList<>();
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(account_balance.this, android.R.layout.simple_list_item_1, Account_Balance);
        listView.setAdapter(ArrayAdapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account_Balance.clear();
                for (DataSnapshot temp : snapshot.getChildren()){
                    Account_Balance.add("Account Balance: " + temp.getValue().toString());

                }
                ArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}