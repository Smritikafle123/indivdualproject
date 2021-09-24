package com.smriti.musicalinstrumentapp.response

import com.smriti.musicalinstrumentapp.entity.BookingInstrument

data class BookingResponse(val success:Boolean?=null,val message:String?=null,val data:MutableList<BookingInstrument>?=null)