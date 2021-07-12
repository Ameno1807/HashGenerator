package com.example.hashgenerator.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.hashgenerator.data.model.HashModel
import com.example.hashgenerator.data.room.HashDataBase.Companion.getDatabase
import com.example.hashgenerator.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest


class HashViewModel (application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<HashModel>>
    private val repository: Repository

    init {
        val hashDao = getDatabase(application).hashDao()
        repository = Repository(hashDao)
        readAllData = repository.readAllData
    }

    fun addHash(hash: HashModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHash(hash)
        }
    }

    fun deleteHash(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHash()
        }
    }


    fun hashGenerate(text: String): String {
        val bytes = MessageDigest.getInstance("sha-256").digest(text.toByteArray())
        Log.e("TAG", "$bytes")
        return toHex(bytes)
    }

     private fun toHex(byteArray: ByteArray): String {
        Log.e("Tag", byteArray.joinToString("") { "%02x".format(it) } )
        return byteArray.joinToString("") { "%02x".format(it) }
    }
}