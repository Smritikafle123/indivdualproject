package com.smriti.musicalinstrumentapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.smriti.musicalinstrumentapp.R
import com.smriti.musicalinstrumentapp.api.ServiceBuilder
import com.smriti.musicalinstrumentapp.connection.CheckConnection
import com.smriti.musicalinstrumentapp.db.ProductDB
import com.smriti.musicalinstrumentapp.notification.NotificationSender
import com.smriti.musicalinstrumentapp.repository.UserRepository
import kotlinx.coroutines.*
import java.io.IOException

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        CheckConnection(this@SplashScreenActivity).checkRegisteredNetwork()
        var shared = getSharedPreferences("credentials",Context.MODE_PRIVATE)
        var username = shared.getString("username","")
        var password = shared.getString("password","")
        println(username)

        if(username != "" && password!= "")
        {
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val repository = UserRepository()
                        val response = repository.checkUser(username!!, password!!)
                        if (response.success == true) {
                            NotificationSender(this@SplashScreenActivity,"Logged in","").createHighPriority()
                            var instance = ProductDB.getInstance(this@SplashScreenActivity).getUserDAO()
                            instance.deleteUser()
                            instance.registerUser(response.data!!)

                            ServiceBuilder.token = "Bearer ${response.token}"
                            startActivity(Intent(this@SplashScreenActivity, DashboardActivity::class.java))
                            finish()
                        }



                    } catch (io: IOException) {
                        println(io.printStackTrace())
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@SplashScreenActivity,
                                "Please enter correct credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        else
        {
                println("Here i am")
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                finish()

        }

    }
    override fun onDestroy() {
        super.onDestroy()
        CheckConnection(this).unregisteredNetwork()
    }
}