package com.example.hashgenerator.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.hashgenerator.data.model.HashModel
import com.example.hashgenerator.data.room.HashDataBase.Companion.getDatabase
import com.example.hashgenerator.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



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
}