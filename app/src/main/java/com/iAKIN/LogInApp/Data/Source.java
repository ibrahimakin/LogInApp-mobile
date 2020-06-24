package com.iAKIN.LogInApp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Source {
    SQLiteDatabase db;
    SQLite_db myDB;

    public Source(Context c){
        myDB = new SQLite_db(c,"Records", null, 1);
    }

    public void open(){
        db = myDB.getWritableDatabase();
    }

    public void close(){
        myDB.close();
    }

    public long CreateRecord(String site, String email, String username, String hint, String labels, String registrationDate, String changingDate, int sync, String hash){
        ContentValues val = new ContentValues();

        val.put("site", site);
        val.put("email", email);
        val.put("username", username);
        val.put("hint", hint);
        val.put("labels", labels);
        val.put("registrationDate", registrationDate);
        val.put("changingDate", changingDate);
        val.put("sync", sync);
        val.put("hash", hash);

        long a = db.insert("Records", null, val);
        //Log.d(TAG, "Kayıt " + a);
        return a;
    }

    public List<Record> getRecords(){
        String columns[] = {"id", "site", "email", "username", "hint", "labels", "registrationDate", "changingDate", "sync", "hash"};

        Cursor c = db.query("Records", columns, null, null, null, null, null);

        List<Record> records = new ArrayList<Record>();

        c.moveToFirst();
        while(!c.isAfterLast()){

            Record r = new Record(c.getInt(0), c.getString(1), c.getString(2),
                    c.getString(3), c.getString(4), c.getString(5),
                    c.getString(6), c.getString(7), c.getInt(8),
                    c.getString(9));
            records.add(r);
            c.moveToNext();
        }
        c.close();
        return records;
    }
    public void deleteRecord(Record r){
        String hash = r.getHash();
        db.delete("Records", "hash = " + hash, null);
    }
}
