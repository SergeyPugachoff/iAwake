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

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.programCoverImage.load(arguments.programCoverUrl)
        binding.programTitle.text = arguments.programTitle

        binding.tracksList.adapter = TracksAdapter(
            onPlayClicked = { track ->
                viewModel.toggleTrackSelection(track)
            }
        )
        binding.tracksList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.tracks.observe(viewLifecycleOwner, { tracks ->
            (binding.tracksList.adapter as TracksAdapter).setItems(tracks)
        })

        viewModel.selectedTrack.observe(viewLifecycleOwner, { selectedTrack ->
            (binding.tracksList.adapter as TracksAdapter).setSelectedTrack(selectedTrack)
        })
    }
}
