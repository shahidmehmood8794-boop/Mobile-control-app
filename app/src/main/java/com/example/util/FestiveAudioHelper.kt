package com.example.util

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.sin

object FestiveAudioHelper {

  private var musicJob: Job? = null
  private val scope = CoroutineScope(Dispatchers.Default)

  // Pentatonic celebration notes (frequencies in Hz): C5, D5, E5, G5, A5, C6
  private val melodyNotes = listOf(
    523.25, 587.33, 659.25, 783.99, 880.00, 1046.50,
    880.00, 783.99, 659.25, 587.33, 523.25, 659.25
  )

  fun startAmbientMusic(onStateChange: (Boolean) -> Unit) {
    stopAmbientMusic()
    onStateChange(true)
    musicJob = scope.launch {
      val sampleRate = 22050
      val bufferSize = AudioTrack.getMinBufferSize(
        sampleRate,
        AudioFormat.CHANNEL_OUT_MONO,
        AudioFormat.ENCODING_PCM_16BIT
      )

      var audioTrack: AudioTrack? = null
      try {
        audioTrack = AudioTrack.Builder()
          .setAudioAttributes(
            AudioAttributes.Builder()
              .setUsage(AudioAttributes.USAGE_MEDIA)
              .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
              .build()
          )
          .setAudioFormat(
            AudioFormat.Builder()
              .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
              .setSampleRate(sampleRate)
              .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
              .build()
          )
          .setBufferSizeInBytes(bufferSize)
          .setTransferMode(AudioTrack.MODE_STREAM)
          .build()

        audioTrack.play()

        var noteIndex = 0
        while (isActive) {
          val freq = melodyNotes[noteIndex % melodyNotes.size]
          val durationSec = 0.45
          val totalSamples = (sampleRate * durationSec).toInt()
          val buffer = ShortArray(totalSamples)

          for (i in 0 until totalSamples) {
            val t = i.toDouble() / sampleRate
            // Gentle envelope attack/decay
            val envelope = when {
              t < 0.05 -> t / 0.05
              else -> Math.max(0.0, 1.0 - ((t - 0.05) / (durationSec - 0.05)))
            }
            // Fundamental + warm harmonic overtone
            val wave = 0.7 * sin(2.0 * Math.PI * freq * t) + 0.3 * sin(4.0 * Math.PI * freq * t)
            buffer[i] = (wave * envelope * Short.MAX_VALUE * 0.25).toInt().toShort()
          }

          audioTrack.write(buffer, 0, buffer.size)
          delay(120L) // Rest between notes
          noteIndex++
        }
      } catch (_: Exception) {
        // Ignored gracefully if audio subsystem unavailable
      } finally {
        try {
          audioTrack?.stop()
          audioTrack?.release()
        } catch (_: Exception) {}
      }
    }
  }

  fun stopAmbientMusic(onStateChange: ((Boolean) -> Unit)? = null) {
    musicJob?.cancel()
    musicJob = null
    onStateChange?.invoke(false)
  }

  fun playChime() {
    scope.launch {
      val sampleRate = 22050
      val chimeNotes = listOf(659.25, 783.99, 1046.50, 1318.51)
      try {
        val audioTrack = AudioTrack.Builder()
          .setAudioAttributes(
            AudioAttributes.Builder()
              .setUsage(AudioAttributes.USAGE_GAME)
              .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
              .build()
          )
          .setAudioFormat(
            AudioFormat.Builder()
              .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
              .setSampleRate(sampleRate)
              .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
              .build()
          )
          .setBufferSizeInBytes(sampleRate)
          .setTransferMode(AudioTrack.MODE_STREAM)
          .build()

        audioTrack.play()
        for (freq in chimeNotes) {
          val durationSec = 0.18
          val totalSamples = (sampleRate * durationSec).toInt()
          val buffer = ShortArray(totalSamples)
          for (i in 0 until totalSamples) {
            val t = i.toDouble() / sampleRate
            val envelope = Math.max(0.0, 1.0 - (t / durationSec))
            val wave = sin(2.0 * Math.PI * freq * t)
            buffer[i] = (wave * envelope * Short.MAX_VALUE * 0.3).toInt().toShort()
          }
          audioTrack.write(buffer, 0, buffer.size)
        }
        delay(300)
        audioTrack.stop()
        audioTrack.release()
      } catch (_: Exception) {}
    }
  }
}
