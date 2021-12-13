package com.example.momoneynoproblem.FinAnalysis;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

public class DataOverview extends AppCompatActivity {
    TextView t1,t2,t3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_overview);

        //Declare and initializing t buttons
        t1 = (TextView) findViewById(R.id.result1);
        t2  = (TextView) findViewById(R.id.textView17);
        t3  = (TextView) findViewById(R.id.textView14);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query q = databaseReference.child("Transactions");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double total = 0;

                //Loop through firebase to retrieve amount
                for (DataSnapshot ds: snapshot.getChildren()) {
                    String amount = ds.child("amount").getValue(String.class);
                    total += Double.parseDouble(amount);
                    Log.d("Debug", "String amount: " + amount);
                }
                t1.setTextColor(Color.BLACK);
                t2.setTextColor(Color.BLACK);
                t3.setTextColor(Color.BLACK);

                t1.setText("$" + String.format("%.2f", total));
                double weekly = total/4;
                t2.setText("$" + String.format("%.2f", weekly));
                t3.setText("Travel,\n" +
                        "Shopping,\n" +
                        "Rent,\n" +
                        "Sports,\n" +
                        "Other\n");
                //Set color of Weekly/Monthly and categories to grey to differentiate
                t1.setTextColor(Color.GRAY);
                t2.setTextColor(Color.GRAY);
                t3.setTextColor(Color.GRAY);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", "Database Error detected: " + error.getCode());
            }
        });
    }
}
