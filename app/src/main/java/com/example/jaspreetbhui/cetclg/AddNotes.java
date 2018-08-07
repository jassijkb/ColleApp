package com.example.jaspreetbhui.cetclg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNotes extends AppCompatActivity {
    EditText head,notes;
    NotesDBHandler db;
    String key="save";
    int id;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db=new NotesDBHandler(getApplicationContext());
        bundle = getIntent().getExtras();
        head=(EditText)findViewById(R.id.head);
        notes=(EditText)findViewById(R.id.notes);
        id=bundle.getInt("id");
        key=bundle.getString("key");
        if(!key.equals("save")){
            head.setText(db.getHead(id));
            notes.setText(db.getBody(id));
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
    public void noteSaved(View V){
        if(key.equals("save")) {
            boolean flag = db.insertNotes(head.getText().toString(), notes.getText().toString());

            if (flag)
                Toast.makeText(getApplicationContext(), "Note Saved", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Note Not Saved", Toast.LENGTH_LONG).show();
        }else {
            boolean flag = db.updateNotes(id, head.getText().toString(), notes.getText().toString());

            if (flag)
                Toast.makeText(getApplicationContext(), "Note updated", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Note Not updated", Toast.LENGTH_LONG).show();

        }

        Intent i = new Intent(getApplicationContext(),Notes.class);
        startActivity(i);

    }

}
