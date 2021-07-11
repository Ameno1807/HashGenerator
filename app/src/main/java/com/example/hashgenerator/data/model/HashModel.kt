package com.example.hashgenerator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hash_table")
data class HashModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val hashName: String?,
    val hashValue: String?
    )