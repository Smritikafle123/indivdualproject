package com.smriti.musicalinstrumentapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smriti.musicalinstrumentapp.notification.NotificationSender
import java.util.*

class DashboardActivity : AppCompatActivity(),SensorEventListener {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor?=null
    private var sensor2: Sensor?=null
    private var sensor3: Sensor?=null
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.frgDashboard) as NavHostFragment
        navController = navHostFragment.findNavController()
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if(!checkSensor())
        {
            return
        }
        else
        {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensor3 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(this,sensor2,SensorManager.SENSOR_DELAY_NORMAL)
            sensorManager.registerListener(this,sensor3,SensorManager.SENSOR_DELAY_NORMAL)
        }
        requestPermission()
        bottomNavigationView.setupWithNavController(navController)

    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@DashboardActivity,
            permissions, 1
        )
    }


    private fun checkSensor():Boolean
    {
        var flag = true
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null)
        {
            flag = false
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null)
        {
            flag = false
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null)
        {
            flag = false
        }
        return flag
    }




    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_PROXIMITY)
        {
            val values = event!!.values[0]
            if(values <= 4)
            {
                val logOut = Logout(this,this)
                logOut.logout()
            }
        }
        if(event!!.sensor.type == Sensor.TYPE_LIGHT)
        {
            val values = event!!.values[0]
            if(values > 40)
            {
                NotificationSender(this,"High Light can damage your eyes","").createHighPriority()
            }
        }
        if(event!!.sensor.type == Sensor.TYPE_ACCELEROMETER)
        {
            val values = event!!.values
            val x_axis = values[0]
            if(x_axis >= 7 && x_axis < 10)
            {
                var intent = Intent(this,SignUpActivity::class.java)
                startActivity(intent)
            }
            else if (x_axis <= -7 && x_axis > -10)
            {
                var intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }
   }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

}
