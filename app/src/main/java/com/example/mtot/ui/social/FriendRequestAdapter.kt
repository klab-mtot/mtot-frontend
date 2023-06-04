package com.example.mtot.ui.social

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.ItemFriendRequestListRowBinding
import com.example.mtot.retrofit2.PendingFriendshipsData
import com.example.mtot.retrofit2.getPostState
import com.example.mtot.retrofit2.getRetrofitInterface
import com.example.mtot.retrofit2.saveFriendData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendRequestAdapter(var items: ArrayList<FriendRequestListInfo>) :
    RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>() {

    var OnItemClickListener : onItemClickListener? = null
    interface onItemClickListener {
        fun onAcceptButtonClicked(position: Int)
        fun onRejectButtonClicked(position: Int)
    }

    inner class ViewHolder(private val binding: ItemFriendRequestListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = items[position]
            binding.friendName.text = item.name

            binding.acceptRequest.setOnClickListener {
                OnItemClickListener?.onAcceptButtonClicked(position)
            }

            binding.cancelRequest.setOnClickListener {
                OnItemClickListener?.onRejectButtonClicked(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFriendRequestListRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
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
        Log.d("jin", "item removed : " + items.toString())
        notifyItemRemoved(position)
        Log.d("jin", "notified : " + items.toString())

    }

}
