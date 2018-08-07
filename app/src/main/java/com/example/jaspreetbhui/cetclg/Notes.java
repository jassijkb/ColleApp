package com.example.jaspreetbhui.cetclg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class Notes extends AppCompatActivity{
    private ListView listView;
    NotesDBHandler db;
    ArrayList list;
    ArrayAdapter arrayAdapter;
    AlertDialog.Builder alertDialogBuilder;
    SharedPreferences sharedPreferences;
    String uname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences(StaffLogin.Staff,MODE_PRIVATE);
        uname=sharedPreferences.getString("uname",null);
        db = new NotesDBHandler(getApplicationContext());
        list = db.getAllNotes();
        arrayAdapter = new ArrayAdapter(this, R.layout.list, list);
        listView = (ListView) findViewById(R.id.listNotes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("" + position);
                Bundle bundle = new Bundle();

                bundle.putInt("id", position);
                bundle.putString("key", "update");
                Intent intent = new Intent(getApplicationContext(), AddNotes.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }

        });

       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                Toast.makeText(Notes.this, "You clicked yes button", Toast.LENGTH_LONG).show();
               /* alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
                alertDialogBuilder.setMessage("Do you realy want to delete your note ?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        db.deleteNotes(pos);
                        //Toast.makeText(MainActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
*/
                return false;
            }
        });


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




}
