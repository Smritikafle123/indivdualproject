package com.smriti.musicalinstrumentapp.connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.smriti.musicalinstrumentapp.api.ServiceBuilder

class ConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val conn = Connection(context)
        if(conn.getConnection())
        {
            ServiceBuilder.online = true
            Toast.makeText(context, "You are online", Toast.LENGTH_SHORT).show()
        }
        else
        {
            ServiceBuilder.online = false
            Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()

        }
    }
}