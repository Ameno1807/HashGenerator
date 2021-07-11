package com.example.hashgenerator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hashgenerator.data.model.HashModel
import com.example.hashgenerator.databinding.ActivityMainBinding
import com.example.hashgenerator.ui.viewModel.HashViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var messageId = intent?.extras?.getString(FireBaseService.MESSAGE_ID)
    private val hashViewModel: HashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertData()

        binding.button.setOnClickListener {
            hashViewModel.deleteHash()
        }

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            val adapter = MainAdapter()
            this.adapter = adapter
            hashViewModel.readAllData.observe(this@MainActivity, Observer { hash ->
                adapter.setData(hash)
            })
        }
    }

    private fun insertData() {
        val hash = HashModel(messageId?.toInt(),intent?.extras?.getString(FireBaseService.KEY_MESSAGE), generateHash())
        hashViewModel.addHash(hash)
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