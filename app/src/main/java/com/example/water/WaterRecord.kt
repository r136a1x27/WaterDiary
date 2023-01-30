package com.example.water

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class WaterRecord : AppCompatActivity() {
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var goalTextView: TextView
    lateinit var drink100mlButton: Button
    lateinit var drinkStatusTextView: TextView

    var beforeCup: Int = 0
    var goal: Int = 0
    var cup: Int = 0

    lateinit var cupImage: ImageView
    lateinit var percentText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waterrecord)
        
        // DB와 연결
        dbManager = DBManager(this, "personnel", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM personnel;",null)

        if(cursor.moveToNext()){
            beforeCup = cursor.getInt(cursor.getColumnIndexOrThrow("drinked"))
            goal = cursor.getInt(cursor.getColumnIndexOrThrow("goal"))
        }

        // layout과 위젯 연결
        goalTextView = findViewById<TextView>(R.id.goalText)
        drink100mlButton = findViewById<Button>(R.id.drink100ml)
        drinkStatusTextView = findViewById<TextView>(R.id.drinkStatus)

        cupImage = findViewById<ImageView>(R.id.cupImage)
        percentText = findViewById<TextView>(R.id.percentText)

        // 이미지 설정
        changeImage(goal, beforeCup)

        drinkStatusTextView.setText("현재까지 마신 물 "+beforeCup+"ml 💧")
        // 목표치로 텍스트 수정
        goalTextView.setText("오늘의 목표: "+goal+"ml 🥛")

        cursor.close()
        sqlitedb.close()

        drink100mlButton.setOnClickListener {
            // 목표가 설정되지 않은 경우 알림
            if(goal == 0){
                Toast.makeText(this, "목표를 먼저 추가해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sqlitedb = dbManager.writableDatabase
            var cursor: Cursor
            cursor = sqlitedb.rawQuery("SELECT * FROM personnel;",null)

            if(cursor.moveToNext()){
                beforeCup = cursor.getInt(cursor.getColumnIndexOrThrow("drinked"))
                cup = cursor.getInt(cursor.getColumnIndexOrThrow("cup"))
            }

            var afterCup: Int = beforeCup+cup
            sqlitedb.execSQL("UPDATE personnel SET drinked ="+afterCup)
            
            // 현재 상태도 수정
            drinkStatusTextView.setText("현재까지 마신 물 "+afterCup+"ml 💧")

            changeImage(goal, afterCup)

            cursor.close()
            sqlitedb.close()
            dbManager.close()
        }
    }

    fun changeImage(goal: Int, water: Int){
        var percent = 0.0
        if(water!=0) {
            percent = (water.toDouble() / goal.toDouble()) * 100
        }
        percentText.setText("${percent.toInt()}%")

        when{
            percent >= 100 -> cupImage.setImageResource(R.drawable.cup2_100)
            percent >= 70 -> cupImage.setImageResource(R.drawable.cup2_70)
            percent >= 50 -> cupImage.setImageResource(R.drawable.cup2_50)
            percent >= 30 -> cupImage.setImageResource(R.drawable.cup2_30);
            else -> cupImage.setImageResource(R.drawable.cup2_0);
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_record, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_goal -> {
                val intent = Intent(this, user_setting::class.java)
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