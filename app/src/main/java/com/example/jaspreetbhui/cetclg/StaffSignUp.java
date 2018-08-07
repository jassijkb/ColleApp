package com.example.jaspreetbhui.cetclg;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StaffSignUp extends AppCompatActivity {
  String[] branches={"Computer Science","Electrical","Electronics","Civil","Ceremics","Mechanical"};
    private AutoCompleteTextView dept;
    private EditText name,uname,email,pass1,pass2,stfKey,sub,phno;
    RequestQueue requestQueue1;

    StringRequest request1;

    private static final String URL_Register = "http://192.168.43.37/cetclg/register.php";
    private static final String URL_User = "http://192.168.43.37/cetclg/staff_control.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       name=(EditText)findViewById(R.id.name);
        uname=(EditText)findViewById(R.id.uname);
        email=(EditText)findViewById(R.id.email);
        pass1=(EditText)findViewById(R.id.pass1);
        pass2=(EditText)findViewById(R.id.pass2);
        stfKey=(EditText)findViewById(R.id.stfkey);
        sub=(EditText)findViewById(R.id.subject);
        phno=(EditText)findViewById(R.id.mobno);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,branches);
        dept=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        dept.setThreshold(1);
        dept.setAdapter(adapter);
        uname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                    request1 = new StringRequest(Request.Method.POST, URL_User, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("hello2"+response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.names().get(0).equals("success")){
                                    uname.setError("this userName is already taken.");
                                    uname.setText("");
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
                            hashMap.put("uname",uname.getText().toString());

                            return hashMap;
                        }
                    };

                    requestQueue1.add(request1);
                }
            }
        });

        pass1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (pass1.getText().toString().length() < 8) {
                        pass1.setError("password must contains atleast 8 characters");
                        pass1.setText("");
                    } else
                        pass1.setError(null);
                }
            }

        });
        pass2.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!pass2.getText().toString().equals(pass1.getText().toString())){
                        pass2.setError("password not matched");
                        pass2.setText("");
                    }
                    else
                        pass1.setError(null);
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

    public void createAcc(View V) {
         String namestr=name.getText().toString();
         String unamestr=uname.getText().toString();
         String  emailstr=email.getText().toString();
         String passstr1=pass1.getText().toString();
         String passstr2=pass1.getText().toString();
         String deptstr=dept.getText().toString();
         String  substr=sub.getText().toString();
         String  phnostr=phno.getText().toString();
        if(!isOnline(getApplicationContext())){
            Toast.makeText(getApplicationContext(),"No Internet Connection.",Toast.LENGTH_LONG).show();
        }else {


            if (namestr.isEmpty())
                name.setError("name should'nt be empty!");

            if (unamestr.isEmpty())
                uname.setError("UserName should'nt be empty!");
            if (emailstr.isEmpty())
                email.setError("enter your email address!");

            if (passstr1.isEmpty())
                pass1.setError("Enter password");

            if (passstr2.isEmpty())
                pass2.setError("re-enter password..");

            if (deptstr.isEmpty())
                dept.setError("enter your department..");
            if (substr.isEmpty())
                sub.setError("enter your department..");

            if (phnostr.isEmpty())
                phno.setError("please enter your phone number.");
            else if (!namestr.isEmpty() && !unamestr.isEmpty() && !emailstr.isEmpty() && !passstr1.isEmpty() && !passstr2.isEmpty()
                    && !deptstr.isEmpty() && !substr.equals("Year")) {
                Register register = new Register();
                register.execute(URL_Register);

            }
        }

    }
    public class Register extends AsyncTask<String, Void, String> {
        String response = "";
        String namestr=name.getText().toString();
        String unamestr=uname.getText().toString();
        String  emailstr=email.getText().toString();
        String passstr1=pass1.getText().toString();
        String passstr2=pass1.getText().toString();
        String deptstr=dept.getText().toString();
        String  substr=sub.getText().toString();
        String  phnostr=phno.getText().toString();
        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection conn;
            try {
                String updatetext =
                        "param1=" + URLEncoder.encode(namestr, "UTF-8") +
                                "&param2=" + URLEncoder.encode(unamestr, "UTF-8") +
                                "&param3=" + URLEncoder.encode(emailstr, "UTF-8")+
                                "&param4=" + URLEncoder.encode(passstr1, "UTF-8") +
                                "&param5=" + URLEncoder.encode(deptstr, "UTF-8") +
                                "&param6=" + URLEncoder.encode(substr, "UTF-8") +
                                "&param7=" + URLEncoder.encode(phnostr, "UTF-8") ;
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setFixedLengthStreamingMode(updatetext.getBytes().length);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                PrintWriter out = new PrintWriter(conn.getOutputStream());
                out.print(updatetext);
                out.close();
                Scanner inStream = new Scanner(conn.getInputStream());
                while (inStream.hasNextLine())
                    response += (inStream.nextLine());
                System.out.println("hello"+response);
            } catch (Exception ex) {
                ex.printStackTrace();

            }
            return response;
        }

        public void onPostExecute(String s) {
            String status = "";
            super.onPostExecute(s);
            JSONArray jArray = null;
            try {
                jArray = new JSONArray(response);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    status = json_data.getString("status");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(status.equals("YoDone"))
            {
                Toast.makeText(getApplicationContext(),"Your Account is Created.",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), StudentLogin.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
            }
        }

    }

}
