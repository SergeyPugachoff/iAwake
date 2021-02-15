package com.sergey.pugachov.iawake.ui.programs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sergey.pugachov.iawake.R
import com.sergey.pugachov.iawake.databinding.FragmentProgramsBinding
import com.sergey.pugachov.iawake.extentions.binding.viewBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ProgramsFragment : Fragment(R.layout.fragment_programs) {

    private val viewModel: ProgramsViewModel by viewModel()
    private val binding by viewBinding(FragmentProgramsBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_programs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.programsList.adapter = ProgramsAdapter { program ->
            val action = ProgramsFragmentDirections.openProgramDetailsAction(
                programId = program.id,
                programTitle = program.title,
                programCoverUrl = program.imageUrl
            )
            findNavController().navigate(action)
        }
        binding.programsList.layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.program_list_span_count)
        )

        binding.refreshLayout.setOnRefreshListener {
            viewModel.loadPrograms()
        }

        viewModel.programs.observe(viewLifecycleOwner, { programs ->
            (binding.programsList.adapter as ProgramsAdapter).setItems(programs)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            binding.refreshLayout.isRefreshing = isLoading
        })
    }
}