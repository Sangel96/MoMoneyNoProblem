package com.example.momoneynoproblem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateSubAccounts extends AppCompatActivity {
    String subAccountName;
    String reasonsForSubAccount;

    EditText subAccountNameInput;
    EditText reasonsForSubAccountInput;

    Button submitButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_sub_accounts2);
        subAccountNameInput = (EditText) findViewById(R.id.subAccountNameInput);
        reasonsForSubAccountInput = (EditText) findViewById(R.id.reasonsSubAccount);

        submitButton = (Button) findViewById(R.id.createSubAccountButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subAccountName = subAccountNameInput.getText().toString();
                reasonsForSubAccount = reasonsForSubAccountInput.getText().toString();




            }
        });
    }
}