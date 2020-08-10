package com.blank.myworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat


fun showAlarmNotification(
    context: Context,
    title: String,
    message: String,
    notifId: Int
) {
    val CHANNEL_ID = "Channel_1"
    val CHANNEL_NAME = "AlarmManager channel"

    val notificationManagerCompat =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(title)
        .setContentText(message)
        .setColor(ContextCompat.getColor(context, android.R.color.transparent))
        .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        .setSound(alarmSound)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
        builder.setChannelId(CHANNEL_ID)
        notificationManagerCompat.createNotificationChannel(channel)
    }

    val notification = builder.build()
    notificationManagerCompat.notify(notifId, notification)
}