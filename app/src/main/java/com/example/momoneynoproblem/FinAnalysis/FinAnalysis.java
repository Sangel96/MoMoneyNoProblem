package com.example.momoneynoproblem.FinAnalysis;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class FinAnalysis extends AppCompatActivity {
    GraphView graphView;
    DatabaseReference databaseReference;
    ArrayList<Integer> arrayList = new ArrayList<>();
    LineGraphSeries<DataPoint> series;
    ArrayList<Double> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_analysis);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference ref = database.getReference("https://a-690e5-default-rtdb.firebaseio.com/Transactions");

        databaseReference = FirebaseDatabase.getInstance().getReference("Transactions");
        graphView = findViewById(R.id.idGraphView);

        // Attach a listener to read the data at our posts reference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // TransactionData t = dataSnapshot.getValue(TransactionData.class);

                list.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Double amount = Double.parseDouble(ds.child("amount").getValue(String.class));
                    list.add(amount);
                }
                DataPoint[] dp = new DataPoint[29];
                for (int i = 0; i < 29; ++i) {
                    dp[i] = new DataPoint(i, list.get(i));
                }
                series = new LineGraphSeries<>(dp);

                graphView.setTitle("Monthly Graph Analysis");

                 graphView.setTitleColor(R.color.black);

                graphView.setTitleTextSize(26);

                graphView.addSeries(series);

                graphView.getViewport().setMinX(1);
                graphView.getViewport().setMaxX(30);
                graphView.getViewport().setMinY(0.0);
                graphView.getViewport().setMaxY(1500.0);

                graphView.getViewport().setYAxisBoundsManual(true);
                graphView.getViewport().setXAxisBoundsManual(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

       // graphView.addSeries(series);
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(1, 72),
//                new DataPoint(2, 99),
//                new DataPoint(3, 27),
//                new DataPoint(4, 56),
//                new DataPoint(5, 23),
//                new DataPoint(6, 18),
//                new DataPoint(7, 52),
//                new DataPoint(8, 55),
//                new DataPoint(9, 73),
//                new DataPoint(10, 72),
//                new DataPoint(11, 99),
//                new DataPoint(12, 27),
//                new DataPoint(13, 56),
//                new DataPoint(14, 23),
//                new DataPoint(15, 18),
//                new DataPoint(16, 52),
//                new DataPoint(17, 55),
//                new DataPoint(18, 73),
//                new DataPoint(19, 72),
//                new DataPoint(20, 99),
//                new DataPoint(21, 27),
//                new DataPoint(22, 56),
//                new DataPoint(23, 23),
//                new DataPoint(24, 18),
//                new DataPoint(25, 52),
//                new DataPoint(26, 55),
//                new DataPoint(27, 39),
//                new DataPoint(28, 45),
//                new DataPoint(29, 63),
//                new DataPoint(30, 78),
//                new DataPoint(31, 100),
//        });


    }
}
