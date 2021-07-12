package com.example.hashgenerator.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hashgenerator.data.model.HashModel

@Database(entities = [HashModel::class], version = 1, exportSchema = false)
abstract class HashDataBase : RoomDatabase() {

    abstract fun hashDao(): HashDao
    companion object {
        @Volatile
        private var INSTANCE: HashDataBase? = null

        fun getDatabase(context: Context): HashDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HashDataBase::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}