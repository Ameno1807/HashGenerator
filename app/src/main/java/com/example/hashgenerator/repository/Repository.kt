package com.example.hashgenerator.repository

import androidx.lifecycle.LiveData
import com.example.hashgenerator.data.model.HashModel
import com.example.hashgenerator.data.room.HashDao


class Repository (
    private val hashDao: HashDao
) {
    val readAllData: LiveData<List<HashModel>> = hashDao.readAllData()

     suspend fun addHash(hash: HashModel) {
        hashDao.addHash(hash)
    }

    suspend fun deleteHash() {
        hashDao.deleteHash()
    }

}