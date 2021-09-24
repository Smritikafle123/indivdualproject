package com.smriti.musicalinstrumentapp.repository

import com.smriti.musicalinstrumentapp.api.MyApiRequest
import com.smriti.musicalinstrumentapp.api.ProductApi
import com.smriti.musicalinstrumentapp.api.ServiceBuilder
import com.smriti.musicalinstrumentapp.entity.Product
import com.smriti.musicalinstrumentapp.response.AddProductResponse
import com.smriti.musicalinstrumentapp.response.ProductResponse
import okhttp3.MultipartBody

class ProductRepository : MyApiRequest() {
    val egApi = ServiceBuilder.buildService(ProductApi::class.java)

    suspend fun addProduct(name: String, desc: String, price: String, rating: String, image: MultipartBody.Part): AddProductResponse {
        return apiRequest {
            egApi.addProduct(name, desc, price, rating, image)
        }
    }


    suspend fun getProducts(): ProductResponse {
        return apiRequest {
            egApi.getProduct()
        }
    }
}
