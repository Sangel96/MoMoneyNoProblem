package com.example.momoneynoproblem.Notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.momoneynoproblem.DescribeBudgetAnalysis.AskSymptoms;
import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Report.Report;
import com.example.momoneynoproblem.SubAccount.SelectSubAccount;
import com.example.momoneynoproblem.SubAccount.SubAccount;
import com.example.momoneynoproblem.Transaction.ManageTransaction;
import com.example.momoneynoproblem.balance.account_balance;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsMainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_main_page);
        FirebaseDatabase.getInstance().getReference().child("Transactions")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String transaction_number;
                        transaction_number = String.valueOf(dataSnapshot.getChildrenCount());
                        final TextView helloTextView = (TextView) findViewById(R.id.recent_transactions_notifications);
                        helloTextView.setText(transaction_number);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        FirebaseDatabase.getInstance().getReference().child("reports")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String transaction_number;
                        transaction_number = String.valueOf(dataSnapshot.getChildrenCount());
                        final TextView helloTextView = (TextView) findViewById(R.id.recent_reports_notifications);
                        helloTextView.setText(transaction_number);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        FirebaseDatabase.getInstance().getReference().child("Balance")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String transaction_number;
                        transaction_number = String.valueOf(dataSnapshot.getChildrenCount());
                        final TextView helloTextView = (TextView) findViewById(R.id.balance_updates_notifications);
                        helloTextView.setText(transaction_number);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        // if they click on recent transactions
        ImageView recentTransaction = findViewById(R.id.recent_transactions); //Initializes Emergency Button
        recentTransaction.setOnClickListener(view -> {
            startActivity(new Intent(this, ManageTransaction.class));
        });

        // if they click on recent report
        ImageView recentReport = findViewById(R.id.recent_reports); //Initializes Emergency Button
        recentReport.setOnClickListener(view -> {
            startActivity(new Intent(this, Report.class));
        });

        // if they click on balance updates
        ImageView balanceUpdates = findViewById(R.id.balance_updates); //Initializes Emergency Button
        balanceUpdates.setOnClickListener(view -> {
            startActivity(new Intent(this, account_balance.class));
        });

        // Recent Reports
        // number of recent reports
    }
}