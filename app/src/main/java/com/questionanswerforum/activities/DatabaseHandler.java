package com.questionanswerforum.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.questionanswerforum.Pojo.QueAnsPojo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{
    public DatabaseHandler(Context context) {
        super(context, "queans.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {//recipe_name,ingredients,recipe_procedure,id
        db.execSQL("create table tb_queans(id INTEGER PRIMARY KEY AUTOINCREMENT,question TEXT,answer TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_queans");
        onCreate(db);
    }
    public long addQueAns(String question,String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question", question);
        values.put("answer", answer);

        long a = db.insert("tb_queans", null, values);
        db.close(); // Closing database connection
        return a;
    }
    public boolean isBookQueMarked(String question) {
        boolean fg=false;
        String selectQuery = "SELECT question FROM tb_queans WHERE question='"+question+"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount()>0)
        {
            fg=true;
        }
        cursor.close();
        return fg;
    }
    public boolean isBookMarked(String answer) {
        boolean fg=false;
        String selectQuery = "SELECT answer FROM tb_queans WHERE answer='"+answer+"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount()>0)
        {
            fg=true;
        }
        cursor.close();
        return fg;
    }

    public List<QueAnsPojo> getQueAns() {
        List<QueAnsPojo> ll= new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM tb_queans;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ll.add(new QueAnsPojo(cursor.getString(1),cursor.getString(2)));

            } while (cursor.moveToNext());
        }

        // return contact list
        return ll;
    }
}
