package com.example.jaspreetbhui.cetclg;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String link = "http://192.168.1.102/cetclg/student_register.php";
    String[] branches={"Computer Science","Electrical","Electronics","Civil","Ceremics","Mechanical"};
    private Spinner yearspin,semspin,batchspin;
   private AutoCompleteTextView branch;
    private TextView v;
    private EditText name,email,uname,pass1,pass2,rollno,phno;
    RequestQueue requestQueue;
    StringRequest request;
    private static final String URL_User = "http://192.168.43.37/cetclg/student_control.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        v=(TextView)findViewById(R.id.textView9);
        pass1=(EditText)findViewById(R.id.editText5);
        pass2=(EditText)findViewById(R.id.editText6);
        name=(EditText)findViewById(R.id.editText3);
        email=(EditText)findViewById(R.id.editText8);
        uname=(EditText)findViewById(R.id.editText4);
        rollno=(EditText)findViewById(R.id.editText9);
        phno=(EditText)findViewById(R.id.editText7);

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,branches);
        branch=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        branch.setThreshold(1);
        branch.setAdapter(adapter);
        yearspin = (Spinner) findViewById(R.id.spinner);
        semspin = (Spinner) findViewById(R.id.spinner2);
        batchspin = (Spinner) findViewById(R.id.spinner3);
        batchspin.setOnItemSelectedListener(this);
        yearspin.setOnItemSelectedListener(this);
        semspin.setOnItemSelectedListener(this);
        List<String> year = new ArrayList<String>();
        List<String> sem = new ArrayList<String>();
        List<String> batch = new ArrayList<String>();
        batch.add("class");
        batch.add("I");
        batch.add("II");
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spin1, year);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,R.layout.spin1, sem);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,R.layout.spin1, batch);
        dataAdapter.setDropDownViewResource(R.layout.spin1);
        dataAdapter2.setDropDownViewResource(R.layout.spin1);
        dataAdapter3.setDropDownViewResource(R.layout.spin1);
        batchspin.setAdapter(dataAdapter3);
        yearspin.setAdapter(dataAdapter);
        semspin.setAdapter(dataAdapter2);
      uname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

               if(!hasFocus){
                   requestQueue = Volley.newRequestQueue(getApplicationContext());
                   request = new StringRequest(Request.Method.POST, URL_User, new Response.Listener<String>() {
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
                   requestQueue.add(request);
               }
            }
        });

        pass1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (pass1.getText().toString().length() < 8) {
                        pass1.setError("password must contains atleast 8 characters");
                    }
                    else
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

    private void registerUser() {
        register register = new register();
        register.execute(link);
    }
    public class register extends AsyncTask<String, Void, String> {
        String response = "";
        String namestr=name.getText().toString();
        String unamestr=uname.getText().toString();
       String  emailstr=email.getText().toString();
        String passstr=pass1.getText().toString();
       String  branchstr=branch.getText().toString();
       String  yearstr=yearspin.getSelectedItem().toString();
        String  batchstr=batchspin.getSelectedItem().toString();
        String semstr=semspin.getSelectedItem().toString();
        String mobnostr=phno.getText().toString();
        String rollnostr=rollno.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection conn;
            try {
                String updatetext =
                        "param1=" + URLEncoder.encode(namestr, "UTF-8") +
                                "&param2=" + URLEncoder.encode(unamestr, "UTF-8") +
                                "&param3=" + URLEncoder.encode(emailstr, "UTF-8")+
                                "&param4=" + URLEncoder.encode(passstr, "UTF-8") +
                                "&param5=" + URLEncoder.encode(branchstr, "UTF-8") +
                                "&param6=" + URLEncoder.encode(yearstr, "UTF-8") +
                                "&param7=" + URLEncoder.encode(semstr, "UTF-8") +
                                "&param8=" + URLEncoder.encode(batchstr, "UTF-8") +
                                "&param9=" + URLEncoder.encode(rollnostr, "UTF-8") +
                                "&param10=" + URLEncoder.encode(mobnostr, "UTF-8");
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
                Toast.makeText(getApplicationContext(),"Successfully created.",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkinfo = cm.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            return true;
        }
        return false;
    }
    public void accCreated(View V){
        String namestr,emailstr,unamestr,passstr1,passstr2,rollstr,phnostr,yearstr,branchstr,semstr,batchstr;
        namestr=name.getText().toString();
        unamestr=uname.getText().toString();
        emailstr=email.getText().toString();
        passstr1=pass1.getText().toString();
        passstr2=pass2.getText().toString();
        branchstr=branch.getText().toString();
        yearstr=yearspin.getSelectedItem().toString();
        semstr=semspin.getSelectedItem().toString();
        batchstr=semspin.getSelectedItem().toString();
        phnostr=phno.getText().toString();
       rollstr=rollno.getText().toString();

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

            if (branchstr.isEmpty())
                branch.setError("Enter your branch.");

            if (yearstr.equals("Year"))
                v.setText("Please select your year.");

            if (semstr.equals("Semester"))
                v.setText("Please select your Semester.");
            if (semstr.equals("class"))
                v.setText("Please select your Class.");


            if (rollstr.isEmpty())
                rollno.setError("please enter your roll no.");

            if (phnostr.isEmpty())
                phno.setError("please enter your phone number.");
            else if (!namestr.isEmpty() && !unamestr.isEmpty() && !emailstr.isEmpty() && !passstr1.isEmpty() && !passstr2.isEmpty()
                    && !branchstr.isEmpty() && !yearstr.equals("Year") && !semstr.equals("Semester")  && !batchstr.equals("class") && !rollstr.isEmpty()) {
                registerUser();

            }
        }
    }
}
