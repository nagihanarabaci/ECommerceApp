package com.ao.e_commerce.utils


import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ao.e_commerce.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("Token", "Refreshed_token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            showNotification(
                remoteMessage.notification!!.title,
                remoteMessage.notification!!.body
            )
        }
    }

    fun showNotification(title: String?, message: String?) {
        val channel_id = "notification_channel"
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, channel_id)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setVibrate(
                    longArrayOf(
                        1000, 1000, 1000,
                        1000, 1000
                    )
                )
                .setOnlyAlertOnce(true)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            channel_id, "notification_channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(
            notificationChannel
        )
        notificationManager.notify(0, builder.build())
    }
}



