import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

class AudioPlayer(context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private val appContext = context.applicationContext

    fun play(resourceId: Int) {
        // Release any existing player
        release()

        mediaPlayer = MediaPlayer.create(appContext, resourceId).apply {
            isLooping = true // Set to false if you don't want looping
            start()
        }
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun resume() {
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.stop()
        release()
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying ?: false
}

@Composable
fun rememberAudioPlayer(): AudioPlayer {
    val context = LocalContext.current
    return remember { AudioPlayer(context) }
}
