package com.smriti.musicalinstrumentapp.connection

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


@Suppress("Deprecation")
class Connection(val context: Context) {

    fun getConnection():Boolean
    {
        var connection = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) as NetworkInfo
        val mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) as NetworkInfo
        println(connectivityManager.activeNetworkInfo)
        if((wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected()))
        {
            connection = true
        }
        return connection
    }
}