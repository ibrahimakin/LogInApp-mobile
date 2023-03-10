package com.iAKIN.loginapp.database

import android.net.Uri
import android.content.Context
import org.json.JSONArray

class FileReader {
    companion object {
        fun readFile(uri: Uri?,context: Context?) {
            try {
                val stream = context?.contentResolver?.openInputStream(uri!!)
                val inputString = stream?.bufferedReader().use { it?.readText() }

                val data = JSONArray(inputString)

                // looping through All nodes
                for (i in 0 until data.length()) {
                    val c = data.getJSONObject(i)
                    val id = c.getInt("id")
                    val site = c.getString("site")
                    val email = c.getString("email")
                    val username = c.getString("username")
                    val hint = c.getString("hint")
                    val tags = c.getString("labels")
                    val changingDate = c.getString("changingDate")
                    val registrationDate = c.getString("registrationDate")
                    val hash = c.getString("hash")
                    val sync = c.getInt("sync")
                    // use >  int id = c.getInt("duration"); if you     want get an int

                    DBHelper(context!!).insert(Record(id, site, email, username, hint, tags, changingDate, registrationDate, hash, sync))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}