package com.example.jaspreetbhui.cetclg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Map;

public class StaffList extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
     Spinner dept;

    String[] branches={"Computer Science","Electrical","Electronics","Civil","Ceremics","Mechanical"};
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.43.37/cetclg/get_staff.php";
    private StringRequest request;
    JSONArray staffjson = null;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> staffList;
    ListView staff;
    SharedPreferences sharedPreferences;
    String uname;
    NoticeHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new NoticeHelper(getApplicationContext());
        sharedPreferences=getSharedPreferences(StudentLogin.Student,MODE_PRIVATE);
        uname=sharedPreferences.getString("uname",null);

        dept = (Spinner) findViewById(R.id.spinner7);
        dept.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.spin1, branches);
        dataAdapter.setDropDownViewResource(R.layout.spin1);
        dept.setAdapter(dataAdapter);
        staffList = new ArrayList<HashMap<String, String>>();
        staff=(ListView)findViewById(R.id.staffNameList);

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
    public void showStaffList(View V){
        if(!isOnline(getApplicationContext())){
            Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_LONG).show();
        }else {

            request = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("hello2" + response);
                    try {
                        int sn=1;
                        JSONObject jsonObject = new JSONObject(response);
                        staffjson = jsonObject.getJSONArray("result");
                        for (int i = 0; i < staffjson.length(); i++) {
                            JSONObject c = staffjson.getJSONObject(i);
                            String name = c.getString("name");
                           String sno=""+sn;
                            sn++;
                            map = new HashMap<String, String>();
                           map.put("sno",sno);
                            map.put("name", name);
                            staffList.add(map);

                        }
                        try {
                            ListAdapter dataAdapter = new SimpleAdapter(getApplicationContext(), staffList, R.layout.list_rows, new String[]{ "sno","name"}, new int[]{
                                    R.id.Sno, R.id.main});
                            staff.setAdapter(dataAdapter);
                        }catch (Exception e) {

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(StaffList.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("dept", dept.getSelectedItem().toString());
                    return hashMap;
                }
            };


            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }
        staff.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView v = (TextView) findViewById(R.id.main);
                String content = v.getText().toString();
                Intent i = new Intent(getApplicationContext(), Student_mail.class);
                i.putExtra("name", content);
                startActivity(i);

            }

        });

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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.NoticeList) {
            startActivity(new Intent(getApplicationContext(),StudentNoticeBoard.class));
            finish();
        }
        else if (id == R.id.StNote) {
            startActivity(new Intent(getApplicationContext(),Notes.class));
            finish();

        } else if (id == R.id.Ssettings) {

        } else if (id == R.id.Slogout) {
            db.deleteAll();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(getBaseContext(), StudentLogin.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
