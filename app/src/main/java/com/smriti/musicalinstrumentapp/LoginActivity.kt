package com.smriti.musicalinstrumentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.smriti.musicalinstrumentapp.api.ServiceBuilder
import com.smriti.musicalinstrumentapp.db.ProductDB
import com.smriti.musicalinstrumentapp.db.StudentDB
import com.smriti.musicalinstrumentapp.entity.User
import com.smriti.musicalinstrumentapp.notification.NotificationSender
import com.smriti.musicalinstrumentapp.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var etPassword: EditText
    private lateinit var etUsername: EditText
    private lateinit var btnregnew:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //val context = this
        btnLogin = findViewById(R.id.btnLogin)
        etPassword = findViewById(R.id.etPassword)
        etUsername = findViewById(R.id.etUsername)
        btnregnew = findViewById(R.id.btnregnew)


        btnLogin.setOnClickListener() {
            login()
        }
        btnregnew.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)

        }
    }

    private fun login() {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Please enter your username")
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please enter your password")
        } else {

//          var user: User? = null
            CoroutineScope(Dispatchers.IO).launch {

                try {
                    val repository = UserRepository()
                    val response = repository.checkUser(username, password)
                    if (response.success == true) {
                        NotificationSender(this@LoginActivity,"Logged in","").createHighPriority()
                        var instance = ProductDB.getInstance(this@LoginActivity).getUserDAO()
                        instance.deleteUser()
                        instance.registerUser(response.data!!)
                        savesharedPref()
                        ServiceBuilder.token = "Bearer ${response.token}"
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        finish()
                    }

                } catch (io: IOException) {
                    println(io.printStackTrace())
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                this@LoginActivity,
                                "Please enter correct credentials",
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }


    }
    fun savesharedPref() {
        val sharedPref = getSharedPreferences("credentials",
            MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", etUsername.text.toString())
        editor.putString("password", etPassword.text.toString())
        editor.apply()


    }
}

