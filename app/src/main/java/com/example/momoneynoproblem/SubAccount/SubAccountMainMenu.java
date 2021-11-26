package com.example.momoneynoproblem.SubAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Transaction.AddTransaction;
import com.example.momoneynoproblem.Transaction.transaction;

public class SubAccountMainMenu extends AppCompatActivity {
    ImageView addSubAccount;
    ImageView manageSubAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_account_main_menu);
        addSubAccount = (ImageView) findViewById(R.id.sub_account_management_text_subaccountmainemu);
        manageSubAccount = (ImageView) findViewById(R.id.subaccountmainmenu_sub_account_green_button_text);

        addSubAccount.setOnClickListener(v -> {
            Intent j = new Intent(SubAccountMainMenu.this, CreateSubAccounts.class);
            startActivity(j);
        });
        manageSubAccount.setOnClickListener(v -> {
            Intent i = new Intent(SubAccountMainMenu.this, SelectSubAccount.class);
            startActivity(i);
        });

    }
}