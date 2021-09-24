package com.smriti.musicalinstrumentapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smriti.musicalinstrumentapp.dao.UserDAO
import com.smriti.musicalinstrumentapp.entity.User

@Database(
    entities = [(User::class)],
    version = 1,
)
abstract class StudentDB : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: StudentDB? = null

        fun getInstance(context: Context): StudentDB {
            if (instance == null) {
                synchronized(StudentDB::class) {
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                StudentDB::class.java,
                "MusicDB"
            ).build()
    }
}