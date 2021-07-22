package com.example.hashgenerator.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.Tag
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hashgenerator.data.model.HashModel
import com.example.hashgenerator.databinding.ActivityMainBinding
import com.example.hashgenerator.ui.viewModel.HashViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val hashViewModel: HashViewModel by viewModels()
    private lateinit var pushBroadcastReceiver: BroadcastReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val shared = getSharedPreferences("count_key", MODE_PRIVATE)
        shared.edit().clear().apply()


        insertData()
        fireBase()

        binding.button.setOnClickListener {
            hashViewModel.deleteHash()
        }

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            val adapter = MainAdapter()
            this.adapter = adapter
            hashViewModel.readAllData.observe(this@MainActivity, { hash ->
                adapter.setData(hash)
            })
        }
    }

    private fun insertData() {
        pushBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val bundle = intent?.extras
                val text = bundle?.getString(FireBaseService.KEY_MESSAGE)
                val hash1 = getHash(text.toString())

                val hash = HashModel(
                    null,
                    text,
                    hash1
                )
                hashViewModel.addHash(hash)
                Log.e("Tag", "Hash -> $hash")
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(FireBaseService.INTENT_FILTER)
        registerReceiver(pushBroadcastReceiver, intentFilter)
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
        return hashViewModel.hashGenerate(body)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(pushBroadcastReceiver)
    }
}