package com.example.jaspreetbhui.cetclg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffNoticeBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    private Spinner yearspin,semspin;
    EditText notice;
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.43.37/cetclg/noticeSend.php";
    private StringRequest request;
    SharedPreferences sharedPreferences;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_notice_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        yearspin = (Spinner) findViewById(R.id.year);
        semspin = (Spinner) findViewById(R.id.sem);
        notice=(EditText)findViewById(R.id.notice);
        yearspin.setOnItemSelectedListener(this);
        semspin.setOnItemSelectedListener(this);
        List<String> year = new ArrayList<String>();
        List<String> sem = new ArrayList<String>();
        year.add("Year");
        year.add("first");
        year.add("Second");
        year.add("Third");
        year.add("fourth");
        sem.add("Semester");
        sem.add("I");
        sem.add("II");
        sem.add("III");
        sem.add("IV");
        sem.add("V");
        sem.add("VI");
        sem.add("VII");
        sem.add("VIII");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spin1, year);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,R.layout.spin1, sem);
        dataAdapter.setDropDownViewResource(R.layout.spin1);
        dataAdapter2.setDropDownViewResource(R.layout.spin1);
        yearspin.setAdapter(dataAdapter);
        semspin.setAdapter(dataAdapter2);
        sharedPreferences=getSharedPreferences(StaffLogin.Staff,MODE_PRIVATE);
        uname=sharedPreferences.getString("uname",null);

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

    public void send(View V){
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.names().get(0).equals("success")){
                        Toast.makeText(getApplicationContext(), "SUCCESSFULLY " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Error Occured. ", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("notice", notice.getText().toString());
                hashMap.put("sem",semspin.getSelectedItem().toString());
                hashMap.put("year",yearspin.getSelectedItem().toString());

                return hashMap;
            }
        };

        requestQueue.add(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.staff_notice_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.studentList) {
            startActivity(new Intent(getApplicationContext(),StudentList.class));

        } else if (id == R.id.mail) {
            startActivity(new Intent(getApplicationContext(),Email.class));


        } else if (id == R.id.note) {
            startActivity(new Intent(getApplicationContext(),Notes.class));


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
