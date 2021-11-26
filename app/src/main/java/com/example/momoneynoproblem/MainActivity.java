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

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.momoneynoproblem.Goals.AddGoals;
import com.example.momoneynoproblem.Login.Login;
import com.example.momoneynoproblem.Report.Report;
import com.example.momoneynoproblem.SubAccount.SelectSubAccount;
import com.example.momoneynoproblem.SubAccount.SubAccountMainMenu;
import com.example.momoneynoproblem.Transaction.transaction;
import com.example.momoneynoproblem.balance.account_balance;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    private Button btnLogout;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    public static String user_ID;
    TextView account_name;
    TextView account_email;
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



        if (mAuth.getCurrentUser().getUid() != null) {
            user_ID = mAuth.getCurrentUser().getUid();
            Toast.makeText(this, user_ID, Toast.LENGTH_SHORT).show();
        }
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