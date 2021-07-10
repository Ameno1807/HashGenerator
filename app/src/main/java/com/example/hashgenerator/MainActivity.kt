package com.example.hashgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hashgenerator.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var list: MutableList<HashModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var list1 = arrayListOf(HashModel("toHash: ${intent?.extras?.getString(FireBaseService.KEY_MESSAGE)}, hash value: ${generateHash()}"))
        list = list1.toMutableList()


        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            val adapter = MainAdapter()
            this.adapter = adapter
            adapter.submitList(list)
        }
    }


    private fun generateHash(): String {
        fireBase()
        val extras = intent?.extras
        val text = extras?.getString(FireBaseService.KEY_MESSAGE)?.let { body ->
            Log.e("Tag", "Message -> $body")
            return getHash(body)
        }

        return text.toString()
    }

    private fun fireBase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            Log.e("Tag", "Token -> $token")
        })
    }

    private fun getHash(body: String): String {
        return hashGenerate(body)
    }

    private fun hashGenerate(text: String): String {
        val bytes = MessageDigest.getInstance("sha-256").digest(text.toByteArray())
        Log.e("TAG", "$bytes")
        return toHex(bytes)
    }

    private fun toHex(byteArray: ByteArray): String {
        Log.e("Tag", byteArray.joinToString("") { "%02x".format(it) } )
        return byteArray.joinToString("") { "%02x".format(it) }
    }
}