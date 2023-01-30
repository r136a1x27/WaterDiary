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

        /*calendar를 클릭하는 경우, 사용자의 소감을 입력하여 저장할 수 있도록 title과 note, 저장 버튼이 보이게 함 */
        /*이미 내용이 저장된 날짜라면 입력을 받는 note부분이 빈칸이 되고 달력 내용을 조회하는 함수 호출*/
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

        /*저장 버튼 클릭 시 달력 내용을 추가하는 함수를 통해 내용 저장*/
        /*사용자가 입력을 하는 note와 저장 버튼을 보이지 않게 하고, 사용자가 입력한 내용을 dcontent(TextView)를 통해 보여줌*/
        /*보이지 않던 삭제, 수정 버튼이 보이게 됨*/
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

    /*달력 내용을 조회, 수정하기 위한 함수*/
    fun checkNote(cYear: Int, cMonth: Int, cDay: Int, myID: String) {
        /*사용자가 입력한 내용을 저장할 파일 이름 설정*/
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
            /*수정 버튼 클릭 시 note(EditText)에 저장되어있던 문자열이 출력됨*/
            /*저장 버튼이 다시 활성화*/
            nsave.setOnClickListener {
                note.visibility = View.VISIBLE
                dcontent.visibility = View.INVISIBLE
                note.setText(str)
                save.visibility = View.VISIBLE
                nsave.visibility = View.INVISIBLE
                delete.visibility = View.INVISIBLE
                dcontent.text = note.text
            }
            /*삭제 버튼 클릭 시 note 내용이 빈칸으로 설정됨*/
            /*해당 날짜에 저장되어있던 내용 파일 역시 삭제됨*/
            delete.setOnClickListener {
                dcontent.visibility = View.INVISIBLE
                nsave.visibility = View.INVISIBLE
                delete.visibility = View.INVISIBLE
                note.setText("")
                note.visibility = View.VISIBLE
                save.visibility = View.VISIBLE
                deleteNote(myName)
            }
            /*소감 내용이 null 값이라면 소감을 입력하지 않았을 때와 같은 상태로 설정*/
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

    /*사용자가 입력한 내용을 저장하는 함수*/
    /*note(EditText)에 입력한 문자열이 저장됨*/
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

    /*입력된 내용을 삭제하는 함수*/
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