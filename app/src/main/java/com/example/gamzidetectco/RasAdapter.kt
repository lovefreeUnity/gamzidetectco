package com.example.gamzidetectco

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RasAdapter(val RasList: ArrayList<Rasfile>) : RecyclerView.Adapter<RasAdapter.RasViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RasAdapter.RasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ras_item,parent,false)
        return RasViewHolder(view).apply {
            itemView.setOnClickListener { v ->
                val intent = Intent(v.context, MainActivity::class.java)
                v.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return RasList.size
    }

    override fun onBindViewHolder(holder: RasAdapter.RasViewHolder, position: Int) {
        holder.Ras_Name.text = RasList.get(position).Ras_Name
        holder.Ras_Address.text = RasList.get(position).Ras_Address
        holder.Btnitem.setOnClickListener { v ->
            val intent = Intent(v.context, AddRasActivity::class.java)
            v.context.startActivity(intent)
        }
    }


    inner class RasViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val Ras_Name = itemView.findViewById<TextView>(R.id.Tv_Rasname)
        val Ras_Address = itemView.findViewById<TextView>(R.id.Tv_Rasadrress)
        val Btnitem = itemView.findViewById<TextView>(R.id.btn_item)
    }
}