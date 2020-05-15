package com.devmmurray.marvel.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.marvel.R
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.squareup.picasso.Picasso

private const val TAG = "MarvelRecyclerView"

class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val imageHolder: ImageView = view.findViewById(R.id.circleRecyclerImage)
    private val textHolder: TextView = view.findViewById(R.id.circleRecyclerText)
    fun bind(character: CharacterObject) {
        Log.d(TAG, "_________ View Holder Bind Called _________")
        Picasso.get()
            .load(character.thumbnailLink)
            .error(R.drawable.marvel_placeholder)
            .placeholder(R.drawable.marvel_placeholder)
            .fit()
            .into(imageHolder)

        textHolder.text = character.name?.toUpperCase()
    }
}

class MarvelRecyclerAdapter(private val list: ArrayList<CharacterObject>) :
    RecyclerView.Adapter<MarvelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        return MarvelViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.circle_recycler_item, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updatePhotoList(newList: ArrayList<CharacterObject>) {
        Log.d(TAG, "___________ Update Photo List Called ___________")
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}

