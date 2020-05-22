package com.devmmurray.marvel.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.marvel.R

class DetailListRecyclerHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val holder: TextView = view.findViewById(R.id.cardView)
    fun bind(item: String) {
        holder.text = item
    }
}

class DetailListRecyclerAdapter(private val list: List<String>) :
    RecyclerView.Adapter<DetailListRecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailListRecyclerHolder {
        return DetailListRecyclerHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_list_recycler_item, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: DetailListRecyclerHolder, position: Int) {
        holder.bind(list[position])
    }
}