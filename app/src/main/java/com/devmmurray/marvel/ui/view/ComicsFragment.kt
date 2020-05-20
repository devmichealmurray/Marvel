package com.devmmurray.marvel.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.devmmurray.marvel.R
import com.devmmurray.marvel.ui.viewmodel.ComicsViewModel

class ComicsFragment : Fragment() {

    private val comicsViewModel: ComicsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_comics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Up Navigation Buttons
        setupNavigateToCharacters()
        setupNavigateToComics()
        setupNavigateToSeries()
    }

    /**
     *  Navigation Observers
     */

    private fun setupNavigateToCharacters() {
        comicsViewModel.navigateToCharacters.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.charactersFragment)
        })
    }

    private fun setupNavigateToComics() {
        comicsViewModel.navigateToComics.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.comicsFragment)
        })
    }

    private fun setupNavigateToSeries() {
        comicsViewModel.navigateToSeries.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.seriesFragment)
        })
    }

}
