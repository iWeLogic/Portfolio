package com.iwelogic

import android.app.*
import dagger.hilt.android.*

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val mChannel = NotificationChannel(
            resources.getString(R.string.notification_channel),
            resources.getString(R.string.notification_channel),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(mChannel)
    }
}


