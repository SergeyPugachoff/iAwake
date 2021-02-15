package com.sergey.pugachov.iawake.ui.tracks

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.card.MaterialCardView
import com.sergey.pugachov.iawake.R
import com.sergey.pugachov.iawake.databinding.ViewTracksSelectedTrackBinding
import com.sergey.pugachov.iawake.ui.tracks.model.TracksUiModel

class SelectedTrackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding: ViewTracksSelectedTrackBinding =
        ViewTracksSelectedTrackBinding.inflate(LayoutInflater.from(context), this, true)

    private var onClicked: ((isPlayed: Boolean) -> Unit)? = null

    init {
        binding.soundAnimation.setAnimation(R.raw.audio_play)
        binding.soundAnimation.repeatCount = LottieDrawable.INFINITE
    }

    fun displayTrack(tracksUiModel: TracksUiModel) {
        binding.run {
            root.setOnClickListener {
                onClicked?.invoke(tracksUiModel.isPlaying())
            }

            trackTitle.text = tracksUiModel.title
            trackTitle.isSelected = tracksUiModel.isPlaying()
            playPauseButton.isChecked = tracksUiModel.isPlaying()

            if (tracksUiModel.state == TracksUiModel.State.Playing) {
                binding.soundAnimation.playAnimation()
            } else {
                binding.soundAnimation.pauseAnimation()
            }

            loading.isVisible = tracksUiModel.state == TracksUiModel.State.Loading
        }
    }

    fun setOnClickListener(action: (isPlayed: Boolean) -> Unit) {
        onClicked = action
    }
}