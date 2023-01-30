package com.example.water

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var mainImage: ImageButton
    lateinit var btnAdd: Button
    lateinit var btnAlarm: Button
    lateinit var btnReg: Button
    var aa = true

    fun tm() {
        if(aa) {
            Toast.makeText(this, "물컵 사진을 터치해 보세요.", Toast.LENGTH_SHORT).show()
            aa = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tm()

        mainImage = findViewById(R.id.mainImage)
        btnAdd = findViewById(R.id.btnAdd)
        btnAlarm = findViewById(R.id.btnAlarm)
        btnReg = findViewById(R.id.btnReg)

        btnAdd.setOnClickListener {
            val intent = Intent(this, user_setting::class.java)
            startActivity(intent)
        }

        btnReg.setOnClickListener {
            val intent = Intent(this, WaterRecord::class.java)
            Log.i("테스트", "기록하기 화면 연결")
            startActivity(intent)
        }

        btnAlarm.setOnClickListener {
            val intent = Intent(this, Alarm::class.java)
            startActivity(intent)
        }

        mainImage.setOnClickListener {
            val intent = Intent(this, Calendar2::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_goal -> {
                val intent = Intent(this, user_setting::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_reg -> {
                val intent = Intent(this, WaterRecord::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_home -> {
                val intent = Intent(this, Alarm::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}