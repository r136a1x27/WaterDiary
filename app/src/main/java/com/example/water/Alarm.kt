package com.example.water

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import com.example.water.databinding.AlarmBinding
import java.text.DateFormat
import java.util.*
class Alarm : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {



    private lateinit var binding: AlarmBinding
    private val TAG = "requestCode"
    var requestCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //binding 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.alarm)

        //알람 설정
        binding.timeBtn.setOnClickListener {

            var timePicker = TimePickerFragment()
            //시계 호출
            timePicker.show(supportFragmentManager, "Time Picker")

        }

        //알람 취소
        binding.alarmCancelBtn.setOnClickListener {
            //알람 취소 함수
            cancelAlarm()
        }
    }

    //시간 정하면 호출되는 함수
    override fun onTimeSet(timePicker: TimePicker?, hourofDay: Int, minute: Int) {

        var c= Calendar.getInstance()

        //시간 설정
        c.set(Calendar.HOUR_OF_DAY, hourofDay)//시
        c.set(Calendar.MINUTE, minute)//분
        c.set(Calendar.SECOND, 0)//초


        //화면에 시간 지정
        updateTimeText(c)

        //알람 설정
        startAlarm(c)
    }

    private fun updateTimeText(c: Calendar) {

        var curTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)

        binding.timeText.append("\n알람 시간: ")
        binding.timeText.append(curTime)
    }

    private fun startAlarm(c: Calendar) {

        //알람 매니저 선언
        var alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager


        requestCode++


        Log.i(TAG, "$requestCode")

        var intent = Intent(this, AlertReceiver::class.java)

        //데이터 담기
        var curTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)
        intent.putExtra("time", curTime)

        var pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0)

        //설정시간이 현재시간 이후라면 설정
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)

    }

    //알람 취소
    private fun cancelAlarm() {

        //알람매니저 선언
        var alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var intent = Intent(this, AlertReceiver::class.java)

        var pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)

        alarmManager.cancel(pendingIntent)
        binding.timeText.text = ""

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_alarm, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_goal -> {
                val intent = Intent(this, user_setting::class.java)
                startActivity(intent)
                return true
            }
            /*R.id.action_reg -> {
                val intent = Intent(this, /*기록하기 액티비티 이름*/::class.java)
                startActivity(intent)
                return true
            }*/
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}