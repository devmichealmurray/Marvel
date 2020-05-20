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
import com.devmmurray.marvel.util.CharacterRecyclerFlags
import com.squareup.picasso.Picasso

private const val TAG = "MarvelRecyclerView"

class MarvelViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val view = v

    fun bindPrimary(character: CharacterObject) {
        val imageHolder: ImageView = view.findViewById(R.id.circleRecyclerImage)
        val textHolder: TextView = view.findViewById(R.id.circleRecyclerText)
        Picasso.get()
            .load(character.thumbnail)
            .error(R.drawable.marvel_placeholder)
            .placeholder(R.drawable.marvel_placeholder)
            .fit()
            .into(imageHolder)

        textHolder.text = character.name?.toUpperCase()
    }

    fun bindCard(character: CharacterObject) {
        val cardImage: ImageView = view.findViewById(R.id.cardRecyclerImage)
        val cardText: TextView = view.findViewById(R.id.cardRecyclerText)
        Picasso.get()
            .load(character.thumbnail)
            .error(R.drawable.marvel_placeholder)
            .placeholder(R.drawable.marvel_placeholder)
            .fit()
            .into(cardImage)

        cardText.text = character.name?.toUpperCase()
    }
}


class MarvelRecyclerAdapter(
    private val list: ArrayList<CharacterObject>,
    flags: CharacterRecyclerFlags
) :
    RecyclerView.Adapter<MarvelViewHolder>() {

    private val flag = flags

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelViewHolder {
        return when (flag) {
            CharacterRecyclerFlags.POPULAR -> MarvelViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.circle_recycler_item, parent, false)
            )
            else -> MarvelViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.card_layout_item, parent, false)
            )
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MarvelViewHolder, position: Int) {
        when (flag) {
            CharacterRecyclerFlags.POPULAR -> holder.bindPrimary(list[position])
            else -> holder.bindCard(list[position])
        }

    }

    fun updatePhotoList(newList: ArrayList<CharacterObject>) {
        Log.d(TAG, "___________ Update Photo List Called ___________")
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}

