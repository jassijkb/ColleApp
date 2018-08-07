package com.example.jaspreetbhui.cetclg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener,  AdapterView.OnItemSelectedListener {
    private Spinner yearspin,batchspin,branchspin;
    String[] branches={"Computer Science","Electrical","Electronics","Civil","Ceremics","Mechanical"};
    SharedPreferences sharedPreferences;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences(StaffLogin.Staff,MODE_PRIVATE);
        uname=sharedPreferences.getString("uname",null);
        yearspin = (Spinner) findViewById(R.id.yearspin);
        batchspin = (Spinner) findViewById(R.id.batch);
        branchspin = (Spinner) findViewById(R.id.branch);
        branchspin.setOnItemSelectedListener(this);
        yearspin.setOnItemSelectedListener(this);
        batchspin.setOnItemSelectedListener(this);
        List<String> year = new ArrayList<String>();
        List<String> batch = new ArrayList<String>();
        year.add("Year");
        year.add("first");
        year.add("Second");
        year.add("Third");
        year.add("fourth");
        batch.add("class");
        batch.add("I");
        batch.add("II");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spin1, year);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,R.layout.spin1, batch);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,R.layout.spin1, branches);
        dataAdapter.setDropDownViewResource(R.layout.spin1);
        dataAdapter2.setDropDownViewResource(R.layout.spin1);
        dataAdapter3.setDropDownViewResource(R.layout.spin1);
        yearspin.setAdapter(dataAdapter);
        batchspin.setAdapter(dataAdapter2);
        branchspin.setAdapter(dataAdapter3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void show(View V){
        Intent i = new Intent(getApplicationContext(),StList.class);
        Bundle b = new Bundle();
        b.putString("branch", branchspin.getSelectedItem().toString() );
        b.putString("year",yearspin.getSelectedItem().toString());
        b.putString("batch",batchspin.getSelectedItem().toString());
       i.putExtras(b);
      /*   i.putExtra();
        i.putExtra();*/
        startActivity(i);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.NoticeSend) {
            startActivity(new Intent(getApplicationContext(),StaffNoticeBoard.class));
            finish();

        } else if (id == R.id.mail) {
            startActivity(new Intent(getApplicationContext(),Email.class));
            finish();


        } else if (id == R.id.note) {
            startActivity(new Intent(getApplicationContext(),Notes.class));
            finish();

        } else if (id == R.id.settings) {

        } else if (id == R.id.logou) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(getBaseContext(), StaffLogin.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    }