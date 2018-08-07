package com.example.jaspreetbhui.cetclg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Email extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private Spinner semspin,batchspin,branchspin;
    EditText subject_ed,message_ed;
    String[] branches={"Computer Science","Electrical","Electronics","Civil","Ceremics","Mechanical"};
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.43.37/cetclg/email.php";
    private StringRequest request;
    JSONArray emailjson = null;
    List<String> emailList;
    SharedPreferences sharedPreferences;
    String uname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences(StaffLogin.Staff,MODE_PRIVATE);
        uname=sharedPreferences.getString("uname",null);
        subject_ed=(EditText)findViewById(R.id.editTextSub);
        message_ed=(EditText)findViewById(R.id.editTextMsg);
        semspin = (Spinner) findViewById(R.id.spinner5);
        batchspin = (Spinner) findViewById(R.id.spinner6);
        branchspin = (Spinner) findViewById(R.id.spinner4);
        branchspin.setOnItemSelectedListener(this);
        semspin.setOnItemSelectedListener(this);
        batchspin.setOnItemSelectedListener(this);
        List<String> sem = new ArrayList<String>();
        List<String> batch = new ArrayList<String>();
        sem.add("Semester");
        sem.add("I");
        sem.add("II");
        sem.add("III");
        sem.add("IV");
        sem.add("V");
        sem.add("VI");
        sem.add("VII");
        sem.add("VIII");
        batch.add("class");
        batch.add("I");
        batch.add("II");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spin1, sem);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,R.layout.spin1, batch);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,R.layout.spin1, branches);
        dataAdapter.setDropDownViewResource(R.layout.spin1);
        dataAdapter2.setDropDownViewResource(R.layout.spin1);
        dataAdapter3.setDropDownViewResource(R.layout.spin1);
        semspin.setAdapter(dataAdapter);
        batchspin.setAdapter(dataAdapter2);
        branchspin.setAdapter(dataAdapter3);
        emailList = new ArrayList<String>();


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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void compose(View V){
        sendEmail();

    }
    protected void sendEmail() {
        if(!isOnline(getApplicationContext())){
            Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_LONG).show();
        }else {

            request = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("hello2" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        emailjson = jsonObject.getJSONArray("result");
                        for (int i = 0; i < emailjson.length(); i++) {
                            JSONObject c = emailjson.getJSONObject(i);
                            String email = c.getString("email");

                            emailList.add(email);

                        }
                        String subject = subject_ed.getText().toString();
                        String message = message_ed.getText().toString();
                        Object[] to =  emailList.toArray();
                        String[] TO = {""};
                        String[] CC = {""};
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        for(int i = 0; i < to.length; i++){
                            Log.i("String is", (String) to[i]);
                            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                                    emailList.toArray(new String[emailList.size()]));
                            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
                            emailIntent.setType("text/plain");
                            try {
                                startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));


                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                            }


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Email.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("branch",branchspin.getSelectedItem().toString() );
                    hashMap.put("sem", semspin.getSelectedItem().toString());
                    hashMap.put("batch", batchspin.getSelectedItem().toString());

                    return hashMap;
                }
            };


            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }

    }
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkinfo = cm.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            return true;
        }
        return false;
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.studentList) {
            startActivity(new Intent(getApplicationContext(),StudentList.class));
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
