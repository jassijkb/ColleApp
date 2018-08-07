package com.example.jaspreetbhui.cetclg;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CET extends AppCompatActivity {
    Button b3,b5,b6,b7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        b3=(Button)findViewById(R.id.button3);
        b5=(Button)findViewById(R.id.button5);
        b6=(Button)findViewById(R.id.button6);
        b7=(Button)findViewById(R.id.button7);

        b3.setOnClickListener(new View.OnClickListener(){
                                  @Override
                                  public void onClick(View v) {
                                      startActivity(new Intent(getBaseContext(),About.class));
                                  }
                              }
        );

        b5.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      startActivity( new Intent(getBaseContext(), Result.class));
                                  }
                              }
        );
        b6.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {

                                      startActivity(new Intent(getBaseContext(), Photo.class));
                                  }
                              }
        );
        b7.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      startActivity( new Intent(getBaseContext(), CentralFacilities.class));
                                  }
                              }
        );


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("key", "save");
                Intent intent = new Intent(getApplicationContext(), AddNotes.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void student(View V) {
        startActivity(new Intent(getBaseContext(), StudentLogin.class));

    }
    public void staffLogin(View V){
        startActivity(new Intent(getBaseContext(),StaffLogin.class));

    }
    public  void news(View V){

        startActivity(new Intent(getBaseContext(),News.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }


}
