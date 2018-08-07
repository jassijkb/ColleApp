package com.example.jaspreetbhui.cetclg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CentralFacilities extends AppCompatActivity {
    Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_facilities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void bank(View V){
        Intent intent = new Intent(this, CampusDetails.class);
        b.putInt("xyz",1);
        intent.putExtras(b);
        startActivity(intent);

    }
    public void hostel(View V){
        Intent intent = new Intent(this, CampusDetails.class);
        b.putInt("xyz",2);
        intent.putExtras(b);
        startActivity(intent);

    }
    public void canteen(View V){
        Intent intent = new Intent(this, CampusDetails.class);
        b.putInt("xyz",3);
        intent.putExtras(b);
        startActivity(intent);

    }
    public void medical(View V){
        Intent intent = new Intent(this, CampusDetails.class);
        b.putInt("xyz",4);
        intent.putExtras(b);
        startActivity(intent);

    }
    public void nss(View V){
        Intent intent = new Intent(this, CampusDetails.class);
        b.putInt("xyz",5);
        intent.putExtras(b);
        startActivity(intent);

    }
    public void ncc(View V){
        Intent intent = new Intent(this, CampusDetails.class);
        b.putInt("xyz",6);
        intent.putExtras(b);
        startActivity(intent);
    }
    public void comp(View V){
        Intent intent = new Intent(this, CampusDetails.class);
        b.putInt("xyz",7);
        intent.putExtras(b);
        startActivity(intent);
    }
    public void trans(View V){
        Intent intent = new Intent(this, CampusDetails.class);
        b.putInt("xyz",8);
        intent.putExtras(b);
        startActivity(intent);
    }


}
