package com.pcm.gamzigamzi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val context: Context, val onClick: (Sensor) -> Unit): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var userList = mutableListOf<Sensor>()

    fun setListData(data:MutableList<Sensor>){
        userList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        val sensor : Sensor = userList[position]
        holder.bind(sensor)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(sensor: Sensor) {
            with(itemView) {
                val name : TextView = findViewById(R.id.tv_name)
                val id : TextView = findViewById(R.id.tv_id)
                val address : TextView = findViewById(R.id.tv_address)

                name.text = sensor.name
                id.text = sensor.id
                address.text = sensor.address

                setOnClickListener {
                    onClick(sensor)
                }
            }
        }
    }

}