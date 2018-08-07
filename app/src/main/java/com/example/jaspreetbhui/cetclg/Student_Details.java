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
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class Student_Details extends AppCompatActivity {
    String rollno;
    TextView txtview;
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.43.37/cetclg/details.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtview = (TextView) findViewById(R.id.details);
        Intent i = getIntent();
        rollno = i.getStringExtra("rollno");
        if(!isOnline(getApplicationContext())){
            Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_LONG).show();
        }else {

            request = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("hello2" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.names().get(0).equals("error")) {
                            Toast.makeText(getApplicationContext(), "error" , Toast.LENGTH_SHORT).show();


                        } else {
                            txtview.setText("Name= "+jsonObject.getString("name")+"\n"+"Email= "+jsonObject.getString("email")+"\n"+
                            "Class= "+jsonObject.getString("branch")+"("+jsonObject.getString("batch")+") \n"+"Roll no. ="
                                    +jsonObject.getString("rollno")+"\n Mobile no.= "+jsonObject.getString("mobno"));



                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Student_Details.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("rollno", rollno);

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
