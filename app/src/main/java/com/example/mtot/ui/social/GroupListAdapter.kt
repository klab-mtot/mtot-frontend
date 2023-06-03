package com.example.mtot.ui.social

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.ItemSocialGroupListBinding
import com.example.mtot.retrofit2.GetTeamResponse
import com.example.mtot.retrofit2.GetTeamsResponse
import com.example.mtot.retrofit2.getRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupListAdapter :
    RecyclerView.Adapter<GroupListAdapter.ViewHolder>() {

    val groupInterface = getRetrofitInterface()
    var groupList: List<GetTeamResponse>? = null
    inner class ViewHolder(val binding: ItemSocialGroupListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            if(groupList != null) {
               binding.tvSocialList.text = groupList!![position].teamName
                binding.ivSocialList.isVisible = false
            } else {
                binding.tvSocialList.text = "그룹이 아직 없습니다!"
                binding.ivSocialList.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSocialGroupListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return groupList?.size ?: 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun updateGroupList(friends: List<GetTeamResponse>?) {
        groupList = friends
        notifyDataSetChanged()
    }

    fun fetchFriend() {
        groupInterface.getTeams().enqueue(object : Callback<GetTeamsResponse> {
            override fun onResponse(
                call: Call<GetTeamsResponse>,
                response: Response<GetTeamsResponse>
            ) {
                if (response.isSuccessful) {
                    val grouplist = response.body()?.teamList
                    updateGroupList(grouplist)
                }
            }

            override fun onFailure(call: Call<GetTeamsResponse>, t: Throwable) {
                Log.d("errorbyby", t.message.toString())
            }
        })
    }
}