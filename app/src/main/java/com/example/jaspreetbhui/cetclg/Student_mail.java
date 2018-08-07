package com.example.jaspreetbhui.cetclg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
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
import android.widget.EditText;
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

public class Student_mail extends AppCompatActivity  {
    String name ,email;
    EditText to,subject,message;
    RequestQueue requestQueue;
    StringRequest request;
    private static final String URL_User = "http://192.168.43.37/cetclg/get_email.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_mail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        to=(EditText)findViewById(R.id.Recipient);
        subject=(EditText)findViewById(R.id.Subj);
        message=(EditText)findViewById(R.id.Message);
        to.setText("To : "+name);

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
   public void sendMail(View V) {
       if(!isOnline(getApplicationContext())){
           Toast.makeText(getApplicationContext(),"No Internet Connection.",Toast.LENGTH_LONG).show();
       }else {
           requestQueue = Volley.newRequestQueue(getApplicationContext());
           request = new StringRequest(Request.Method.POST, URL_User, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   System.out.println("hello2" + response);
                   try {
                       JSONObject jsonObject = new JSONObject(response);
                       if (jsonObject.names().get(0).equals("email")) {

                           Intent emailIntent = new Intent(Intent.ACTION_SEND);
                           emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{jsonObject.getString("email")});
                           emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                           emailIntent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
                           emailIntent.setType("text/plain");

                           try {
                               startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));


                           }
                           catch (android.content.ActivityNotFoundException ex) {
                               Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                           }

                       }

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }


               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {

               }
           }) {
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   HashMap<String, String> hashMap = new HashMap<String, String>();
                   hashMap.put("name", name.toString());
                   return hashMap;
               }
           };

           requestQueue.add(request);
       }


    }



}
