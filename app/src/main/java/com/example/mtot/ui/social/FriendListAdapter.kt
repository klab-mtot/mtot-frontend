package com.example.mtot.ui.social

import android.content.Intent
import android.os.Build.VERSION_CODES.P
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.MainActivity
import com.example.mtot.databinding.ItemSocialFriendListBinding
import com.example.mtot.retrofit2.GetFriendResponse
import com.example.mtot.retrofit2.GetFriendshipResponse
import com.example.mtot.retrofit2.getRetrofitInterface
import com.example.mtot.retrofit2.saveAccessToken
import com.example.mtot.retrofit2.saveMyEmail
import com.example.mtot.retrofit2.setAccessToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendListAdapter(var items: ArrayList<SocialListInfo>) :
    RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemSocialFriendListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
//            if(items.size != 0) {
                binding.tvSocialList.text = items[position].text
//            } else {
//                binding.tvSocialList.text = "친구가 아직 없습니다!"
//                binding.ivSocialList.isVisible = false
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSocialFriendListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}
