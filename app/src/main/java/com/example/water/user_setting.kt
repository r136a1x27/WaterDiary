package com.example.water

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText

var count = 1

class user_setting : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var btnRegist: Button
    lateinit var btnCancel: Button
    lateinit var goal: EditText
    lateinit var cup: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setting)

        btnRegist = findViewById(R.id.btnRegister)
        btnCancel = findViewById(R.id.btnCancel)
        goal = findViewById(R.id.goal)
        cup = findViewById(R.id.cup)

        dbManager = DBManager(this, "personnel", null, 1)

        btnRegist.setOnClickListener {
            var str_goal: String = goal.text.toString()
            var str_cup: String = cup.text.toString()

            if(count == 1) {
                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("INSERT INTO personnel VALUES ('" + str_goal + "', '" + str_cup + "' , 0);")
                sqlitedb.close()
                count++
            } else {
                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("UPDATE personnel SET goal = '" + str_goal + "', cup = '" + str_cup + "', drinked = 0;")
                sqlitedb.close()
            }

        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_goal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_reg -> {
                val intent = Intent(this, WaterRecord::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_alarm -> {
                val intent = Intent(this, Alarm::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}