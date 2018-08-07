package com.example.jaspreetbhui.cetclg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CampusDetails extends AppCompatActivity {
    ImageView v;
    TextView t;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i=getIntent();
        Bundle b = i.getExtras();
        x=b.getInt("xyz");
        v=(ImageView)findViewById(R.id.imageView2);
        t=(TextView)findViewById(R.id.textView);

        switch (x) {
            case 1: {
                v.setImageResource(R.drawable.bank);
                t.setText(Html.fromHtml(getString(R.string.obcBank)));
                break;
            }
            case 2: {
                v.setImageResource(R.drawable.hos);
                t.setText(Html.fromHtml(getString(R.string.hostel)));
                break;
            }
            case 3: {
                v.setImageResource(R.drawable.canteen);
                t.setText(Html.fromHtml(getString(R.string.canteen)));
                break;
            }
            case 4: {
                v.setImageResource(R.drawable.medical);
                t.setText(Html.fromHtml(getString(R.string.medical)));
                break;
            }
            case 5: {
                v.setImageResource(R.drawable.nss);
                t.setText(Html.fromHtml(getString(R.string.nss)));
                break;
            }
            case 6: {
                v.setImageResource(R.drawable.ncc);
                t.setText(Html.fromHtml(getString(R.string.ncc)));
                break;
            }
            case 7: {
                v.setImageResource(R.drawable.com);
                t.setText(Html.fromHtml(getString(R.string.com)));
                break;
            }
            case 8: {
                v.setImageResource(R.drawable.trans);
                t.setText(Html.fromHtml(getString(R.string.trans)));
                break;
            }
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

}
