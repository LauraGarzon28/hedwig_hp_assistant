package com.example.hedwig_hp_assistant

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class HedwigAccessibilityService : AccessibilityService() {

    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = false

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == 295 && event.action == KeyEvent.ACTION_DOWN && !isListening) {
            Log.d("HedwigService", "¡Botón inteligente presionado!")

            if (SpeechRecognizer.isRecognitionAvailable(this)) {
                isListening = true
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
                val intent =
                        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(
                                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                            )
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-MX")
                            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
                        }

                speechRecognizer?.setRecognitionListener(
                        object : RecognitionListener {
                            override fun onResults(results: Bundle?) {
                                val spokenText =
                                        results?.getStringArrayList(
                                                        SpeechRecognizer.RESULTS_RECOGNITION
                                                )
                                                ?.firstOrNull()
                                Log.d("HedwigService", "Texto reconocido: $spokenText")
                                launchApp(spokenText)
                                isListening = false
                            }

                            override fun onError(error: Int) {
                                Log.e("HedwigService", "Error STT: $error")
                                isListening = false
                            }

                            override fun onReadyForSpeech(params: Bundle?) {}
                            override fun onBeginningOfSpeech() {}
                            override fun onRmsChanged(rmsdB: Float) {}
                            override fun onBufferReceived(buffer: ByteArray?) {}
                            override fun onEndOfSpeech() {}
                            override fun onPartialResults(partialResults: Bundle?) {}
                            override fun onEvent(eventType: Int, params: Bundle?) {}
                        }
                )

                speechRecognizer?.startListening(intent)
            }
            return true
        }
        return super.onKeyEvent(event)
    }

    private fun launchApp(text: String?) {
        val launchIntent =
                Intent(this, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    putExtra("recognized_text", text)
                }
        startActivity(launchIntent)
    }

    override fun onInterrupt() {
        speechRecognizer?.cancel()
        speechRecognizer?.destroy()
        isListening = false
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer?.destroy()
        stopService(Intent(this, WakeWordService::class.java))
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onServiceConnected() {
        super.onServiceConnected()

        // Aquí inicias el servicio de hotword (WakeWordService)
        val serviceIntent = Intent(this, WakeWordService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }
}
