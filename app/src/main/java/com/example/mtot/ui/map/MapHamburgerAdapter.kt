package com.example.mtot.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.HamburgerItemInfo
import com.example.mtot.databinding.FragmentMapHamburgerBinding
import com.example.mtot.databinding.ItemHamburgerBinding

class MapHamburgerAdapter(val items: ArrayList<HamburgerItemInfo>) : RecyclerView.Adapter<MapHamburgerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHamburgerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.tvItemPostHamburger.text = items[position].text
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