package com.iAKIN.loginapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val db_name = "database"
val table_name = "records"
val id = "id"
val site = "site"
val email = "email"
val username = "username"
val hint = "hint"
val tags = "tags"
val changingDate = "changingDate"
val registrationDate = "registrationDate"
val hash = "hash"
val sync = "sync"

class DBHelper(var context: Context):SQLiteOpenHelper(context, db_name, null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        var createTable = " CREATE TABLE " + table_name + "(" +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                site + " VARCHAR(256)," +
                email + " VARCHAR(256)," +
                username + " VARCHAR(256)," +
                hint + " VARCHAR(256)," +
                tags + " VARCHAR(256)," +
                changingDate + " VARCHAR(256)," +
                registrationDate + " VARCHAR(256)," +
                hash + " VARCHAR(256)," +
                sync + " INTEGER)"
        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insert(record: Record) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(site, record.site)
        cv.put(email, record.email)
        cv.put(username, record.username)
        cv.put(hint, record.hint)
        cv.put(tags, record.tags)
        cv.put(changingDate, record.changingDate)
        cv.put(registrationDate, record.registrationDate)
        cv.put(hash, record.hash)
        cv.put(sync, record.sync)

        var result = db.insert(table_name, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Data could not be added.", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show()
        }
    }

    //    @SuppressLint("Range")
    fun read(): MutableList<Record> {
        var list:MutableList<Record> = ArrayList()
        val db = this.readableDatabase
        var query = " select * from $table_name"
        var result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var record = Record(
                    result.getInt(result.getColumnIndexOrThrow(id)),
                    result.getString(result.getColumnIndexOrThrow(site)),
                    result.getString(result.getColumnIndexOrThrow(email)),
                    result.getString(result.getColumnIndexOrThrow(username)),
                    result.getString(result.getColumnIndexOrThrow(hint)),
                    result.getString(result.getColumnIndexOrThrow(tags)),
                    result.getString(result.getColumnIndexOrThrow(changingDate)),
                    result.getString(result.getColumnIndexOrThrow(registrationDate)),
                    result.getString(result.getColumnIndexOrThrow(hash)),
                    result.getInt(result.getColumnIndexOrThrow(sync))
                )
                list.add(record)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }
}