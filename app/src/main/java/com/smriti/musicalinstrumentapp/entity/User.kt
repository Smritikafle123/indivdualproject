package com.smriti.musicalinstrumentapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var _id: String = "",
    var name: String? = null,
    var email: String? = null,
    var address: String? = null,
    var phone: String? =null,
    var username: String? = null,
    var password: String? = null,
    var profileImg:String?=null
)

{
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0
}