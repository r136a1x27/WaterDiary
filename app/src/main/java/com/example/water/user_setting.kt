package com.example.water

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText

/*사용자가 목표 섭취량과 한 컵의 용량을 새로 저장하는 경우 이전에 DB에 저장된 내용을 지우기 위해 필요한 변수 선언*/
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

        /*DBManager 객체를 받아옴*/
        dbManager = DBManager(this, "personnel", null, 1)

        btnRegist.setOnClickListener {
            /*사용자가 EditText에 입력한 내용을 문자열 형태로 가져옴*/
            var str_goal: String = goal.text.toString()
            var str_cup: String = cup.text.toString()

            /*만일 goal과 cup를 처음 입력하는 것이 아니라면(이전에 저장한 내용이 있다면) 새로 입력한 goal과 cup의 값으로 update되도록 설정*/
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

        /*취소 버튼을 누르는 경우, MainActivity로 이동*/
        btnCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    /*상단 메뉴를 통해 각 액티비티로 이동 가능*/
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