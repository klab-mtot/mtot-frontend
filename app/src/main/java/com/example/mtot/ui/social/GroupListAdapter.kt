package com.example.mtot.ui.social

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.ItemSocialGroupListBinding

class GroupListAdapter(var items: ArrayList<SocialListInfo>) :
    RecyclerView.Adapter<GroupListAdapter.ViewHolder>() {

    var OnItemClickListener : onItemClickListener? = null
    interface onItemClickListener {
        fun onItemClicked(position: Int)
    }
    inner class ViewHolder(val binding: ItemSocialGroupListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
//            if(items.size != 0) {
            binding.tvSocialList.text = items[position].text
            binding.itemSocialGroup.setOnClickListener {
                OnItemClickListener?.onItemClicked(position)
            }
//            } else {
//                binding.tvSocialList.text = "그룹이 아직 없습니다!"
//                binding.ivSocialList.isVisible = false
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSocialGroupListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}