package com.example.mtot

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.ItemPinImageBinding
import com.bumptech.glide.Glide


class PinAdapter(var context: Context,  var items: ArrayList<String>) :
    RecyclerView.Adapter<PinAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPinImageBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val imageUrl = items[position]
            Glide.with(context)
                .load(imageUrl)
                .into(binding.imageItem)
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


