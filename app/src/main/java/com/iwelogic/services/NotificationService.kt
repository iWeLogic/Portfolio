package com.iwelogic.services

import android.app.Notification.*
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.*
import androidx.core.content.ContextCompat
import androidx.core.net.*
import com.google.firebase.messaging.*
import com.iwelogic.R
import com.iwelogic.core.Const.KEY_DESTINATION
import com.iwelogic.core.Const.KEY_ID
import com.iwelogic.core.Const.URL


class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.notification == null) {
            return
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(this,   resources.getString(R.string.notification_channel))
        mBuilder.setContentTitle(remoteMessage.notification?.title)
        mBuilder.setContentText(remoteMessage.notification?.body)
        if(!remoteMessage.data[KEY_DESTINATION].isNullOrEmpty()){
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = "$URL/${remoteMessage.data[KEY_DESTINATION]}/${remoteMessage.data[KEY_ID]}".toUri()
            }
            val pendingIntent = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(
                    1,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }
            mBuilder.setContentIntent(pendingIntent)
        }

        mBuilder.setSmallIcon(R.drawable.ic_notification)
        mBuilder.color = ContextCompat.getColor(baseContext, R.color.black)
        mBuilder.setAutoCancel(true)
        mBuilder.priority = NotificationCompat.PRIORITY_MAX
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        mBuilder.setSound(sound)
        mBuilder.setDefaults(DEFAULT_ALL)
        notificationManager.notify(System.currentTimeMillis().toInt(), mBuilder.build())
    }
}