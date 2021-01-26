package com.sergey.pugachov.iawake.ui.programs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sergey.pugachov.iawake.databinding.ViewProgramListItemBinding
import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel

class ProgramsAdapter(
    private val listener: (program: ProgramModel) -> Unit
) : RecyclerView.Adapter<ProgramsAdapter.ProgramsViewHolder>() {

    private val items: MutableList<ProgramModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramsViewHolder {
        val binding = ViewProgramListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProgramsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramsViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<ProgramModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ProgramsViewHolder(
        private val binding: ViewProgramListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener(items[adapterPosition])
            }
        }

        fun bind(program: ProgramModel) {
            binding.programImage.load(program.thumbnailUrl)
        }
    }
}