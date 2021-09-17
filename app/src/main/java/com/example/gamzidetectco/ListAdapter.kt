package com.example.gamzidetectco

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val context: Context): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var userList = mutableListOf<User>()

    fun setListData(data:MutableList<User>){
        userList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        val user : User = userList[position]
        holder.name.text = user.name
        holder.id.text = user.id
        holder.address.text = user.address

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.tv_name)
        val id : TextView = itemView.findViewById(R.id.tv_id)
        val address : TextView = itemView.findViewById(R.id.tv_address)

    }

}