package com.example.hashgenerator.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hashgenerator.data.model.HashModel

@Dao
interface HashDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHash(hash: HashModel)

    @Query("SELECT * FROM hash_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<HashModel>>

    @Query("DELETE FROM hash_table")
    suspend fun deleteHash()

}