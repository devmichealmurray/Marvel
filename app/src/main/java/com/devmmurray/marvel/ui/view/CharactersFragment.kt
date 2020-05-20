package com.devmmurray.marvel.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmmurray.marvel.R
import com.devmmurray.marvel.data.Lists
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.ui.adapter.MarvelRecyclerAdapter
import com.devmmurray.marvel.ui.viewmodel.CharactersViewModel
import com.devmmurray.marvel.util.CharacterRecyclerFlags
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_characters.*

class CharactersFragment : Fragment() {

    private val charactersViewModel: CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Load Character Lists
        charactersViewModel.loadList(Lists.popularCharacterArray, CharacterRecyclerFlags.POPULAR)
        charactersViewModel.loadList(Lists.femaleCharacterArray, CharacterRecyclerFlags.FEMALE)
        charactersViewModel.loadList(Lists.topVillainsArray, CharacterRecyclerFlags.VILLAIN)
        charactersViewModel.loadList(Lists.avengersMap, CharacterRecyclerFlags.AVENGERS)
        charactersViewModel.loadList(Lists.spidermanMap, CharacterRecyclerFlags.SPIDERMAN)
        charactersViewModel.loadList(Lists.xmenMap, CharacterRecyclerFlags.XMEN)
        charactersViewModel.loadList(Lists.classicsMap, CharacterRecyclerFlags.CLASSICS)
        charactersViewModel.loadList(Lists.punisherMap, CharacterRecyclerFlags.PUNISHER)

        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        charactersViewModel.getMainCharacter()
        charactersViewModel.getCharacterLD.observe(viewLifecycleOwner, Observer {
            it?.let { loadNewCharacter(it) }
        })


        // Recycler List Observers
        charactersViewModel.popularListLD.observe(viewLifecycleOwner, popularListObserver)
        charactersViewModel.femaleListLD.observe(viewLifecycleOwner, femaleListObserver)
        charactersViewModel.villainListLD.observe(viewLifecycleOwner, villainListObserver)
        charactersViewModel.avengerListLD.observe(viewLifecycleOwner, avengersListObserver)
        charactersViewModel.spidermanListLD.observe(viewLifecycleOwner, spidermanListObserver)
        charactersViewModel.xmenListLD.observe(viewLifecycleOwner, xmenListObserver)
        charactersViewModel.classicsListLD.observe(viewLifecycleOwner, classicsListObserver)
        charactersViewModel.punisherListLD.observe(viewLifecycleOwner, punisherListObserver)

        // Recycler LayoutManagers
        firstRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        femaleRecyler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        villainRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        avengersRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        spidermanRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        xmenRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        classicsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        punisherRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }

    // Function to load Main Character Image and Text
    private fun loadNewCharacter(character: CharacterObject) {
        Picasso.get()
            .load(character.poster)
            .error(R.drawable.marvel_placeholder)
            .placeholder(R.drawable.hulk)
            .resize(1200, 0)
            .centerInside()
            .into(charactersMainImage)

        mainCharacterName.text = character.name?.toUpperCase()
        mainCharacterDescription.text = character.description
    }


    /**
     *  Live Data Observer Variables
     */

    private val popularListObserver = Observer<ArrayList<CharacterObject>> {
        it?.let {
            firstRecycler.adapter = MarvelRecyclerAdapter(it, CharacterRecyclerFlags.POPULAR)
        }
        firstRecycler.visibility = View.VISIBLE
    }


    private val femaleListObserver = Observer<ArrayList<CharacterObject>> {
        it?.let {
            femaleRecyler.adapter = MarvelRecyclerAdapter(it, CharacterRecyclerFlags.FEMALE)
        }
        femaleRecyler.visibility = View.VISIBLE
    }

    private val villainListObserver = Observer<ArrayList<CharacterObject>> {
        it?.let {
            villainRecycler.adapter = MarvelRecyclerAdapter(it, CharacterRecyclerFlags.VILLAIN)
        }
        villainRecycler.visibility = View.VISIBLE
    }

    private val avengersListObserver = Observer<ArrayList<CharacterObject>> {
        it?.let {
            avengersRecycler.adapter = MarvelRecyclerAdapter(it, CharacterRecyclerFlags.AVENGERS)
        }
        avengersRecycler.visibility = View.VISIBLE
    }

    private val spidermanListObserver = Observer<ArrayList<CharacterObject>> {
        it?.let {
            spidermanRecycler.adapter = MarvelRecyclerAdapter(it, CharacterRecyclerFlags.SPIDERMAN)
        }
        spidermanRecycler.visibility = View.VISIBLE
    }

    private val xmenListObserver = Observer<ArrayList<CharacterObject>> {
        it?.let {
            xmenRecycler.adapter = MarvelRecyclerAdapter(it, CharacterRecyclerFlags.XMEN)
        }
        xmenRecycler.visibility = View.VISIBLE
    }

    private val classicsListObserver = Observer<ArrayList<CharacterObject>> {
        it?.let {
            classicsRecycler.adapter = MarvelRecyclerAdapter(it, CharacterRecyclerFlags.CLASSICS)
        }
        classicsRecycler.visibility = View.VISIBLE
    }

    private val punisherListObserver = Observer<ArrayList<CharacterObject>> {
        it?.let {
            punisherRecycler.adapter = MarvelRecyclerAdapter(it, CharacterRecyclerFlags.PUNISHER)
        }
        punisherRecycler.visibility = View.VISIBLE
    }


}
