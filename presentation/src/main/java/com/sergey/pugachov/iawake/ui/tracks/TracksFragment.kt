package com.sergey.pugachov.iawake.ui.tracks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.sergey.pugachov.iawake.R
import com.sergey.pugachov.iawake.databinding.FragmentTracksBinding
import com.sergey.pugachov.iawake.extentions.binding.viewBinding
import com.sergey.pugachov.iawake.extentions.widgets.CheckableImageView
import org.koin.android.viewmodel.ext.android.sharedViewModel


class TracksFragment : Fragment(R.layout.fragment_tracks) {

    private val arguments by navArgs<TracksFragmentArgs>()
    private val binding by viewBinding(FragmentTracksBinding::bind)
    private val viewModel: TracksViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTracks(
            programId = arguments.programId,
            programCoverUrl = arguments.programCoverUrl
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

            settingsButton.setOnClickListener {
                val action = TracksFragmentDirections.actionTracksFragmentToAudioSettingsFragment()
                findNavController().navigate(action)
            }
        }

        viewModel.tracks.observe(viewLifecycleOwner, { tracks ->
            (binding.content.tracksList.adapter as TracksAdapter).setItems(tracks)
        })

        viewModel.selectedTrack.observe(viewLifecycleOwner, { selectedTrack ->
            binding.content.playButton.isChecked = selectedTrack.isPlaying()

            (binding.content.tracksList.adapter as TracksAdapter).setSelectedTrack(selectedTrack)

        })
    }
}
