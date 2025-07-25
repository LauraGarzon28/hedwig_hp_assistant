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

        // Lanza MainActivity si no está en primer plano (por ejemplo, si la app estaba cerrada)
        val launchIntent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("source", "smart_button") // Opcional, si quieres saber desde Flutter que fue invocada por botón
        }
        startActivity(launchIntent)

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
