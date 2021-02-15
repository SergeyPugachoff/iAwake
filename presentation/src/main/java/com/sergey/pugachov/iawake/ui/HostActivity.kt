package com.sergey.pugachov.iawake.ui

import android.os.Bundle
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.sergey.pugachov.iawake.databinding.ActivityHostBinding
import com.sergey.pugachov.iawake.extentions.binding.viewBinding
import com.sergey.pugachov.iawake.ui.tracks.TracksViewModel
import com.sergey.pugachov.iawake.ui.tracks.model.TracksUiModel
import org.koin.android.viewmodel.ext.android.viewModel

class HostActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityHostBinding::inflate)
    private val viewModel: TracksViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.selectedTrack.observe(this, Observer(::showSelectedTrack))

        binding.selectedTrackView.setOnClickListener { isPlayed ->
            if (isPlayed) {
                viewModel.pause()
            } else {
                viewModel.play()
            }
        }
    }

    private fun showSelectedTrack(track : TracksUiModel) {
        binding.selectedTrackView.displayTrack(track)

        if (binding.selectedTrackView.isInvisible) {
            binding.selectedTrackView.isInvisible = false

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.root)

            val transition = ChangeBounds()
            val interpolator = AnticipateOvershootInterpolator(1.0f)
            transition.interpolator = interpolator

            TransitionManager.beginDelayedTransition(binding.root, transition)
            constraintSet.clear(binding.selectedTrackView.id, ConstraintSet.TOP)
            constraintSet.connect(
                binding.selectedTrackView.id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            constraintSet.applyTo(binding.root)
        }
    }
}