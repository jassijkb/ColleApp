package com.example.jaspreetbhui.cetclg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jaspreet Bhui on 10-Apr-16.
 */
public class NotesDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME ="Notes.db";
    private static final String TABLE_NAME="Notes";
    SQLiteDatabase db;
    private HashMap hm;
    private static final String TABLE_CREATE="create table Notes(id integer primary key not null,"+
            "Head text ,Notes varchar(500) );";


    public NotesDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(TABLE_CREATE);

        this.db=db;


    }
    public boolean insertNotes(String head,String notes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        String query="select * from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query, null);
        int count=cursor.getCount();
        content.put("id",count);
        content.put("head", head);
        content.put("notes", notes);
        db.insert(TABLE_NAME, null, content);
        Log.i("DATABASE", "Record inserted successfully");
        return true;
    }


    public Integer deleteNotes (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ? ", new String[] { Integer.toString(id)});
    }


    public boolean updateNotes (Integer id, String head, String notes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Head",head);
        content.put("Notes",notes);
        db.update(TABLE_NAME, content, "id = ? ", new String[] { Integer.toString(id) } );
        Log.i("DATABASE","Record updated successuflly");
        return true;
    }

    public ArrayList getAllNotes()
    {
        ArrayList list = new ArrayList();
        hm = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select * from "+TABLE_NAME, null );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false)
        {
            list.add(cursor.getString(cursor.getColumnIndex("Head")));
            cursor.moveToNext();
        }

        return list;
    }

    public String getHead(int id){
        String head="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select Head from "+TABLE_NAME+" where id="+id, null );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false)
        {
            head=cursor.getString(cursor.getColumnIndex("Head"));
            cursor.moveToNext();
        }

      return  head;
    }
    public String getBody(int id){
        String head="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select Notes from "+TABLE_NAME+" where id="+id, null );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false)
        {
            head=cursor.getString(cursor.getColumnIndex("Notes"));
            cursor.moveToNext();
        }

        return  head;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query="DROP TABLE IF EXITS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

}
