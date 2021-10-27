package com.example.momoneynoproblem.FinAnalysis;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class FinAnalysis extends AppCompatActivity {
    GraphView graphView;
    DatabaseReference databaseReference;
    ArrayList<Integer> arrayList = new ArrayList<>();
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_analysis);

        databaseReference = FirebaseDatabase.getInstance().getReference("Transactions");

        graphView = findViewById(R.id.idGraphView);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 500),
                new DataPoint(1, 45),
                new DataPoint(2, 36),
                new DataPoint(3, 600),
                new DataPoint(4, 30),
                new DataPoint(5, 50),
                new DataPoint(6, 60),
                new DataPoint(7, 80),
                new DataPoint(8, 700),
        });

        graphView.setTitle("My Graph View");

        graphView.setTitleColor(R.color.black);

        graphView.setTitleTextSize(26);

        graphView.addSeries(series);
    }
}
