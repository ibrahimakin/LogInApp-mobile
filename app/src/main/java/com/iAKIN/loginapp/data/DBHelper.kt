package com.iAKIN.loginapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.util.Date

const val db_name = "database"
const val table_name = "records"
const val id = "id"
const val site = "site"
const val email = "email"
const val username = "username"
const val hint = "hint"
const val tags = "tags"
const val changingDate = "changingDate"
const val registrationDate = "registrationDate"
const val hash = "hash"
const val sync = "sync"

class DBHelper(var context: Context) : SQLiteOpenHelper(context, db_name, null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable = " CREATE TABLE " + table_name + "(" +
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
        cv.put(changingDate, Date().toString())
        cv.put(registrationDate, Date().toString())
        cv.put(hash, Sync.getMD5Hash(record.site + record.email))
        cv.put(sync, 2)

        val result = db.insert(table_name, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Data could not be added.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show()
        }
        db.close()
    }

    fun read(): MutableList<Record> {
        val list: MutableList<Record> = ArrayList()
        val db = this.readableDatabase
        val query = " select * from $table_name"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val record = Record(
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

    fun update(id: Int, record: Record) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(site, record.site)
        cv.put(email, record.email)
        cv.put(username, record.username)
        cv.put(hint, record.hint)
        cv.put(tags, record.tags)
        cv.put(changingDate, Date().toString())
        cv.put(hash, Sync.getMD5Hash(record.site + record.email))
        cv.put(sync, 2)
        val result = db.update(table_name, cv, "id = $id", null)
        if (result == 1) {
            Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Data could not be updated.", Toast.LENGTH_LONG).show()
        }
        db.close()
    }

    fun delete(id: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(table_name, "id = $id", null)
        if (result == 1) {
            Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Data could not be updated.", Toast.LENGTH_LONG).show()
        }
        return result
    }
}