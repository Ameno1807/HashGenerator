package com.example.hashgenerator.ui

import android.os.Bundle
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
            hashViewModel.readAllData.observe(this@MainActivity, { hash ->
                adapter.setData(hash)
            })
        }
    }

    private fun insertData() {
        val hash = HashModel(
            messageId?.toInt(),
            intent?.extras?.getString(FireBaseService.KEY_MESSAGE),
            generateHash()
        )

        if (intent?.extras?.getString(FireBaseService.KEY_MESSAGE) != null) {
            hashViewModel.addHash(hash)
        } else Log.e("Tag", "Уведомление null")
    }


    private fun generateHash(): String {
        fireBase()
            val bundle = intent.extras
            val text = bundle?.getString(FireBaseService.KEY_MESSAGE)
            return getHash(text.toString())
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
}