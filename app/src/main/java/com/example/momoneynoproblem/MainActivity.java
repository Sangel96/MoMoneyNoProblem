package com.example.momoneynoproblem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.momoneynoproblem.Goals.AddGoals;
import com.example.momoneynoproblem.Login.Login;
import com.example.momoneynoproblem.SubAccount.SelectSubAccount;
import com.example.momoneynoproblem.Transaction.transaction;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    private Button btnLogout;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //show toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //current user
        mAuth = FirebaseAuth.getInstance();

        //drawer layout
        drawer = findViewById(R.id.drawer_layout);

        //toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
    public boolean onCreateOptionsMenu(Menu menu){
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
            startActivity(new Intent(MainActivity.this, SelectSubAccount.class));
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