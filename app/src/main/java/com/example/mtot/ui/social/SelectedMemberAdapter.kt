package com.example.mtot.ui.social

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.ItemSelectedMemberBinding

class SelectedMemberAdapter(val items: ArrayList<String>) : RecyclerView.Adapter<SelectedMemberAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSelectedMemberBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.tvSocialList.text = items[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSelectedMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun addItem(str: String){
        items.add(str)
        notifyItemInserted(items.size-1)
    }

}