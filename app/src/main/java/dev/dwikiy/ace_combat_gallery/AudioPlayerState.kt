package dev.dwikiy.ace_combat_gallery

data class AudioPlayerState(
    val title: String = "Hush",
    val artist: String = "Ace Combat 7 OST",
    val isPlaying: Boolean = false,
    val progress: Float = 0f, // A value between 0.0 and 1.0
    val currentTime: String = "00:00",
    val totalTime: String = "00:00"
)