package com.aidul23.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aidul23.notification.Constant.CHANNEL_ID
import com.aidul23.notification.Constant.CHANNEL_NAME
import com.aidul23.notification.Constant.NOTIFICATION_ID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        //this intent take the class which we wanna show after clicking the notification
        val intent = Intent(this,MainActivity::class.java)

        //this is the pending intent which is responsible for get back to app after clicking notification
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1,PendingIntent.FLAG_UPDATE_CURRENT)
        }

        //this is our notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("My Notification Title")
            .setContentText("This is the content of the notification.")
            .setSmallIcon(R.drawable.ic_android_black)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        //this is notification manager which just take the context of the activity
        val notificationManager = NotificationManagerCompat.from(this)

        val button: Button = findViewById(R.id.notification_button)

        button.setOnClickListener {
            notificationManager.notify(NOTIFICATION_ID,notification)
        }
    }

    //this function create a channel for our notification
    //we have to call the function before passing a notification in it
    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}