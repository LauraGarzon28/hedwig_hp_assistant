package com.example.hedwig_hp_assistant

import android.app.*
import android.content.*
import android.util.Log
import android.os.IBinder
import com.example.hedwig_hp_assistant.R

class SmartButtonService : Service() {

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("SmartButtonService", "Servicio ejecutándose...")

        // Aquí puedes escuchar el botón o lanzar tu lógica.
        // Por ahora solo un log para verificar.

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startForegroundService() {
        val channelId = "smart_button_channel"
        val channel = NotificationChannel(
            channelId,
            "Smart Button Service",
            NotificationManager.IMPORTANCE_LOW
        )

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

        val notification = Notification.Builder(this, channelId)
            .setContentTitle("Asistente activo")
            .setContentText("Escuchando botón inteligente")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

        startForeground(1, notification)
    }
}
