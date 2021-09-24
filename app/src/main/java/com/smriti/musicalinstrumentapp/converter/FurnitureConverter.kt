package com.smriti.musicalinstrumentapp.converter

import androidx.room.TypeConverter
import com.smriti.musicalinstrumentapp.entity.Product

class InstrumentConverter {
    companion object{
        @TypeConverter
        @JvmStatic
        fun fromBookingInstrument(record:Product):String
        {
            return record._id
        }

        @TypeConverter
        @JvmStatic
        fun toBookingInstrument(recordId:String):Product
        {
            return recordId.let { Product(it) }
        }
    }
}