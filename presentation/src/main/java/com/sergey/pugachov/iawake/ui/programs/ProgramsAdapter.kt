package com.sergey.pugachov.iawake.ui.programs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sergey.pugachov.iawake.R
import com.sergey.pugachov.iawake.domain.model.programs.ProgramModel

class ProgramsAdapter(
    private val listener: (program: ProgramModel) -> Unit
) : RecyclerView.Adapter<ProgramsAdapter.ProgramsViewHolder>() {

    private val items: MutableList<ProgramModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_program_list_item, parent, false)
        return ProgramsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramsViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<ProgramModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ProgramsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.programImage)

        fun bind(program: ProgramModel) {
            view.setOnClickListener {
                listener(program)
            }
            image.load(program.thumbnailUrl)
        }
    }
}