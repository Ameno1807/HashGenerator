package com.example.hashgenerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

open class MainAdapter() :
    androidx.recyclerview.widget.ListAdapter<HashModel, MainAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.hash_name)

        fun bind(item: HashModel) {
            textView.text = item.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hesh_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<HashModel>() {
        override fun areItemsTheSame(oldItem: HashModel, newItem: HashModel): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: HashModel, newItem: HashModel): Boolean {
            TODO("Not yet implemented")
        }
    }
}