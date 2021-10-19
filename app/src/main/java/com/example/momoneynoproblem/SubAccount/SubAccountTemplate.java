package com.example.momoneynoproblem.SubAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.momoneynoproblem.R;

public class SubAccountTemplate extends AppCompatActivity {
    public TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_account_template);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(SelectSubAccount.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
//        TextView textView = new TextView();
//        textView = findViewById(R.id.selection_Sub_Account);
        textView.setText(message);
    }
}