package com.sergey.pugachov.iawake.ui.tracks

import android.bluetooth.BluetoothA2dp
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.SeekBar
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

            settingsButton.setOnClickListener {
                val action = TracksFragmentDirections.actionTracksFragmentToAudioSettingsFragment()
                findNavController().navigate(action)
            }
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

        setupVolumeBar()
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

    private fun setupVolumeBar(){
        val audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        binding.volumeBar.max = maxVolume
        binding.volumeBar.progress = currentVolume
        binding.volumeBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        var mBluetoothSpeaker: BluetoothA2dp
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val mProfileListener: BluetoothProfile.ServiceListener =
            object : BluetoothProfile.ServiceListener {
                override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                    if (profile == BluetoothProfile.A2DP) {
                        mBluetoothSpeaker = proxy as BluetoothA2dp

                        // no devices are connected
                        val connectedDevices: List<BluetoothDevice> =
                            mBluetoothSpeaker.connectedDevices

                        //the one paired (and disconnected) speaker is returned here
                        val statesToCheck = intArrayOf(BluetoothA2dp.STATE_DISCONNECTED)
                        val disconnectedDevices: List<BluetoothDevice> =
                            mBluetoothSpeaker.getDevicesMatchingConnectionStates(statesToCheck)
                        val names = disconnectedDevices.map { it.name }
                        val btSpeaker = disconnectedDevices[0]

                        //WHAT NOW?
                    }
                }

                override fun onServiceDisconnected(profile: Int) {
                    if (profile == BluetoothProfile.A2DP) {
//                        mBluetoothSpeaker = null
                    }
                }
            }
        mBluetoothAdapter.getProfileProxy(requireContext(), mProfileListener, BluetoothProfile.A2DP)
//        audioManager.isSpeakerphoneOn = false

        val audioDevices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        val names = audioDevices.map { it.productName }

//        binding.outputSwitcher.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked){
//                audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
//                audioManager.stopBluetoothSco()
//                audioManager.isBluetoothScoOn = false
//                audioManager.isSpeakerphoneOn = true
//            } else{
//                audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
//                audioManager.startBluetoothSco()
//                audioManager.isBluetoothScoOn = true
//                audioManager.isSpeakerphoneOn = false
//            }

//            audioManager.isSpeakerphoneOn = isChecked
        }
//            var isOn = isChecked
//            if (isOn) {
//                isOn = false
//                audioManager.mode = AudioManager.MODE_NORMAL
//                audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
//
//            } else {
//                isOn = true
//                audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
//                audioManager.mode = AudioManager.MODE_NORMAL
//            }
//            audioManager.isSpeakerphoneOn = isOn
//        }
//        Got it working:                                                                                                      'if(false) {
//        //For BT
//        mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
//        mAudioManager.startBluetoothSco();
//        mAudioManager.setBluetoothScoOn(true);
//    } else if(true) {
//        //For phone ear piece
//        mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
//        mAudioManager.stopBluetoothSco();
//        mAudioManager.setBluetoothScoOn(false);
//        mAudioManager.setSpeakerphoneOn(false);
//    } else {
//        //For phone speaker(loudspeaker)
//        mAudioManager.setMode(AudioManager.MODE_NORMAL);
//        mAudioManager.stopBluetoothSco();
//        mAudioManager.setBluetoothScoOn(false);
//        mAudioManager.setSpeakerphoneOn(true);
//    }
//    }
}
