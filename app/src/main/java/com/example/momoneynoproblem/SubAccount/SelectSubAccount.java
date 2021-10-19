package com.example.momoneynoproblem.SubAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.momoneynoproblem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectSubAccount extends AppCompatActivity {
    String[] subAccountArray = new String[]{};
//    public Button submitButton;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
//    public ArrayList<String> subAccountArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sub_account);
        FirebaseDatabase.getInstance().getReference().child("SubAccounts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String[] subAccountArray = new String[]{};
                        ArrayList<String> subAccountArrayList = new ArrayList<>();
//                      ArrayList<String> subAccountArrayList = new ArrayList<String>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            SubAccount subAccount = new SubAccount();
                            subAccount = snapshot.getValue(SubAccount.class);
//                            Toast.makeText(SelectSubAccount.this, subAccount.subAccountName, Toast.LENGTH_SHORT).show();

                            subAccountArrayList.add(subAccount.getSubAccountName());


                        }
                        subAccountArray = subAccountArrayList.toArray(subAccountArray);
                        Spinner s = (Spinner) findViewById(R.id.SubAccountSpinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectSubAccount.this, android.R.layout.simple_spinner_item, subAccountArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
//        String[] subAccountArray = new String[]{};
//        Object[] subAccountArrayObject = subAccountArrayList.toArray();
//        Toast.makeText(SelectSubAccount.this, subAccountArrayList.get(0), Toast.LENGTH_SHORT).show();
//        String[] subAccountArray = Arrays.copyOf(subAccountArrayObject, subAccountArrayObject.length, String[].class);
//        System.out.println(subAccountArray);
//        subAccountArray = new String[]{"0", "1"};
        Button submitButton = new Button(this);
        submitButton = (Button) findViewById(R.id.selectSubAccountButton);

//
          submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner s = (Spinner) findViewById(R.id.SubAccountSpinner);
                String selectedItem = s.getSelectedItem().toString();
                openNewActivity(selectedItem);
            }
        });

    }

    private void openNewActivity(String selectedItem) {
        Intent intent = new Intent(this, SubAccountTemplate.class);
//        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = selectedItem;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}