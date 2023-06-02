package com.example.mtot.ui.social

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.SocialGrouplistBinding

class GroupListAdapter(val items: ArrayList<SocialListInfo>) :
    RecyclerView.Adapter<GroupListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: SocialGrouplistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.ivSocialList.setImageResource(items[position].image)
            binding.tvSocialList.text = items[position].text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SocialGrouplistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}