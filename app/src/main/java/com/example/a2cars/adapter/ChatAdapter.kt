package com.example.a2cars.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a2cars.R
import com.example.a2cars.databinding.ChatUserItemLayoutBinding

import com.example.a2cars.model.usermodel

class ChatAdapter(var context:Context,val list:ArrayList<usermodel>):RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(view: View):RecyclerView.ViewHolder(view){
     var binding:ChatUserItemLayoutBinding=ChatUserItemLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
       return ChatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_user_item_layout,parent,false))
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
       var user=list[position]
        Glide.with(context).load(user.imageUrl).into(holder.binding.userImage)
        holder.binding.userName.text=user.name
    }
}