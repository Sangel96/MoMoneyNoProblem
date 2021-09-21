package com.example.momoneynoproblem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.momoneynoproblem.Login.Login;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private Button btnLogout;
//    private ArrayList<SourceType>mSourceList;
//    private SourceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initList();

//        Spinner spinnerSources= findViewById(R.id.spinner);
//        mAdapter = new SourceAdapter(this,mSourceList);

//        spinnerSources.setAdapter(mAdapter);
//        spinnerSources.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                                     @Override
//                                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                                         SourceType ClickedItem = (SourceType) parent.getItemAtPosition(position);
//                                                         String clickedSourceType = ClickedItem.getsourceName();
//                                                         Toast.makeText(MainActivity.this,clickedSourceType+ " selected",
//                                                                 Toast.LENGTH_SHORT).show();

//                                                     }

//                                                     @Override
//                                                     public void onNothingSelected(AdapterView<?> parent) {

//                                                     }
//                                                 });


                btnLogout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, Login.class));
        });
    }
//    private void initList(){
//        mSourceList= new ArrayList<>();

//        mSourceList.add(new SourceType("Salary"));
//        mSourceList.add(new SourceType("Rent"));
//        mSourceList.add(new SourceType("Clothes"));
//        mSourceList.add(new SourceType("Travel"));
//        mSourceList.add(new SourceType("Food"));
//    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, Login.class));
    }
}

    //register = (TextView) findViewId(R.id.register);
    //register.setOnClickListener(this);


}