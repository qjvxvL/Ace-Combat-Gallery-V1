package dev.dwikiy.ace_combat_gallery

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AudioViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(AudioPlayerState())
    val uiState = _uiState.asStateFlow()

    private var mediaPlayer: MediaPlayer? = null
    private var progressUpdateJob: Job? = null

    init {
        mediaPlayer = MediaPlayer.create(application.applicationContext, R.raw.hush)
        mediaPlayer?.setOnCompletionListener {
            _uiState.update { it.copy(isPlaying = false) }
            progressUpdateJob?.cancel()
        }
        _uiState.update { it.copy(totalTime = formatTime(mediaPlayer?.duration ?: 0)) }
    }

    fun onPlayPauseClick() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            _uiState.update { it.copy(isPlaying = false) }
            progressUpdateJob?.cancel()
        } else {
            mediaPlayer?.start()
            _uiState.update { it.copy(isPlaying = true) }
            startProgressUpdates()
        }
    }

    fun onSeek(newPosition: Float) {
        val newMillis = (newPosition * (mediaPlayer?.duration ?: 0)).toInt()
        mediaPlayer?.seekTo(newMillis)
        updateProgress() // Immediately update UI after seek
    }

    private fun startProgressUpdates() {
        progressUpdateJob?.cancel()
        progressUpdateJob = viewModelScope.launch {
            while (true) {
                updateProgress()
                delay(1000) // Update every second
            }
        }
    }

    private fun updateProgress() {
        mediaPlayer?.let { player ->
            _uiState.update {
                it.copy(
                    progress = player.currentPosition.toFloat() / player.duration.toFloat(),
                    currentTime = formatTime(player.currentPosition)
                )
            }
        }
    }

    private fun formatTime(millis: Int): String {
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(millis.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis.toLong()))
        )
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}