package com.example.hashgenerator

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FireBaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.e("Tag", "Token -> $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val intent = Intent(INTENT_FILTER)
        remoteMessage.data.forEach { entity ->
            intent.putExtra(entity.key, entity.value)
        }
        startActivity(intent)
    }

    companion object {
        const val INTENT_FILTER = "INTENT"
        const val KEY_MESSAGE = "body"
    }
}