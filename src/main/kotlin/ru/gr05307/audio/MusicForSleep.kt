package ru.gr05307.audio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

object MusicForSleep {
    private var currentClip: Clip? = null

    private var currentFramePosition: Int = 0
    private val fractalTracks = mapOf(
        "mandelbrot" to "HOYO-MiX_-_Scorching_Outpost_74869833.wav",
        "julia" to "Clair_Obscur_Expedition_33_Original_Soundtrack_100_Sirène_Robe_de.wav",
        "newton" to "HOYO_MiX_Yu_Peng_Chen_A_Storm_A_Spire_and_A_Sanctum_Dvalins_Nest.wav"
    )

    fun playFractalTheme(fractalType: String) {
        val fileName = fractalTracks[fractalType]

        stopAndCloseCurrentTheme()

        GlobalScope.launch(Dispatchers.IO) {

            val rawStream = {}.javaClass.classLoader.getResourceAsStream(fileName)

            val stream = BufferedInputStream(rawStream)

            val audioStream = AudioSystem.getAudioInputStream(stream)
            val newClip = AudioSystem.getClip()

            newClip.open(audioStream)
            newClip.loop(Clip.LOOP_CONTINUOUSLY)
            currentFramePosition = 0
            newClip.start()

            currentClip = newClip


        }
    }

    fun stopAndCloseCurrentTheme() {
        currentClip?.let { clip ->
            if (clip.isRunning) {
                clip.stop()
            }
            clip.close()
        }
        currentClip = null
        currentFramePosition = 0
    }

    fun pauseTheme() {
        currentClip?.let { clip ->
            if (clip.isRunning) {
                currentFramePosition = clip.framePosition
                clip.stop()
            }
        }
    }

    fun resumeTheme() {
        currentClip?.let { clip ->
            if (!clip.isRunning) {
                clip.framePosition = currentFramePosition
                clip.start()
            }
        }
    }
}