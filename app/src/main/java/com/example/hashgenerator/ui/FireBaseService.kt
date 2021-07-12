package com.example.hashgenerator.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Intent.*
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.hashgenerator.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FireBaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.e("Tag", "Token -> $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        if (remoteMessage.notification != null) {
            showNotification(remoteMessage.notification!!.title, remoteMessage.notification!!.body)
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            remoteMessage.data.forEach { entity ->
                    intent.putExtra(entity.key, entity.value)
                    startActivity(intent)
            }
            Log.e("Tag", "Token -> $intent")
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            "channel_id", "Test Notification Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.description = "My test notification channel"
        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }


    private fun showNotification(title: String?, body: String?) {
        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, MainActivity::class.java)

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, "channel_id")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentIntent(
                    PendingIntent.getActivity(
                        applicationContext,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
        notificationManager.notify(1, builder.build())
    }


    companion object {
        const val KEY_MESSAGE = "body"
        const val MESSAGE_ID = "id"
    }
}