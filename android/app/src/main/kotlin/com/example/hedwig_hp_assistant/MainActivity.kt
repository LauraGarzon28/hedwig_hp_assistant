package com.example.hedwig_hp_assistant

import android.os.Bundle
import android.view.KeyEvent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.content.Intent
import android.os.Build

class MainActivity : FlutterActivity() {
    private val CHANNEL = "voice_key_channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->
            // Puedes manejar llamadas de Flutter si lo necesitas.
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

}