package com.smriti.musicalinstrumentapp.api

import com.smriti.musicalinstrumentapp.entity.BookingInstrument
import com.smriti.musicalinstrumentapp.response.BookingResponse
import retrofit2.Response
import retrofit2.http.*

interface BookingAPI {
    @POST("book/music")
    suspend fun bookProduct(
        @Header("Authorization") token:String,
        @Body record:BookingInstrument
    ): Response<BookingResponse>


    @GET("retrieve/myBookings")
    suspend fun retrieveBooking(
        @Header("Authorization") token:String
    ): Response<BookingResponse>

    @FormUrlEncoded
    @POST("update/booking")
    suspend fun updateBooking(
        @Header("Authorization") token:String,
        @Field("pid") id:String,
        @Field("qty") qty:Int
    ): Response<BookingResponse>

    @FormUrlEncoded
    @POST("delete/booking")
    suspend fun deleteBooking(
        @Header("Authorization") token:String,
        @Field("pid") id:String
    ): Response<BookingResponse>
}