package com.devmmurray.marvel.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmmurray.marvel.R
import com.devmmurray.marvel.data.Lists.Companion.popularCharacterArray
import com.devmmurray.marvel.data.model.RetrofitFlags
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.ui.adapter.MarvelRecyclerAdapter
import com.devmmurray.marvel.ui.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val firstAdapter = MarvelRecyclerAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.character.observe(viewLifecycleOwner, characterObserver)

        homeViewModel.getAllCharacters()
        homeViewModel.characterList.observe(viewLifecycleOwner, characterListObserver)
        homeViewModel.refresh("1009351", RetrofitFlags.SINGLE_CHARACTER)
        homeViewModel.loadCharacterArray(popularCharacterArray, RetrofitFlags.CHARACTER_LIST)

        firstRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = firstAdapter
        }
    }

    private val characterObserver = Observer<CharacterObject> {
        Picasso.get()
            .load(it.posterImage)
            .error(R.drawable.marvel_placeholder)
            .placeholder(R.drawable.marvel_placeholder)
            .fit()
            .into(mainCharacterImage)
        mainCharacterName.text = it.name?.toUpperCase()
        mainCharacterDescription.text = it.description
    }


    private val characterListObserver = Observer<ArrayList<CharacterObject>> { list ->
        list?.let {
            Log.d(TAG, "**** Character List Observer Called ******")
            Log.d(TAG, "******* Character List Observer: Size = ${it.size} **********")
            firstAdapter.updatePhotoList(it)
        }
    }

}
