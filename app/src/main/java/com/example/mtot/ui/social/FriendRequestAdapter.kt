package com.example.mtot.ui.social

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.ItemFriendRequestListRowBinding
import com.example.mtot.retrofit2.PendingFriendshipsData
import com.example.mtot.retrofit2.getRetrofitInterface
import com.example.mtot.retrofit2.saveFriendData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendRequestAdapter(private var items: ArrayList<FriendRequestListInfo>) :
    RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>() {

    private val friendRequestInterface = getRetrofitInterface()

    inner class ViewHolder(private val binding: ItemFriendRequestListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = items[position]
            binding.friendName.text = item.name

            binding.acceptRequest.setOnClickListener {
                friendRequestInterface.acceptPendingFriendship(item.friendshipId)
                    .enqueue(object : Callback<String> {
                        override fun onResponse(
                            call: Call<String>,
                            response: Response<String>
                        ) {
                            Log.d("HHH",response.body().toString())
                            if (response.isSuccessful) {
                                removeItem(position)
                            }
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            // Handle failure
                            Log.d("QQQ", t.message.toString())
                        }
                    })
            }

            binding.cancelRequest.setOnClickListener {
                friendRequestInterface.rejectPendingFriendship(item.friendshipId)
                    .enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            Log.d("HHH",response.body().toString())
                            if (response.isSuccessful) {
                                // 목록 갱신
                                removeItem(position)
                            }
                        }
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.d("QQQ", t.message.toString())
                        }
                    })
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

    private fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}
