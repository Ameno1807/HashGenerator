package com.example.hashgenerator.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Intent.*
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import android.preference.PreferenceManager
import android.preference.PreferenceManager.getDefaultSharedPreferences
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

        /*val prefs = getSharedPreferences("count_key", MODE_PRIVATE)
        var count = prefs.getInt("count_key", 0)

        count += 1*/

        showNotification(remoteMessage.data["body"])

       /* val editor = prefs.edit()
        editor.putInt("count_key", count)
        editor.apply()*/

        val intent = Intent(INTENT_FILTER)
        remoteMessage.data.forEach { entity ->
            intent.putExtra(entity.key, entity.value)
        }
        sendBroadcast(intent)
        Log.e("Tag", "Body -> ${remoteMessage.data["body"]}")

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


    private fun showNotification(body: String?) {


        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(applicationContext, MainActivity::class.java)



        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, "channel_id")
                .setContentTitle("Hash")
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
        notificationManager.notify(0, builder.build())



    }

    companion object {
        const val INTENT_FILTER = "PUSH_EVENT"
        const val KEY_MESSAGE = "body"
        const val MESSAGE_ID = "id"
    }
}