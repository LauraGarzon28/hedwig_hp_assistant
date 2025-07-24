package com.example.hedwig_hp_assistant

import android.os.Bundle
import android.view.KeyEvent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

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
            MethodChannel(
                flutterEngine?.dartExecutor?.binaryMessenger ?: return false,
                CHANNEL
            ).invokeMethod("smartButtonPressed", null)
            return true
        }

        return super.onKeyDown(keyCode, event)
    }
}