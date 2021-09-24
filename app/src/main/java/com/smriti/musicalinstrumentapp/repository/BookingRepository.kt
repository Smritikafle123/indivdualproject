package com.smriti.musicalinstrumentapp.repository

import com.smriti.musicalinstrumentapp.api.BookingAPI
import com.smriti.musicalinstrumentapp.api.MyApiRequest
import com.smriti.musicalinstrumentapp.api.ServiceBuilder
import com.smriti.musicalinstrumentapp.entity.BookingInstrument
import com.smriti.musicalinstrumentapp.response.BookingResponse

class BookingRepository(): MyApiRequest() {
    val bookingAPI =ServiceBuilder.buildService(BookingAPI::class.java)

    suspend fun bookInstrument(booking:BookingInstrument): BookingResponse
    {
        return apiRequest {
            bookingAPI.bookProduct(ServiceBuilder.token!!,booking)
        }
    }

    suspend fun retrieveBooking():BookingResponse
    {
        return apiRequest {
            bookingAPI.retrieveBooking(ServiceBuilder.token!!)
        }
    }

    suspend fun updateBooking(id:String,qty:Int):BookingResponse
    {
        return apiRequest {
            bookingAPI.updateBooking(ServiceBuilder.token!!,id,qty)
        }
    }

    suspend fun deleteBooking(id:String):BookingResponse
    {
        return apiRequest {
            bookingAPI.deleteBooking(ServiceBuilder.token!!,id)
        }
    }
}