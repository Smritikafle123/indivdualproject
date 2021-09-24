package com.smriti.musicalinstrumentapp.repository

import com.smriti.musicalinstrumentapp.api.MyApi
import com.smriti.musicalinstrumentapp.api.MyApiRequest
import com.smriti.musicalinstrumentapp.api.ServiceBuilder
import com.smriti.musicalinstrumentapp.entity.User
import com.smriti.musicalinstrumentapp.response.LoginResponse
import okhttp3.MultipartBody

class UserRepository: MyApiRequest() {

    val myApi = ServiceBuilder.buildService(MyApi::class.java)
    suspend fun registerUser(user: User): LoginResponse{
        return apiRequest {
            myApi.registerUser(user)
        }
    }

    suspend fun checkUser(username: String, password: String):LoginResponse{
        return apiRequest {
            myApi.checkUser(username,password)
        }
    }
    suspend fun userEdit(fln:String,em:String,un:String,ad:String):LoginResponse
    {
        return apiRequest {
            myApi.editDetails(ServiceBuilder.token!!,fln,em,un,ad)
        }
    }


    suspend fun editImage(profileImg: MultipartBody.Part):LoginResponse
    {
        return  apiRequest {
            myApi.changeImage(ServiceBuilder.token!!,profileImg)
        }
    }
}