package com.airpods.ultimate

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

object NotificationHelper {

    fun show(context: Context, text: String) {

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "airpods",
            "AirPods",
            NotificationManager.IMPORTANCE_LOW
        )

        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, "airpods")
            .setContentTitle("AirPods")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.stat_sys_data_bluetooth)
            .build()

        manager.notify(1, notification)
    }
}
