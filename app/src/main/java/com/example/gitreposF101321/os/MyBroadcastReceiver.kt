package com.example.gitreposF101321.os

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if (intent.action == "com.example.gitreposF101321.myBroadcastMessage") {
            Log.i("broadcast message", "onReceive: broadcast message is received")
            Toast.makeText(context, "Broadcast message is received", Toast.LENGTH_LONG).show()
        }
    }
}