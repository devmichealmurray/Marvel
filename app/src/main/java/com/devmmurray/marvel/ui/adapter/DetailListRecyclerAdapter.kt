package com.devmmurray.marvel.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.marvel.R
import com.devmmurray.marvel.data.model.domain.Comic
import com.devmmurray.marvel.data.model.domain.Series
import com.squareup.picasso.Picasso

class DetailListRecyclerHolder(v: View) : RecyclerView.ViewHolder(v) {
    val view = v
    fun bindComic(comic: Comic) {
        val cardImage: ImageView = view.findViewById(R.id.cardRecyclerImage)
//        cardImage.setOnClickListener { character.marvelId?.let { id ->
//            moveToDetailActivity(cardImage, id) } }
        val cardText: TextView = view.findViewById(R.id.cardRecyclerText)
        Picasso.get()
            .load(comic.thumbnail)
            .error(R.drawable.marvel_placeholder)
            .placeholder(R.drawable.marvel_placeholder)
            .fit()
            .into(cardImage)

        cardText.text = comic.title
    }

    fun bindSeries(series: Series) {
        val cardImage: ImageView = view.findViewById(R.id.cardRecyclerImage)
//        cardImage.setOnClickListener { character.marvelId?.let { id ->
//            moveToDetailActivity(cardImage, id) } }
        val cardText: TextView = view.findViewById(R.id.cardRecyclerText)
        Picasso.get()
            .load(series.thumbnail)
            .error(R.drawable.marvel_placeholder)
            .placeholder(R.drawable.marvel_placeholder)
            .fit()
            .into(cardImage)

        cardText.text = series.title
    }
}

class DetailListRecyclerAdapter(private val list: ArrayList<Any>, flag: String) :
    RecyclerView.Adapter<DetailListRecyclerHolder>() {

    private val objectFlag = flag

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailListRecyclerHolder {
        return DetailListRecyclerHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_layout_item, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: DetailListRecyclerHolder, position: Int) {
        when (objectFlag) {
            "comic" -> holder.bindComic(list[position] as Comic)
            else -> holder.bindSeries(list[position] as Series)
        }

    }
}