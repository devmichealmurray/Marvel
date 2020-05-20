package com.devmmurray.marvel.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.devmmurray.marvel.R
import com.devmmurray.marvel.ui.viewmodel.SeriesViewModel

class SeriesFragment : Fragment() {

    private val seriesViewModel: SeriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Up Navigation Buttons
//        setupNavigateToCharacters()
//        setupNavigateToComics()
//        setupNavigateToSeries()

    }

    /**
     *  Navigation Observers
     */
//
//    private fun setupNavigateToCharacters() {
//        seriesViewModel.navigateToCharacters.observe(this, Observer {
//            NavHostFragment.findNavController(this).navigate(R.id.charactersFragment)
//        })
//    }
//
//    private fun setupNavigateToComics() {
//        seriesViewModel.navigateToComics.observe(this, Observer {
//            NavHostFragment.findNavController(this).navigate(R.id.comicsFragment)
//        })
//    }
//
//    private fun setupNavigateToSeries() {
//        seriesViewModel.navigateToSeries.observe(this, Observer {
//            NavHostFragment.findNavController(this).navigate(R.id.seriesFragment)
//        })
//    }

}
