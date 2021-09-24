package com.smriti.musicalinstrumentapp.api

import com.smriti.musicalinstrumentapp.response.LoginResponse
import com.smriti.musicalinstrumentapp.entity.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface MyApi {
    //Register User
    @POST("user/insert")
    suspend fun registerUser(@Body user: User): Response<LoginResponse>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun checkUser(
            @Field("username") username: String,
            @Field("password")password: String
    ):Response<LoginResponse>
    @FormUrlEncoded
    @POST("update/details")
    suspend fun editDetails(
        @Header("Authorization") token:String,
        @Field("name") fn:String,

        @Field("email") em:String,
        @Field("username") un:String,
        @Field("address") ad:String,
    ):Response<LoginResponse>

    @Multipart
    @PUT("change/profilePicture")
    suspend fun changeImage(
        @Header("Authorization") token:String,
        @Part profileImg: MultipartBody.Part
    ):Response<LoginResponse>
}