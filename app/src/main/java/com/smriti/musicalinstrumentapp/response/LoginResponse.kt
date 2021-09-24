package com.smriti.musicalinstrumentapp.response

import com.smriti.musicalinstrumentapp.entity.User

data class LoginResponse(
        val success: Boolean? = null,
        val token : String? = null,
        val message : String? = null,
        val data: User?=null
)