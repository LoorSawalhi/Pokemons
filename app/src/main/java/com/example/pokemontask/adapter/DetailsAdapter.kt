package com.example.pokemontask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontask.R
import com.example.pokemontask.network.Details

class DetailsAdapter(
    private val details: Details
) : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>(){

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val percent: TextView = itemView.findViewById(R.id.persent)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress)
        val state: TextView = itemView.findViewById(R.id.state)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.details_view, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val stats = details.stats?.get(position)

        println(details)
        if (stats != null) {
            holder.percent.text = stats.baseStat.toString()
            holder.progressBar.progress = stats.baseStat
            holder.state.text = stats.stat.name
        }
    }
    override fun getItemCount(): Int {
        return details.stats?.size ?: 0
    }
}