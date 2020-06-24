package com.iAKIN.LogInApp.Data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite_db extends SQLiteOpenHelper {

    public SQLite_db(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table Records (id integer primary key autoincrement, site text not null, email TEXT NOT NULL, " +
                "username TEXT, hint TEXT, labels TEXT, registrationDate TEXT NOT NULL, changingDate TEXT NOT NULL, " +
                "sync INTEGER NOT NULL, hash TEXT NOT NULL UNIQUE);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("drop table if exists Records"); // Tablo mevcut ise sil
    }
}
