package com.example.hedwig_hp_assistant

import android.os.Bundle
import android.view.KeyEvent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.content.Intent
import android.os.Build
import android.util.Log

class MainActivity : FlutterActivity() {
    private val CHANNEL = "voice_key_channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result -> 
            // No es necesario manejar llamadas desde Dart aún
        }

        // Envía el extra a Flutter (solo si la app se abrió por el botón inteligente)
        intent?.getStringExtra("source")?.let { source ->
            if (source == "smart_button") {
                MethodChannel(
                    flutterEngine.dartExecutor.binaryMessenger,
                    CHANNEL
                ).invokeMethod("smartButtonPressed", null)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == 295) {
            // Inicia el servicio
            val intent = Intent(this, SmartButtonService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }

            // Notifica a Flutter (opcional si quieres que se comunique con Dart)
            MethodChannel(
                flutterEngine?.dartExecutor?.binaryMessenger ?: return false,
                CHANNEL
            ).invokeMethod("smartButtonPressed", null)
            
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleAssistIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = intent.getStringExtra("recognized_text")
        val startedFromAccessibility = intent.getBooleanExtra("fromAccessibility", false)

        if (!text.isNullOrEmpty()) {
            flutterEngine?.dartExecutor?.binaryMessenger?.let { messenger ->
                MethodChannel(messenger, "voice_key_channel").invokeMethod("speechRecognized", text)
            }
        } else if (startedFromAccessibility) {
            flutterEngine?.dartExecutor?.binaryMessenger?.let { messenger ->
                MethodChannel(messenger, "voice_key_channel").invokeMethod("smartButtonPressed", null)
            }
        }
    }

    private fun handleAssistIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_ASSIST) {
            // Este se activa con el botón inteligente si eres el asistente por defecto.
            Log.d("MainActivity", "Botón inteligente detectado (ACTION_ASSIST)")
            
            // Aquí podrías iniciar un servicio, una actividad, o emitir algo a Flutter
            val serviceIntent = Intent(this, SmartButtonService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
        }
    }


}