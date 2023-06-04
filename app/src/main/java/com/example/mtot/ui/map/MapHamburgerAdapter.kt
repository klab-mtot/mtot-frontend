package com.example.mtot.ui.post

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.HamburgerItemInfo
import com.example.mtot.databinding.ItemHamburgerBinding

class MapHamburgerAdapter(val items: ArrayList<HamburgerItemInfo>) : RecyclerView.Adapter<MapHamburgerAdapter.ViewHolder>() {

    var OnItemClickListener : onItemClickListener? = null
    interface onItemClickListener {
        fun MapHamburgerButtonClicked(position: Int)
    }

    inner class ViewHolder(val binding: ItemHamburgerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.tvItemPostHamburger.text = items[position].text

            binding.ivItemPostHamburger.setOnClickListener {
                OnItemClickListener?.MapHamburgerButtonClicked(position)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHamburgerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

}