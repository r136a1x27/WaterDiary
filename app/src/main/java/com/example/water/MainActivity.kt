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

/*앱이 처음 실행될 때만 Toast 메시지를 띄우기 위해 필요한 변수 선언*/
var aa = 0

class MainActivity : AppCompatActivity() {

    lateinit var mainImage: ImageButton
    lateinit var btnAdd: Button
    lateinit var btnAlarm: Button
    lateinit var btnReg: Button


    /*앱이 처음 실행되는 경우, 물컵 사진을 클릭하라는 Toast 메시지 출력을 위한 함수(다시 메인 화면으로 돌아올 경우에는 뜨지 않음)*/
    fun tm() {
        if(aa == 0) {
            Toast.makeText(this, "물컵 사진을 터치해 보세요.", Toast.LENGTH_SHORT).show()
            aa++
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*Toast 메시지 출력 함수 호출*/
        tm()

        mainImage = findViewById(R.id.mainImage)
        btnAdd = findViewById(R.id.btnAdd)
        btnAlarm = findViewById(R.id.btnAlarm)
        btnReg = findViewById(R.id.btnReg)

        /*목표추가 버튼(btnAdd) 클릭 시 user_setting 액티비티로 이동*/
        btnAdd.setOnClickListener {
            val intent = Intent(this, user_setting::class.java)
            startActivity(intent)
        }

        /*기록하기 버튼(btnReg) 클릭 시 WaterRecord 액티비티로 이동*/
        btnReg.setOnClickListener {
            val intent = Intent(this, WaterRecord::class.java)
            Log.i("테스트", "기록하기 화면 연결")
            startActivity(intent)
        }

        /*알람추가 버튼(btnAlarm) 클릭 시 Alarm 액티비티로 이동*/
        btnAlarm.setOnClickListener {
            val intent = Intent(this, Alarm::class.java)
            startActivity(intent)
        }

        /*물컵 이미지(mainImage) 클릭 시 Calendar2 액티비티로 이동*/
        mainImage.setOnClickListener {
            val intent = Intent(this, Calendar2::class.java)
            startActivity(intent)
        }
    }

    /*버튼을 누르지 않아도 상단 메뉴를 통해 각 액티비티로 이동 가능*/
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