package com.example.mtot.ui.social

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.SocialFriendlistBinding
import com.example.mtot.databinding.SocialGrouplistBinding

class FriendListAdapter(val items: ArrayList<SocialListInfo>) :
    RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: SocialFriendlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.ivSocialList.setImageResource(items[position].image)
            binding.tvSocialList.text = items[position].text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SocialFriendlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}