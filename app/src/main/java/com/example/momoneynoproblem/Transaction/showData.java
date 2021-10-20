package com.example.momoneynoproblem.Transaction;

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

import com.example.momoneynoproblem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showData extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public DatabaseReference firebaseDatabase;
    public DatabaseReference databaseReference;
    ListView listshow;
    Button btnUpdate;
    ArrayList<String> arrList = new ArrayList<>();
    ArrayAdapter<String> arrAdp;
    //Module model;
    final int[] selection = new int[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        //model = ((Module)getApplicationContext());
        databaseReference = FirebaseDatabase.getInstance().getReference("Transactions");
        listshow = (ListView) findViewById(R.id.Listviewtxt);
        btnUpdate= (Button)findViewById(R.id.btnUpdate);
        arrAdp = new ArrayAdapter<String>(this, android.R.layout
                .simple_list_item_1,arrList);
        listshow.setAdapter(arrAdp);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrList.clear();
                for (DataSnapshot temp : snapshot.getChildren()){
                    arrList.add("Transaction: " + temp.getValue().toString());
                    }

                arrAdp.notifyDataSetChanged();

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listshow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selection[0] = position;
                String text = "position: " + selection[0];
                Toast.makeText(showData.this, text, Toast.LENGTH_LONG).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showData.this, modifyTransaction.class);
                intent.putExtra("Transaction",listshow.getItemIdAtPosition(selection[0]));
                startActivity(intent);
            }
        });

    }
}