package com.example.momoneynoproblem.SubAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.momoneynoproblem.MainActivity;
import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectSubAccount extends AppCompatActivity {
    String[] subAccountArray = new String[]{};
//    public Button submitButton;
    ImageView submitButton;
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
                        ArrayList<String> subAccountArrayList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("tag", "debug: " + snapshot.child("user_ID").getValue(String.class));
                            if (snapshot.child("user_ID").getValue(String.class).compareTo(Singleton.getInstance().getUserID()) == 0) {
                                SubAccount subAccount = new SubAccount();
                                subAccount = snapshot.getValue(SubAccount.class);
//                            Toast.makeText(SelectSubAccount.this, subAccount.subAccountName, Toast.LENGTH_SHORT).show();

                                subAccountArrayList.add(subAccount.getSubAccountName());

                            }
                        }
                        subAccountArray = subAccountArrayList.toArray(subAccountArray);
                        Spinner s = (Spinner) findViewById(R.id.SubAccountSpinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectSubAccount.this,
                                android.R.layout.simple_spinner_item, subAccountArray);
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
//        Button submitButton = new Button(this);
        submitButton = (ImageView) findViewById(R.id.enterbuttontextmanagesubaccount);

//
          submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner s = (Spinner) findViewById(R.id.SubAccountSpinner);
                String selectedItem = s.getSelectedItem().toString();
                Log.d("Tag", "DebugBefore: " + selectedItem);
                openNewActivity(selectedItem);
                Log.d("Tag", "DebugAfter: " + selectedItem);
            }
        });

    }

    private void openNewActivity(String selectedItem) {
        Intent intent = new Intent(this, MainActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = selectedItem;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}