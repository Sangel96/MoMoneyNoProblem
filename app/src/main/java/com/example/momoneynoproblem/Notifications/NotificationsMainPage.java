package com.example.momoneynoproblem.Notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.momoneynoproblem.DescribeBudgetAnalysis.AskSymptoms;
import com.example.momoneynoproblem.Goals.CreateGoal;
import com.example.momoneynoproblem.Goals.Goal;
import com.example.momoneynoproblem.R;
import com.example.momoneynoproblem.Report.Report;
import com.example.momoneynoproblem.SubAccount.SelectSubAccount;
import com.example.momoneynoproblem.SubAccount.SubAccount;
import com.example.momoneynoproblem.Transaction.ManageTransaction;
import com.example.momoneynoproblem.Transaction.Transaction1;
import com.example.momoneynoproblem.balance.account_balance;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class NotificationsMainPage extends AppCompatActivity {
    DataSnapshot transactionSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_main_page);
//        createNotificationChannel("test");
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"test" )
//                .setSmallIcon(R.drawable.ic_money_background)
//                .setContentTitle("Creation")
//                .setContentText("Testing")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);



// notificationId is a unique int for each notification that you must define
//        notificationManager.notify(0123456, builder.build());
        FirebaseDatabase.getInstance().getReference().child("Transactions")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String transaction_number;
//                        transactionSnapshot = dataSnapshot;
                        transaction_number = String.valueOf(dataSnapshot.getChildrenCount());
                        final TextView helloTextView = (TextView) findViewById(R.id.recent_transactions_notifications);
                        helloTextView.setText(transaction_number);
                        int count = 0;
                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
//                            User user1 = userSnapshot.getValue(User.class);
                            HashMap tr = (HashMap) userSnapshot.getValue();
                            String transactionID = "Transaction" + tr.toString();

                            String transactionText = "Transaction " + tr.get("date") ;
                            createNotificationChannel(transactionID);
                            String transactionDescription = "$" + tr.get("amount") + " " + tr.get("storeName") + ".";
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),transactionID )
                                    .setSmallIcon(R.drawable.ic_money_background)
                                    .setContentTitle(transactionText)
                                    .setContentText(transactionDescription)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            // Create an Intent for the activity you want to start
                            Intent resultIntent = new Intent(getApplicationContext(), ManageTransaction.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                            stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
                            PendingIntent resultPendingIntent =
                                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(resultPendingIntent);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                            Random random = new Random();
                            int rand = random.nextInt(1000);
                            notificationManager.notify(rand, builder.build());


                            count++;
                            if (count < 3) {
                                continue;
                            }
                            break;
                        }
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
//                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
////                            User user1 = userSnapshot.getValue(User.class);
//                            UserReport rt = (Report) userSnapshot.getValue();
//                            String transactionID = "Transaction" + rt.;
//
//                            String transactionText = "Transaction" + transactionID;
//                            createNotificationChannel(transactionID);
//                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),transactionID )
//                                    .setSmallIcon(R.drawable.ic_money_background)
//                                    .setContentTitle(transactionText)
//                                    .setContentText(dataSnapshot.getValue().toString())
//                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
//                            Random random = new Random();
//                            int rand = random.nextInt(1000);
//                            notificationManager.notify(rand, builder.build());
//                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        FirebaseDatabase.getInstance().getReference().child("Goals")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String transaction_number;
                        transaction_number = String.valueOf(dataSnapshot.getChildrenCount());
                        final TextView helloTextView = (TextView) findViewById(R.id.balance_updates_notifications);
                        helloTextView.setText(transaction_number);
                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
//                            User user1 = userSnapshot.getValue(User.class);
                            HashMap gl = (HashMap) userSnapshot.getValue();
                            String goalID = "Goal" + gl.toString();

                            String goalText = "Goal " + gl.get("date");
                            String goalDescription = "Monthly Limit: $" + gl.get("monthlyLimit");
                            createNotificationChannel(goalID);
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),goalID)
                                    .setSmallIcon(R.drawable.ic_money_background)
                                    .setContentTitle(goalText)
                                    .setContentText(goalDescription)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            // Create an Intent for the activity you want to start
                            Intent resultIntent = new Intent(getApplicationContext(), CreateGoal.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                            stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
                            PendingIntent resultPendingIntent =
                                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(resultPendingIntent);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                            Random random = new Random();
                            int rand = random.nextInt(1000);
                            notificationManager.notify(rand, builder.build());
                        }
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

    private void createNotificationChannel(String channelID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Test";
            String description = "Test";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}