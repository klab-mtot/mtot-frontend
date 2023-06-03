package com.example.mtot.ui.social

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.ItemFriendRequestListRowBinding
import com.example.mtot.retrofit2.getRetrofitInterface

class FriendRequestAdapter(val items: ArrayList<FriendRequestListInfo>) :RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>() {

    var friendRequestInterface= getRetrofitInterface()

    inner class ViewHolder(var binding: ItemFriendRequestListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.friendName.text = items[position].name
            binding.acceptRequest.setOnClickListener {
                friendRequestInterface.acceptPendingFriendship(items[position].friendshipId)
                removeItem(position)
            }
            binding.cancelRequest.setOnClickListener {
                friendRequestInterface.rejectPendingFriendship(items[position].friendshipId)
                removeItem(position)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemFriendRequestListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun removeItem(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }

}
