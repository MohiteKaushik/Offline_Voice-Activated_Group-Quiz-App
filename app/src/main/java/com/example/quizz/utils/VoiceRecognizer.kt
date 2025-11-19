// ============================================
// utils/VoiceRecognizer.kt (BEST SOLUTION - Manual Control)
// ============================================
package com.example.quizz.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log

class VoiceRecognizer(
    private val context: Context,
    private val onResult: (String) -> Unit,
    private val onError: (String) -> Unit,
    private val onListeningStateChange: (Boolean) -> Unit
) {

    private var speechRecognizer: SpeechRecognizer? = null
    private val wakeWord = "movie"
    private var isListening = false
    private var shouldRestart = true  // Control auto-restart

    init {
        setupSpeechRecognizer()
    }

    private fun setupSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    isListening = true
                    onListeningStateChange(true)
                    Log.d("VoiceRecognizer", "🎤 Ready for speech")
                }

                override fun onBeginningOfSpeech() {
                    Log.d("VoiceRecognizer", "🗣️ Speech started")
                }

                override fun onRmsChanged(rmsdB: Float) {
                    // Volume level changed - can be used for visual feedback
                }

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {
                    isListening = false
                    onListeningStateChange(false)
                    Log.d("VoiceRecognizer", "🔇 Speech ended")
                }

                override fun onError(error: Int) {
                    isListening = false
                    onListeningStateChange(false)

                    val errorMessage = when(error) {
                        SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                        SpeechRecognizer.ERROR_CLIENT -> "Client error"
                        SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Microphone permission required"
                        SpeechRecognizer.ERROR_NETWORK -> "Network error"
                        SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                        SpeechRecognizer.ERROR_NO_MATCH -> "No speech detected"
                        SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognition service busy"
                        SpeechRecognizer.ERROR_SERVER -> "Server error"
                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Speech timeout"
                        13 -> "Recognizer not ready"
                        else -> "Unknown error: $error"
                    }

                    Log.e("VoiceRecognizer", "❌ Error $error: $errorMessage")

                    // Only show critical errors to user
                    if (error == SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS) {
                        onError(errorMessage)
                        shouldRestart = false  // Don't auto-restart on permission error
                        return
                    }

                    // Auto-restart only if shouldRestart is true
                    if (shouldRestart) {
                        // Destroy and recreate recognizer to fix error 13
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            recreateRecognizer()
                            startListening()
                        }, 2000)  // 2 second delay
                    }
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

                    // Log all recognized alternatives
                    matches?.forEachIndexed { index, match ->
                        Log.d("VoiceRecognizer", "Match $index: $match")
                    }

                    // Try to find English text in results
                    val englishMatch = matches?.firstOrNull { text ->
                        text.matches(Regex("^[a-zA-Z0-9\\s]+$"))
                    }

                    val recognizedText = englishMatch ?: matches?.firstOrNull()

                    recognizedText?.let { text ->
                        Log.d("VoiceRecognizer", "✅ Processing: $text")
                        processRecognizedText(text)
                    }

                    // Auto-restart listening if enabled
                    if (shouldRestart) {
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            startListening()
                        }, 1500)  // 1.5 second delay after successful recognition
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {}

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        } else {
            Log.e("VoiceRecognizer", "Speech recognition not available on this device")
            onError("Speech recognition not available")
        }
    }

    private fun recreateRecognizer() {
        Log.d("VoiceRecognizer", "🔄 Recreating speech recognizer")
        speechRecognizer?.destroy()
        speechRecognizer = null
        setupSpeechRecognizer()
    }

    private fun processRecognizedText(text: String) {
        val lowerText = text.lowercase().trim()

        Log.d("VoiceRecognizer", "Processing lowercase: $lowerText")
        onResult(lowerText)

        // Check for wake word
//        if (lowerText.startsWith(wakeWord) or lowerText.startsWith("stop")) {
//
//            onResult(lowerText)
//
//            //val movieName = lowerText.removePrefix(wakeWord).trim()
////            if (movieName.isNotEmpty()) {
////                Log.d("VoiceRecognizer", "🎬 Movie name extracted: $movieName")
////
////            } else {
////                Log.d("VoiceRecognizer", "⚠️ No movie name after wake word")
////            }
//        }
//        else {
//            Log.d("VoiceRecognizer", "⚠️ Wake word '$wakeWord' or stop not detected. Text: $lowerText")
//        }
    }

    fun startListening() {
        if (isListening) {
            Log.d("VoiceRecognizer", "Already listening, skipping start")
            return
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            // Force English language
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN")
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)

            // Get multiple results
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)

            // CRITICAL: Enable offline recognition
            putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)

            // IMPORTANT: Set to false to avoid partial results triggering too early
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)

            // Give users plenty of time to speak
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 2000L)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 2000L)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 5000L)

            // Don't show any UI
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
        }

        try {
            speechRecognizer?.startListening(intent)
            Log.d("VoiceRecognizer", "🎤 Started listening in English mode")
        } catch (e: Exception) {
            Log.e("VoiceRecognizer", "Failed to start listening: ${e.message}")
            onError("Failed to start voice recognition")
        }
    }

    fun stopListening() {
        shouldRestart = false  // Disable auto-restart
        isListening = false
        speechRecognizer?.stopListening()
        onListeningStateChange(false)
        Log.d("VoiceRecognizer", "🛑 Stopped listening")
    }

    fun destroy() {
        shouldRestart = false  // Disable auto-restart
        speechRecognizer?.destroy()
        speechRecognizer = null
        Log.d("VoiceRecognizer", "🗑️ Voice recognizer destroyed")
    }
}