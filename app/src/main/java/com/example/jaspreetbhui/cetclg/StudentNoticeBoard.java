package com.example.jaspreetbhui.cetclg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class StudentNoticeBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView notice;
    List<String> noticeList;
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.43.37/cetclg/notice.php";
    private StringRequest request;
    private RequestQueue requestQueue2;
    private static final String URL2 = "http://192.168.43.37/cetclg/get_count.php";
    private StringRequest request2;
    JSONArray noticejson = null;
    SharedPreferences sharedPreferences;
    ArrayAdapter<String> Adapter;
    String uname;
    NoticeHelper db;
    ArrayList list;
    Cursor c;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notice_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        notice=(ListView)findViewById(R.id.listView);
        noticeList = new ArrayList<String>();
        db = new NoticeHelper(getApplicationContext());


        sharedPreferences=getSharedPreferences(StudentLogin.Student,MODE_PRIVATE);
        uname=sharedPreferences.getString("uname", null);
        count=sharedPreferences.getInt("count",0);
        if(!isOnline(getApplicationContext())){

            list = db.getMenu();
            Adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list,list);
            notice.setAdapter(Adapter);


            Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_LONG).show();
        }else {
            request2 = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("hello2" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.names().get(0).equals("count")) {
                            if(jsonObject.getInt("count")>sharedPreferences.getInt("count",0)){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("count",jsonObject.getInt("count"));
                                editor.commit();
                                showlist();


                            }else{
                                list = db.getMenu();
                                Adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list,list);
                                notice.setAdapter(Adapter);

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
                            Toast.makeText(StudentNoticeBoard.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("uname", uname);

                    return hashMap;
                }
            };

            requestQueue2 = Volley.newRequestQueue(this);
            requestQueue2.add(request2);
        }

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }
        public void showlist(){
            db.deleteAll();
            request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("hello2" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        noticejson = jsonObject.getJSONArray("result");
                        for (int i = 0; i < noticejson.length(); i++) {
                            JSONObject c = noticejson.getJSONObject(i);
                            String menu = c.getString("menu");
                            noticeList.add(menu);
                            NoticeHelper helper = new NoticeHelper(getApplicationContext());
                            helper.insertStData(menu);
                        }
                        list = db.getMenu();
                        Adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list,list);
                        notice.setAdapter(Adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(StudentNoticeBoard.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("uname", uname);

                    return hashMap;
                }
            };

            requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);


        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_notice_board, menu);
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

        if (id == R.id.NoticeList) {
            // Handle the camera action
        } else if (id == R.id.TList) {
            startActivity(new Intent(getApplicationContext(),StaffList.class));

        } else if (id == R.id.StNote) {
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
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkinfo = cm.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            return true;
        }
        return false;
    }


}
