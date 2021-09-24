package com.smriti.musicalinstrumentapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smriti.musicalinstrumentapp.converter.InstrumentConverter
import com.smriti.musicalinstrumentapp.dao.ProductDAO
import com.smriti.musicalinstrumentapp.dao.UserDAO
import com.smriti.musicalinstrumentapp.entity.BookingInstrument
import com.smriti.musicalinstrumentapp.entity.Product
import com.smriti.musicalinstrumentapp.entity.User

@Database(
    entities = [(Product::class),(User::class),(BookingInstrument::class)],
    version = 5,
    exportSchema = false
)
@TypeConverters(InstrumentConverter::class)
abstract class ProductDB : RoomDatabase() {
    abstract fun getUserDAO():UserDAO

    abstract fun getProductDAO(): ProductDAO

    companion object {
        @Volatile
        private var instance: ProductDB? = null
        fun getInstance(context: Context): ProductDB {
            if (instance == null) {
                synchronized(ProductDB::class) {
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }
        private fun buildDatabase(context: Context): ProductDB {
            return Room.databaseBuilder(
                context.applicationContext,
                ProductDB::class.java,
                "MusicHub"
            ).fallbackToDestructiveMigration().build()
        }
    }
}
