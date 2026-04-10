package com.airpods4anc

import android.app.*
import android.content.Intent
import android.os.IBinder

class AirPodsService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notification = Notification.Builder(this, "airpods")
            .setContentTitle("AirPods aktiv")
            .setContentText("App läuft im Hintergrund")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .build()

        startForeground(1, notification)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
