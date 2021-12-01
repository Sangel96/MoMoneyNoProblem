package com.example.momoneynoproblem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.momoneynoproblem.Goals.AddGoals;
import com.example.momoneynoproblem.Login.Login;
import com.example.momoneynoproblem.Report.Report;
import com.example.momoneynoproblem.SubAccount.SubAccountMainMenu;
import com.example.momoneynoproblem.Transaction.transaction;
import com.example.momoneynoproblem.balance.account_balance;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    private Button btnLogout;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    public static String user_ID;
    TextView account_name;
    TextView account_email;
    private Button datePicker;
    private TextView selectedDateText;
    DatabaseReference databaseReference;
    ArrayList<Double> list = new ArrayList<>();
    private TextView amount;
    private TextView balance;
    String date;
    SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy", Locale.US);
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //current user
        mAuth = FirebaseAuth.getInstance();


        //change name


        //show toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Singleton user = Singleton.getInstance();
        Toast.makeText(this, Singleton.getInstance().getUserID(), Toast.LENGTH_SHORT).show();
//        if (mAuth.getCurrentUser().getUid() != null) {
//            //user_ID = mAuth.getCurrentUser().getUid();
//            //Log.i("Main Activity: ",mAuth.getCurrentUser().getUid().toString());
//            user.setUserID(mAuth.getCurrentUser().getUid());
//
//        }
        //drawer layout
        drawer = findViewById(R.id.drawer_layout);

        //toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        account_name = (TextView) headerView.findViewById(R.id.account_name);
        account_email = (TextView) headerView.findViewById(R.id.account_email);
        account_name.setText(mAuth.getCurrentUser().getDisplayName());
        account_email.setText(mAuth.getCurrentUser().getEmail());

        balance = (TextView) findViewById(R.id.balance);
        //balance
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = mAuth.getCurrentUser().getEmail();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String db_email = ds.child("email").getValue(String.class);
                    Log.i("db_email", db_email);
                    Log.i("email", email);
                    if (db_email.equals(email)){
                        Long db_balance = ds.child("balance").getValue(Long.class);
                        //Log.i("balance", Long.parseLong(db_balance));
                        balance.setText("Balance: \n" + "$" + db_balance);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //calendar
        datePicker = (Button) findViewById(R.id.date_picker);
        selectedDateText = (TextView) findViewById(R.id.selectedDate);
        amount = (TextView) findViewById(R.id.amount);
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC)"));
        calendar.clear();

        long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);

        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        Long january = calendar.getTimeInMillis();

        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        Long december = calendar.getTimeInMillis();

        //calendar constraints
        CalendarConstraints.Builder constraints = new CalendarConstraints.Builder();
        constraints.setStart(january);
        constraints.setEnd(december);


        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
        builder.setSelection(today);
        builder.setCalendarConstraints(constraints.build());
        MaterialDatePicker materialDatePicker = builder.build();


        //MaterialDatePicker
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                calendar.add(Calendar.DATE, 1);
                date = format.format(calendar.getTime());
                selectedDateText.setText("Selected Date: " + date);

                databaseReference = FirebaseDatabase.getInstance().getReference();
                Query q = databaseReference.child("Transactions").orderByChild("date").
                        equalTo(date);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long total = 0;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String db_amount = ds.child("amount").getValue(String.class);
                            total += Long.parseLong(db_amount);
                        }
                        amount.setText("Expenses: " + total);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    //hamburger icon
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    //inflate the menu - adds items ot the action bar if it is present
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_goals) {
            startActivity(new Intent(MainActivity.this, AddGoals.class));
        } else if (id == R.id.nav_transactions) {
            startActivity(new Intent(MainActivity.this, transaction.class));
        } else if (id == R.id.nav_accounts) {
            startActivity(new Intent(MainActivity.this, SubAccountMainMenu.class));
        } else if (id == R.id.nav_balance) {
            startActivity(new Intent(MainActivity.this, account_balance.class));
        } else if (id == R.id.nav_report) {
            startActivity(new Intent(MainActivity.this, Report.class));
        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
            //Singleton.getInstance().reset();
            startActivity(new Intent(MainActivity.this, Login.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, Login.class));
        }
    }
}