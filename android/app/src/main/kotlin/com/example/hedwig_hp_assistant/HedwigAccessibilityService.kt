package com.example.hedwig_hp_assistant

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.KeyEvent
import android.content.Intent


class HedwigAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.d("HedwigService", "Evento capturado: ${event?.eventType}")
        // Aquí podrías iniciar una acción como activar STT, enviar a Flutter, etc.
    }

    override fun onInterrupt() {
        Log.d("HedwigService", "Servicio interrumpido")
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
    Log.d("HedwigService", "Key event recibido: ${event.keyCode}, acción: ${event.action}")
    
    if (event.keyCode == 295 && event.action == KeyEvent.ACTION_DOWN) {
        Log.d("HedwigService", "¡Botón inteligente presionado!")

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("fromAccessibility", true)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

        return true
    }
    return super.onKeyEvent(event)
}
}
