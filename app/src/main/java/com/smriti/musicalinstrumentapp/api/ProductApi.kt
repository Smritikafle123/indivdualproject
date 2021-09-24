package com.smriti.musicalinstrumentapp.api

import com.smriti.musicalinstrumentapp.entity.Product
import com.smriti.musicalinstrumentapp.response.AddProductResponse
import com.smriti.musicalinstrumentapp.response.ProductResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {
    //for inserting product
    @Multipart
    @POST("product/insert")
    suspend fun addProduct(
            @Part("pname") name : String,
            @Part("pdesc") desc : String,
            @Part("pprice") price : String,
            @Part("prating") rating : String,
            @Part pimage : MultipartBody.Part
    ): Response<AddProductResponse>

//    @FormUrlEncoded
//    @POST("account/login")
//    suspend fun loginUser(
//        @Field("username") username: String,
//        @Field("password") password: String
//    ): Response<LoginResponse>

    @GET("product/showAll")
    suspend fun getProduct(): Response<ProductResponse>
}