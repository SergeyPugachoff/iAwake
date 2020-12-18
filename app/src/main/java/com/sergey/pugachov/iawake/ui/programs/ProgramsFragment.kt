package com.sergey.pugachov.iawake.ui.programs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sergey.pugachov.iawake.R
import kotlinx.android.synthetic.main.fragment_programs.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProgramsFragment : Fragment() {

    private val viewModel: ProgramsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_programs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        programsList.adapter = ProgramsAdapter { program ->
            val action = ProgramsFragmentDirections.openProgramDetailsAction(
                programId = program.id,
                programTitle = program.title,
                programCoverUrl = program.imageUrl
            )
            findNavController().navigate(action)
        }
        programsList.layoutManager = GridLayoutManager(
            requireContext(),
            resources.getInteger(R.integer.program_list_span_count)
        )

        refreshLayout.setOnRefreshListener {
            viewModel.loadPrograms()
        }

        viewModel.programs.observe(viewLifecycleOwner, Observer { programs ->
            (programsList.adapter as ProgramsAdapter).setItems(programs)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            refreshLayout.isRefreshing = isLoading
        })
    }
}