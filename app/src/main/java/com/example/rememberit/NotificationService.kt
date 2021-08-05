package com.example.rememberit

import android.annotation.TargetApi
import android.app.*
import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificationService : IntentService {
    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private var NOTIFICATION_ID = 0
    private lateinit var TITULO_NOTIFICACION : String
    private lateinit var DESC_NOTIFICACION : String
    lateinit var notification: Notification

    constructor(name: String) : super(name)

    constructor() : super("SERVICE")

    @TargetApi(Build.VERSION_CODES.O)
    override fun onHandleIntent(intent: Intent?) {
        val datosNotiService = intent?.extras
        if (datosNotiService != null) {
            NOTIFICATION_ID = datosNotiService.getInt("id_notificacion")
            TITULO_NOTIFICACION = datosNotiService.getString("titulo_ev_ntf").toString()
            DESC_NOTIFICACION = datosNotiService.getString("fechahora_ev_ntf").toString()
        } else {
            NOTIFICATION_ID = 1
            TITULO_NOTIFICACION = "TITULO: NO SE RECIBIÓ NADA"
            DESC_NOTIFICACION = "DESCRIPCION: NO SE RECIBIÓ NADA"
        }
        val NOTIFICATION_CHANNEL_ID = applicationContext.getString(R.string.app_name)
        val context = this.applicationContext
        notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val mainIntent = Intent(this, MainActivity::class.java)
        val res = this.resources
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val NOTIFY_ID = NOTIFICATION_ID // ID de notificación
            val pendingIntent: PendingIntent
            val builder: NotificationCompat.Builder
            var notifManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_HIGH
            var mChannel = notifManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
            mChannel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_ID, importance
            )
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notifManager.createNotificationChannel(mChannel)

            builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            pendingIntent =
                    PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            builder.setContentTitle(TITULO_NOTIFICACION)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setSmallIcon(R.drawable.ic_notify)
                    .setContentText(DESC_NOTIFICACION)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_notify))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setSound(soundUri)
                    .setContentIntent(pendingIntent)
                    .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
            val notification = builder.build()
            notifManager.notify(NOTIFY_ID, notification)
            startForeground(NOTIFY_ID + 1, notification)
        } else {
            pendingIntent =
                    PendingIntent.getActivity(context, 1, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            notification = NotificationCompat.Builder(this)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background))
                    .setSound(soundUri)
                    .setAutoCancel(true)
                    .setContentTitle(TITULO_NOTIFICACION)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setContentText(DESC_NOTIFICACION).build()
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}