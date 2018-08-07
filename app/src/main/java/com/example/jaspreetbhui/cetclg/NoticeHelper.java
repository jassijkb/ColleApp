package com.example.jaspreetbhui.cetclg;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;
import java.util.HashMap;

public class NoticeHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME ="Notice.db";
    private static final String TABLE_NAME="NoticeBoard";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_NOTICE="Notice";
    private HashMap hm;
    SQLiteDatabase db;
    private static final String TABLE_CREATE="create table NoticeBoard(id integer primary key not null,"+
            "Notice varchar(250) not null);";

    public NoticeHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

        this.db=db;

    }
    public void insertStData(String menu){
        db=getWritableDatabase();
        ContentValues values=new ContentValues();
        String query="select * from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        int count=cursor.getCount();
        values.put(COLUMN_ID, count);
        values.put(COLUMN_NOTICE, menu);
        db.insert(TABLE_NAME, null, values);
        db.close();
        System.out.println(" hello inserted x5");

    }
    public ArrayList getMenu(){
        ArrayList list = new ArrayList();
       // hm = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor r = db.rawQuery("select * from NoticeBoard ", null);
        r.moveToFirst();
        while(r.isAfterLast() == false)
        {
            list.add(r.getString(r.getColumnIndex(COLUMN_NOTICE)));
            r.moveToNext();
        }
        System.out.println(" hello hgfs x5");
        return list;
    }
    public void deleteAll(){
        db=getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
        System.out.println(" hello yes deleted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query="DROP TABLE IF EXITS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }
}
