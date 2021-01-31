package com.sergey.pugachov.iawake.ui.tracks

import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import coil.load
import com.sergey.pugachov.iawake.R
import com.sergey.pugachov.iawake.databinding.FragmentTracksBinding
import com.sergey.pugachov.iawake.extentions.binding.viewBinding
import com.sergey.pugachov.iawake.extentions.widgets.CheckableImageView
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TracksFragment : Fragment(R.layout.fragment_tracks) {

    private val arguments by navArgs<TracksFragmentArgs>()
    private val binding by viewBinding(FragmentTracksBinding::bind)
    private val viewModel: TracksViewModel by viewModel {
        parametersOf(
            arguments.programId,
            arguments.programCoverUrl
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.content.run {
            backButton.setOnClickListener {
                findNavController().navigateUp()
            }

            programCoverImage.load(arguments.programCoverUrl)
            programTitle.text = arguments.programTitle
            toolbarTitle.text = arguments.programTitle

            playButton.setOnClickListener { button ->
                button as CheckableImageView
                if (button.isChecked) {
                    viewModel.pause()
                } else {
                    viewModel.play()
                }
            }

            tracksList.adapter = TracksAdapter(
                onItemClick = { track ->
                    viewModel.play(track)
                }
            )
            tracksList.layoutManager = LinearLayoutManager(requireContext())
        }

        binding.selectedTrackView.setOnClickListener { isPlayed ->
            if (isPlayed) {
                viewModel.pause()
            } else {
                viewModel.play()
            }
        }

        viewModel.tracks.observe(viewLifecycleOwner, { tracks ->
            (binding.content.tracksList.adapter as TracksAdapter).setItems(tracks)
        })

        viewModel.selectedTrack.observe(viewLifecycleOwner, { selectedTrack ->
            binding.content.playButton.isChecked = selectedTrack.isPlaying()

            (binding.content.tracksList.adapter as TracksAdapter).setSelectedTrack(selectedTrack)

            binding.selectedTrackView.displayTrack(selectedTrack)
            showSelectedTrackView()
        })
    }

    private fun showSelectedTrackView() {
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
