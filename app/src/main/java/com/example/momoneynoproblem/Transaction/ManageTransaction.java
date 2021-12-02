package com.example.momoneynoproblem.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Singleton;
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



    ListView listshow;
    ArrayList<String> arrList = new ArrayList<>();
    ArrayAdapter<String> arrAdp;

    final int[] selection = new int[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_transaction);

        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Transactions");
        modify_Button = (Button) findViewById(R.id.modify_Button);
        delete_Button = (Button) findViewById(R.id.delete_Button);

        modify_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(ManageTransaction.this, modifyTransaction.class);
                j.putExtra("Transaction",listshow.getItemIdAtPosition(selection[0]));
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

        arrAdp = new ArrayAdapter<String>(this, android.R.layout
                .simple_list_item_1,arrList);
        listshow.setAdapter(arrAdp);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrList.clear();
                for (DataSnapshot temp : snapshot.getChildren()){

                    if (temp.child("accountId").getValue(String.class).compareTo(Singleton.getInstance().getUserID()) == 0) {
                        String transID = temp.child("transID").getValue(String.class);
                        String accountId = temp.child("accountId").getValue(String.class);
                        String transaction_type = temp.child("transaction_type").getValue(String.class);
                        String date = temp.child("date").getValue(String.class);
                        String storeName = temp.child("storeName").getValue(String.class);
                        String transaction_source_type = temp.child("transaction_source_type").getValue(String.class);
                        String amount = temp.child("amount").getValue(String.class);


                        // arrList.add("Transaction ID: " + transID);
//                    arrList.add("Sub_Account ID: " + accountId + " / " + "transaction Type: "
//                            + transaction_type + " / " + "Date: " + date
//                            + " / " + "Store Name " + storeName + " / " + "Soruce Type: " + transaction_source_type + " / " + "Amount: " +  amount +"$");
                        arrList.add("Transaction ID: " + transID
                                + "\nDate: " + date
                                + "\n$Amount: " + amount
                                + "\nStore: " + storeName);

                        //arrList.add("--------------------------------------------------");
                        // arrList.add("Transaction: " + temp.getValue().toString());
                        Log.d("TAG", transID + " / " + accountId + " / " + transaction_type + " / " + date
                                + " / " + storeName + " / " + transaction_source_type + " / " + amount + "$");
                    }
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
                //Toast.makeText(ManageTransaction.this, text, Toast.LENGTH_LONG).show();
                //Log.i("Tag", "Position" + selection[0]);
            }
        });
    }
}

