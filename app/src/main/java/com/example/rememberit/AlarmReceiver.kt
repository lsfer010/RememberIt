package com.example.rememberit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val datos = intent.extras
        if (datos != null) {
            val servicio = Intent(context, NotificationService::class.java)
            servicio.putExtra("id_notificacion",datos.getInt("id_notif"))
            servicio.putExtra("titulo_ev_ntf",datos.getString("titulo_ev"))
            servicio.putExtra("fechahora_ev_ntf",datos.getString("fechahora_ev"))
            servicio.data = Uri.parse("custom://" + System.currentTimeMillis())
            ContextCompat.startForegroundService(context, servicio)
        } else {
            Log.e("Error", "NO SE RECIBIO NADA")
        }
    }
}

/* Código anterior:
*
* override fun onReceive(context: Context?, intent: Intent?) {
        val service1 = Intent(context, NotificationService::class.java)
        service1.data = Uri.parse("custom://" + System.currentTimeMillis())
        ContextCompat.startForegroundService(context!!, service1)
    }
* */