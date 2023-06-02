package com.example.mtot.ui.post

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.R
import com.example.mtot.databinding.ItemPinImageBinding

class PinAdapter(private val items: List<PinData>) :
    RecyclerView.Adapter<PinAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPinImageBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.imageItem.setImageResource(items[position].image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPinImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

}

