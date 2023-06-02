package com.example.mtot.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.HamburgerItemInfo
import com.example.mtot.R
import com.example.mtot.databinding.ItemHamburgerBinding

class PostHamburgerAdapter(val items: ArrayList<HamburgerItemInfo>) : RecyclerView.Adapter<PostHamburgerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHamburgerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            if(items[position].img != 0){
                binding.ivItemPostHamburger.setImageResource(items[position].img)
            } else {
                binding.ivItemPostHamburger.setImageResource(R.drawable.ic_post_hamburger_edit_pin)
            }
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