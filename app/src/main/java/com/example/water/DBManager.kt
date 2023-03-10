package com.example.water

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        /*DB에 저장할 목표 성취량(goal), 한 컵의 용량(cup), 마신 물의 양(drinked) 선언*/
        db!!.execSQL("CREATE TABLE personnel(goal INTEGER, cup INTEGER, drinked INTEGER);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}