package com.example.momoneynoproblem.FinAnalysis;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.Login.ForgotPassword;
import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.UserSignUp.UserSignUp;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class FinAnalysisMenu extends AppCompatActivity {

    private ImageView graphBtn, pieChartBtn, dataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initial Screen
        setContentView(R.layout.activity_fin_analysis_menu);

        //container of ImageView type declaration
        graphBtn = (ImageView) findViewById(R.id.graph);
        pieChartBtn = (ImageView) findViewById(R.id.pieChart);
        dataBtn = (ImageView) findViewById(R.id.dOverviewText);

        //adds functionality graph Button which takes user to graph
        //analysis
        graphBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, FinAnalysis.class));
        });

        //adds functionalitiy for pie graph which takes user to
        //pie chart analysis page
        pieChartBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, PieChart.class));
        });

        dataBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, DataOverview.class));
        });
    }
}
