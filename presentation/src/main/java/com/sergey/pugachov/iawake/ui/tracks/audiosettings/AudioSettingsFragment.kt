package com.sergey.pugachov.iawake.ui.tracks.audiosettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sergey.pugachov.iawake.R
import com.sergey.pugachov.iawake.databinding.FragmentAudioSettingsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class AudioSettingsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAudioSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AudioSettingsViewModel by viewModel()
    private val volumeBarListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            viewModel.setVolume(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }

    }

    override fun getTheme(): Int = R.style.BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.volumePercent.observe(viewLifecycleOwner, {
            binding.volumeBar.progress = it
        })

        binding.volumeBar.setOnSeekBarChangeListener(volumeBarListener)

        binding.speackerSwitcher.isChecked = viewModel.isSpeakerPhoneOn
        binding.speackerSwitcher.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onSpeakerPhone(isChecked)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}