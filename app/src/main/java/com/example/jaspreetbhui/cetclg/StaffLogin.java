package com.example.jaspreetbhui.cetclg;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

public class StaffLogin extends AppCompatActivity {
    public static final String Staff = "staff" ;
    EditText uname,pass;
    TextView error;
    RequestQueue requestQueue;
    StringRequest request;
    private static final String URL_User = "http://192.168.43.37/cetclg/staff_login.php";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        uname=(EditText)findViewById(R.id.editTextname);
        pass=(EditText)findViewById(R.id.editTextpass);
        error=(TextView)findViewById(R.id.textViewerror);
        sharedPreferences=getSharedPreferences(Staff,MODE_PRIVATE);
        if(sharedPreferences.contains("uname")){
            Intent intent = new Intent(getBaseContext(), StaffNoticeBoard.class);
            startActivity(intent);
            finish();
        }
        uname.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    error.setText("");
                }
            }

        });

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
    public void stfLogin(View V){

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
                        if (jsonObject.names().get(0).equals("success")) {
                            Toast.makeText(getApplicationContext(), "" + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("uname", uname.getText().toString());
                            editor.commit();
                            Intent intent = new Intent(getBaseContext(), StaffNoticeBoard.class);
                            startActivity(intent);
                        } else {
                            pass.setText("");
                            uname.setText("");
                            error.setText("Incorrect Username Or Password.");


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
                    hashMap.put("uname", uname.getText().toString());
                    hashMap.put("pass", pass.getText().toString());
                    return hashMap;
                }
            };

            requestQueue.add(request);
        }


    }
    public void stfSignUp(View V){
        Intent intent=new Intent(getBaseContext(),StaffSignUp.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onStop();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
       // onCreate(saved);
    }
}
