package com.example.hedwig_hp_assistant

import ai.picovoice.porcupine.*
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class WakeWordService : Service() {

    private var porcupineManager: PorcupineManager? = null
    private val CHANNEL_ID = "wakeword_channel"
    private val NOTIFICATION_ID = 1

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel() // Needed for Android 8+

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Hedwig is listening...")
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        val accessKey = BuildConfig.PORCUPINE_API_KEY
        val keywordPath = "Hedwig_en_android_v3_0_0.ppn"

        try {
            porcupineManager = PorcupineManager.Builder()
                .setAccessKey(accessKey)
                .setKeywordPath(keywordPath)
                .build(applicationContext) {
                    launchAssistant()
                }

            porcupineManager?.start()
        } catch (e: PorcupineException) {
            Log.e("WakeWordService", "Error al iniciar PorcupineManager", e)
        }
    }

    private fun launchAssistant() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("fromWakeWord", true)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        porcupineManager?.delete()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Wake Word Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }
}
