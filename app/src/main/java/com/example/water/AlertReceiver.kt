package com.example.water

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class AlertReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        var notificationHelper:NotificationHelper= NotificationHelper(context)

        //넘어온 데이터 변수에 담기
        var time=intent?.extras?.getString("time")

        var nb: NotificationCompat.Builder=notificationHelper.getChannelNotification(time)

        //알림 호출
        notificationHelper.getManager().notify(1,nb.build())
    }
}