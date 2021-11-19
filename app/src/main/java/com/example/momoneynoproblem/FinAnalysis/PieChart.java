package com.example.momoneynoproblem.FinAnalysis;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class PieChart extends AppCompatActivity {
    private List<SliceValue> transactionData;
    private ArrayList arrayList;
    private PieChartView pieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initial view screen
        setContentView(R.layout.activity_pie_chart);

        //Variable declaration for respective data types
        List<SliceValue> transactionData = new ArrayList<>();
        PieChartView pieChartView = findViewById(R.id.chartView);


        //Monthly Sample Values
        //Food/Groceries
        transactionData.add(new SliceValue(1332, Color.GRAY).setLabel("Travel: $1332"));

        //Travel
        transactionData.add(new SliceValue(255, Color.RED).setLabel("Shopping: $255"));

        //Shopping
        transactionData.add(new SliceValue(123, Color.BLUE).setLabel("Rent: $1500"));

        //Salary
        transactionData.add(new SliceValue(3253, Color.YELLOW).setLabel("Sports: $3253"));

        //Other
        transactionData.add(new SliceValue(1439, Color.GREEN).setLabel("Other: $1439"));

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
