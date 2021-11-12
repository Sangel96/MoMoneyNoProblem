package com.example.momoneynoproblem.Report;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.PDF.PDFViewer;
import com.example.momoneynoproblem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ListView listView;
        listView = (ListView) findViewById(R.id.listView);
        final int[] Selection = new int[1];

        Button btnLoadReport = (Button) findViewById(R.id.PDFMaker_Button);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User_Report"); //create a reference to generated reports on firebase

        ArrayList<String> Report = new ArrayList<>();
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(Report.this, android.R.layout.simple_list_item_1, Report);
        listView.setAdapter(ArrayAdapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Report.clear();
                for (DataSnapshot temp : snapshot.getChildren()){
                    Report.add("Report for: " + temp.getValue().toString());
                }
                ArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //onclick item listener to select which report to open in PDFViewer
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Selection[0] = position;
                String text = "position: " + Selection[0];
                Toast.makeText(Report.this, text, Toast.LENGTH_LONG).show();
            }
        });

        btnLoadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this, PDFViewer.class);
                intent.putExtra("PDFtoOpen",listView.getItemIdAtPosition(Selection[0]));
                startActivity(intent);
            }
        });





    }
}

