package com.smriti.musicalinstrumentapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookingInstrument(
    @PrimaryKey
    val _id:String = "",
    var product_id:Product? = null,
    var quantity:Int = 0,
    var price:Int = 0,
    var booked_At:String?=null,



)