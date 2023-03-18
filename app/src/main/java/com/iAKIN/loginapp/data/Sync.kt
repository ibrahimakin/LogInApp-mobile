package com.iAKIN.loginapp.data

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONArray
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Sync {
    companion object {
        fun readFile(uri: Uri?, context: Context?) {
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

                    DBHelper(context!!).insert(
                        Record(id, site, email, username, hint, tags, changingDate, registrationDate, hash, sync)
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun getMD5Hash(input: String): String {
            val md: MessageDigest = MessageDigest.getInstance("MD5")
            val hashInBytes: ByteArray = md.digest(input.toByteArray(StandardCharsets.UTF_8))
            val sb = StringBuilder()
            for (b in hashInBytes) sb.append(String.format("%02x", b))
            return sb.toString()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getTime(): String {
            val dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            val now = LocalDateTime.now()
            return dtf.format(now)
        }
    }
}