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

public class ManageTransaction extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    public Button delete_Button = null;
    public Button modify_Button = null;
    public Button search_Button = null;

    //from show data
    ListView listshow;
    Button btnUpdate;
    ArrayList<String> arrList = new ArrayList<>();
    ArrayAdapter<String> arrAdp;

    final int[] selection = new int[1];

    //end show data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_transaction);
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Transactions");
        modify_Button = (Button) findViewById(R.id.modify_Button);
        delete_Button = (Button) findViewById(R.id.delete_Button);
        search_Button = (Button) findViewById(R.id.search_Button);


        // when click on the search button, go to the search screen
        search_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(ManageTransaction.this, searchForTransaction.class);
                startActivity(j);
            }
        });

        modify_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(ManageTransaction.this, modifyTransaction.class);
                startActivity(j);
            }
        });

        delete_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(ManageTransaction.this, deleteTransaction.class);
                startActivity(j);
            }
        });

        mAuth = FirebaseAuth.getInstance();  //Obtaining an instance of FireBase to be used later
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
                Toast.makeText(ManageTransaction.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }


}