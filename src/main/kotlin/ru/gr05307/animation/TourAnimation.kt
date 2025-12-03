package ru.gr05307.animation

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.gr05307.painting.convertation.Plain
import ru.gr05307.viewmodels.MainViewModel

class TourAnimation {
    data class AnimationState(
        val currentFrame: Int = 0,
        val totalFrames: Int = 0,
        val progress: Float = 0f,
        val isPlaying: Boolean = false,
        val isPaused: Boolean = false,
        val currentPlain: Plain? = null
    )

    private  var animationJob: Job? = null
    private val _animationState = MutableStateFlow(AnimationState())
    val animationState = _animationState.asStateFlow()

    private var tourKeyframes: List<MainViewModel.TourKeyframe> = emptyList()
    private var animationSpeed: Double = 1.0    // multiplier
    private var framesPerSecond: Int = 60

}