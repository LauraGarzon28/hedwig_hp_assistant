package com.example.hedwig_hp_assistant

import android.accessibilityservice.AccessibilityService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.IBinder
import android.view.accessibility.AccessibilityEvent
import androidx.core.app.NotificationCompat
import android.content.pm.ServiceInfo

class SmartButtonService : Service() {

    private val channelId = "hedwig_channel_id"
    private val notificationId = 1

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForegroundServiceWithMicrophoneType()
        toggleMicMute() // Tu lógica de mute
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId,
                "Hedwig Background Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun startForegroundServiceWithMicrophoneType() {
        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Hedwig funcionando")
            .setContentText("Control de micrófono activo")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ (API 29) permite pasar tipo
            startForeground(
                notificationId,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE
            )
        } else {
            startForeground(notificationId, notification)
        }
    }

    private fun toggleMicMute() {
        val audioManager = getSystemService(AccessibilityService.AUDIO_SERVICE) as AudioManager
        val isMuted = audioManager.isMicrophoneMute
        audioManager.isMicrophoneMute = !isMuted
    }
}