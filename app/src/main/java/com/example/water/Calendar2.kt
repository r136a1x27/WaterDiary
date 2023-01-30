package com.example.water

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.internal.ViewOverlayImpl
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Calendar2 : AppCompatActivity() {

    var myID: String = "myID"
    lateinit var myName: String
    lateinit var calendar: CalendarView
    lateinit var title: TextView
    lateinit var note: EditText
    lateinit var save: Button
    lateinit var nsave: Button
    lateinit var delete: Button
    lateinit var str: String
    lateinit var dcontent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar2)

        calendar = findViewById(R.id.calendar)
        title = findViewById(R.id.title)
        note = findViewById(R.id.note)
        save = findViewById(R.id.save)
        nsave = findViewById(R.id.nsave)
        delete = findViewById(R.id.delete)
        dcontent = findViewById(R.id.diaryContent)


        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            title.visibility = View.VISIBLE
            note.visibility = View.VISIBLE
            save.visibility = View.VISIBLE
            nsave.visibility = View.INVISIBLE
            delete.visibility = View.INVISIBLE
            dcontent.visibility = View.INVISIBLE
            note.setText("")
            checkNote(year, month, dayOfMonth, myID)
        }

        save.setOnClickListener {
            saveNote(myName)
            note.visibility = View.INVISIBLE
            save.visibility = View.INVISIBLE
            nsave.visibility = View.VISIBLE
            delete.visibility = View.VISIBLE
            str = note.text.toString()
            dcontent.text = str
            dcontent.visibility = View.VISIBLE
        }
    }

    fun checkNote(cYear: Int, cMonth: Int, cDay: Int, myID: String) {
        myName = "" + myID + cYear + "-" + (cMonth+1) + "" + "-" + cDay + ".txt"
        var fileInputStream: FileInputStream
        try {
            fileInputStream = openFileInput(myName)
            val fileData = ByteArray(fileInputStream.available())
            fileInputStream.read(fileData)
            fileInputStream.close()
            str = String(fileData)
            note.visibility = View.INVISIBLE
            dcontent.visibility = View.VISIBLE
            dcontent.text = str
            save.visibility = View.INVISIBLE
            nsave.visibility = View.VISIBLE
            delete.visibility = View.VISIBLE
            nsave.setOnClickListener {
                note.visibility = View.VISIBLE
                dcontent.visibility = View.INVISIBLE
                note.setText(str)
                save.visibility = View.VISIBLE
                nsave.visibility = View.INVISIBLE
                delete.visibility = View.INVISIBLE
                dcontent.text = note.text
            }
            delete.setOnClickListener {
                dcontent.visibility = View.INVISIBLE
                nsave.visibility = View.INVISIBLE
                delete.visibility = View.INVISIBLE
                note.setText("")
                note.visibility = View.VISIBLE
                save.visibility = View.VISIBLE
                deleteNote(myName)
            }
            if (dcontent.text == null) {
                title.visibility = View.VISIBLE
                dcontent.visibility = View.INVISIBLE
                nsave.visibility = View.INVISIBLE
                delete.visibility = View.INVISIBLE
                save.visibility = View.VISIBLE
                note.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun saveNote(readDay: String?) {
        var fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
            val content: String = note.text.toString()
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("WrongConstant")
    fun deleteNote(readDay: String?) {
        var fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS)
            val content = ""
            fileOutputStream.write(content.toByteArray())
            fileOutputStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}