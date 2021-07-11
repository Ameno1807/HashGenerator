package com.example.hashgenerator.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hashgenerator.R
import com.example.hashgenerator.data.model.HashModel

class MainAdapter() :
   RecyclerView.Adapter<MainAdapter.ViewHolder>() {

     private var hashList = emptyList<HashModel>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hashName: TextView = itemView.findViewById(R.id.hash_name)

        fun bind(item: HashModel) {
            "toHash: ${item.hashName}, hash value: ${item.hashValue}".also { hashName.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hesh_list, parent, false)
        )
    }

     override fun getItemCount(): Int {
         return hashList.size
     }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = hashList[position]
        holder.bind(item)
    }

     fun setData(hash: List<HashModel>) {
        this.hashList = hash
         notifyDataSetChanged()
     }
   }