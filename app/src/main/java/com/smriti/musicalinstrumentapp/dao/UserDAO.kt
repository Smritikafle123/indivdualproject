package com.smriti.musicalinstrumentapp.dao


import androidx.room.Dao
import androidx.room.Insert

import androidx.room.Query

import com.smriti.musicalinstrumentapp.entity.User


@Dao
interface UserDAO {
    // insert user
    @Insert
    suspend fun registerUser(user : User)

    @Query("select * from User")
    suspend fun retrieveUser():User

    @Query("delete from User")
    suspend fun deleteUser()
}