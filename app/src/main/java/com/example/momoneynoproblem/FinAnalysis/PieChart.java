package com.example.momoneynoproblem.FinAnalysis;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class PieChart extends AppCompatActivity {
    private List<SliceValue> transactionData;
    private PieChartView pieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initial view screen
        setContentView(R.layout.activity_pie_chart);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Variable declaration for respective data types
        List<SliceValue> transactionData = new ArrayList<>();
        PieChartView pieChartView = findViewById(R.id.chartView);

       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        Query q = databaseReference.child("Transactions").orderByChild("transaction_source_type").
                equalTo("Travel");
        Query q1 = databaseReference.child("Transactions").orderByChild("transaction_source_type").
                equalTo("Shopping");
        Query q2 = databaseReference.child("Transactions").orderByChild("transaction_source_type").
                equalTo("Rent");
        Query q3 = databaseReference.child("Transactions").orderByChild("transaction_source_type").
                equalTo("Sports");
        Query q4 = databaseReference.child("Transactions").orderByChild("transaction_source_type").
                equalTo("Other");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long total = 0;

                for (DataSnapshot ds: snapshot.getChildren()) {
                    String amount = ds.child("amount").getValue(String.class);
                    total += Long.parseLong(amount);
                    Log.d("Debug", "String amount: " + amount);
                }
                Log.d("Debug", "Travel Total: " + total);
                transactionData.add(new SliceValue(total, Color.GRAY).setLabel("Travel: $" + total));   //"Travel: $1332

                //PieChartData class instance to pass data in
                PieChartData pieTransData = new PieChartData(transactionData);

                //Set pieChartVIew data to data of the instance of pieTransData variable
                pieChartView.setPieChartData(pieTransData);

                //Change label size of pie graph
                pieTransData.setHasLabels(true).setValueLabelTextSize(10);

                //Set title of centered text in the middle of the screen
                pieTransData.setHasCenterCircle(true).setCenterText1("Monthly Spending");

                // Set font size of the centered text in the middle of the screen
                pieTransData.setHasCenterCircle(true).setCenterText1FontSize(14);

                //assign data of pieTransData to pieChartView
                pieChartView.setPieChartData(pieTransData);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        q1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long total = 0;

                for (DataSnapshot ds: snapshot.getChildren()) {
                    String amount = ds.child("amount").getValue(String.class);
                    total += Long.parseLong(amount);
                    Log.d("Debug", "String amount: " + amount);
                }
                Log.d("Debug", "Travel Total: " + total);
                transactionData.add(new SliceValue(total, Color.RED).setLabel("Shopping: $" + total));   //"Travel: $1332

                //PieChartData class instance to pass data in
                PieChartData pieTransData = new PieChartData(transactionData);

                //Set pieChartVIew data to data of the instance of pieTransData variable
                pieChartView.setPieChartData(pieTransData);

                //Change label size of pie graph
                pieTransData.setHasLabels(true).setValueLabelTextSize(10);

                //Set title of centered text in the middle of the screen
                pieTransData.setHasCenterCircle(true).setCenterText1("Monthly Spending");

                // Set font size of the centered text in the middle of the screen
                pieTransData.setHasCenterCircle(true).setCenterText1FontSize(14);

                //assign data of pieTransData to pieChartView
                pieChartView.setPieChartData(pieTransData);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        q2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long total = 0;

                for (DataSnapshot ds: snapshot.getChildren()) {
                    String amount = ds.child("amount").getValue(String.class);
                    total += Long.parseLong(amount);
                    Log.d("Debug", "String amount: " + amount);
                }
                Log.d("Debug", "Travel Total: " + total);
                transactionData.add(new SliceValue(total, Color.BLUE).setLabel("Rent: $" + total));   //"Travel: $1332

                //PieChartData class instance to pass data in
                PieChartData pieTransData = new PieChartData(transactionData);

                //Set pieChartVIew data to data of the instance of pieTransData variable
                pieChartView.setPieChartData(pieTransData);

                //Change label size of pie graph
                pieTransData.setHasLabels(true).setValueLabelTextSize(10);

                //Set title of centered text in the middle of the screen
                pieTransData.setHasCenterCircle(true).setCenterText1("Monthly Spending");

                // Set font size of the centered text in the middle of the screen
                pieTransData.setHasCenterCircle(true).setCenterText1FontSize(14);

                //assign data of pieTransData to pieChartView
                pieChartView.setPieChartData(pieTransData);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        q3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long total = 0;

                for (DataSnapshot ds: snapshot.getChildren()) {
                    String amount = ds.child("amount").getValue(String.class);
                    total += Long.parseLong(amount);
                    Log.d("Debug", "String amount: " + amount);
                }
                Log.d("Debug", "Travel Total: " + total);
                transactionData.add(new SliceValue(total, Color.YELLOW).setLabel("Sports: $" + total));   //"Travel: $1332

                //PieChartData class instance to pass data in
                PieChartData pieTransData = new PieChartData(transactionData);

                //Set pieChartVIew data to data of the instance of pieTransData variable
                pieChartView.setPieChartData(pieTransData);

                //Change label size of pie graph
                pieTransData.setHasLabels(true).setValueLabelTextSize(10);

                //Set title of centered text in the middle of the screen
                pieTransData.setHasCenterCircle(true).setCenterText1("Monthly Spending");

                // Set font size of the centered text in the middle of the screen
                pieTransData.setHasCenterCircle(true).setCenterText1FontSize(14);

                //assign data of pieTransData to pieChartView
                pieChartView.setPieChartData(pieTransData);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        q4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long total = 0;

                for (DataSnapshot ds: snapshot.getChildren()) {
                    String amount = ds.child("amount").getValue(String.class);
                    total += Long.parseLong(amount);
                    Log.d("Debug", "String amount: " + amount);
                }
                Log.d("Debug", "Travel Total: " + total);
                transactionData.add(new SliceValue(total, Color.GREEN).setLabel("Other: $" + total));   //"Travel: $1332

                //PieChartData class instance to pass data in
                PieChartData pieTransData = new PieChartData(transactionData);

                //Set pieChartVIew data to data of the instance of pieTransData variable
                pieChartView.setPieChartData(pieTransData);

                //Change label size of pie graph
                pieTransData.setHasLabels(true).setValueLabelTextSize(10);

                //Set title of centered text in the middle of the screen
                pieTransData.setHasCenterCircle(true).setCenterText1("Monthly Spending");

                // Set font size of the centered text in the middle of the screen
                pieTransData.setHasCenterCircle(true).setCenterText1FontSize(14);

                //assign data of pieTransData to pieChartView
                pieChartView.setPieChartData(pieTransData);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //Monthly Sample Values
        //Food/Groceries
        //transactionData.add(new SliceValue(listInt.get(0), Color.GRAY).setLabel("Travel: $" + listInt.get(0)));   //"Travel: $1332

//        //Travel
//        transactionData.add(new SliceValue(255, Color.RED).setLabel("Shopping: $255"));
//
//        //Shopping
//        transactionData.add(new SliceValue(123, Color.BLUE).setLabel("Rent: $1500"));
//
//        //Salary
//        transactionData.add(new SliceValue(3253, Color.YELLOW).setLabel("Sports: $3253"));
//
//        //Other
//        transactionData.add(new SliceValue(1439, Color.GREEN).setLabel("Other: $1439"));

        //PieChartData class instance to pass data in
        PieChartData pieTransData = new PieChartData(transactionData);

        //Set pieChartVIew data to data of the instance of pieTransData variable
        pieChartView.setPieChartData(pieTransData);

        //Change label size of pie graph
        pieTransData.setHasLabels(true).setValueLabelTextSize(10);

        //Set title of centered text in the middle of the screen
        pieTransData.setHasCenterCircle(true).setCenterText1("Monthly Spending");

        // Set font size of the centered text in the middle of the screen
        pieTransData.setHasCenterCircle(true).setCenterText1FontSize(14);

        //assign data of pieTransData to pieChartView
        pieChartView.setPieChartData(pieTransData);

    }
}
