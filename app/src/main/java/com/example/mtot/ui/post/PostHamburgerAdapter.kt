package com.example.mtot.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.ItemPostHamburgerBinding

class PostHamburgerAdapter(val items: ArrayList<PostHamburgerInfo>) : RecyclerView.Adapter<PostHamburgerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPostHamburgerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.tvItemPostHamburger.text = items[position].text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostHamburgerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

}