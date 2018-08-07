package com.example.jaspreetbhui.cetclg;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class StList extends AppCompatActivity {
    String branch,year,batch;
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.43.37/cetclg/stList.php";
    private StringRequest request;
    JSONArray noticejson = null;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> studentList;
    ListView student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        Bundle b = i.getExtras();

        branch = b.getString("branch");
        year=b.getString("year");
        batch=b.getString("batch");
        student=(ListView)findViewById(R.id.stlist);
        studentList = new ArrayList<HashMap<String, String>>();
        if(!isOnline(getApplicationContext())){
            Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_LONG).show();
        }else {

            request = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("hello2" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        noticejson = jsonObject.getJSONArray("result");
                        for (int i = 0; i < noticejson.length(); i++) {
                            JSONObject c = noticejson.getJSONObject(i);
                            String rollno = c.getString("rollno");
                            String name = c.getString("name");
                            map = new HashMap<String, String>();
                            map.put("rollno", rollno);
                            map.put("name", name);
                            studentList.add(map);

                        }
                        try {
                            ListAdapter dataAdapter = new SimpleAdapter(getApplicationContext(), studentList, R.layout.news_rows, new String[]{"rollno", "name"}, new int[]{
                                    R.id.date, R.id.newsSubject});
                            student.setAdapter(dataAdapter);
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
                            Toast.makeText(StList.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("branch", branch);
                    hashMap.put("year", year);
                    hashMap.put("batch", batch);

                    return hashMap;
                }
            };


            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        student.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView v = (TextView) findViewById(R.id.date);
                String content = v.getText().toString();
                Intent i = new Intent(getApplicationContext(), Student_Details.class);
                i.putExtra("rollno", content);
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
}
