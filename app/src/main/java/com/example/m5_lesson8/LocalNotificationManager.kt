package com.example.m5_lesson8

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import javax.inject.Inject

class LocalNotificationManager @Inject constructor(private val application: Application) {

    init {
        createChannels()
    }
    private val builderNotification = NotificationCompat.Builder(application, Constants.CHANEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentText("I am a title")
        .setContentTitle("I'm the body of notification text")
        .setStyle(NotificationCompat.BigTextStyle())
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    @SuppressLint("MissingPermission")
    fun createNotification(){
     with(NotificationManagerCompat.from(application)){
         notify(Constants.NOTIFICATION_ID,builderNotification.build())
     }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun createChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val channel = NotificationChannel(Constants.CHANEL_ID,"Test channel", NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager : NotificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}