package com.smriti.musicalinstrumentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
//import com.smriti.musicalinstrumentapp.db.StudentDB
import com.smriti.musicalinstrumentapp.entity.User
import com.smriti.musicalinstrumentapp.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var btnSignUp: Button
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirm: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAddress: EditText
    private lateinit var tvLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //val context = this

        btnSignUp = findViewById(R.id.btnSignUp)
        etName = findViewById(R.id.etName)
        etPhone = findViewById(R.id.etPhone)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirm = findViewById(R.id.etConfirm)
        etEmail = findViewById(R.id.etEmail)
        etAddress = findViewById(R.id.etAddress)
        tvLogin = findViewById(R.id.tvLogin)


        btnSignUp.setOnClickListener() {
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val address = etAddress.text.toString()
            val email = etEmail.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirm.text.toString()

            if (TextUtils.isEmpty(name)) {
                etName.setError("Please enter your name")
            } else if (TextUtils.isEmpty(phone)) {
                etPhone.setError("Please enter phone number")
            } else if (TextUtils.isEmpty(address)) {
                etAddress.setError("Please enter your address")
            } else if (TextUtils.isEmpty(email)) {
                etEmail.setError("Please enter your email!!")
            } else if (TextUtils.isEmpty(username)) {
                etUsername.setError("Please enter your username")
            } else if (TextUtils.isEmpty(password)) {
                etPassword.setError("Please enter your password")
            } else {

                val user =
                        User(name = name, email = email, username = username, password = password, address = address, phone = phone)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val userRepository = UserRepository()
                        val response = userRepository.registerUser(user)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                        this@SignUpActivity,
                                        "User Registered", Toast.LENGTH_SHORT
                                ).show()
                                startActivity(
                                        Intent(
                                                this@SignUpActivity,
                                                LoginActivity::class.java
                                        )
                                )
                                finish()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    this@SignUpActivity,
                                    ex.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }


            }
        }
        tvLogin.setOnClickListener{
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}