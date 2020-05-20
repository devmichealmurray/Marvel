package com.devmmurray.marvel.ui.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.devmmurray.marvel.R
import com.devmmurray.marvel.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel.checkDatabase()

        // Checking DB to confirm Marvel data has been loaded
        mainActivityViewModel.checkDatabaseLD.observe(this, Observer {
            if (it == null) {
                mainActivityViewModel.getAllCharacters()
            } else {
                Log.d("Main Activity", "******* Database is already loaded ********")
                Toast.makeText(this, "Database is already loaded", Toast.LENGTH_SHORT).show()
            }
        })

        /**
         * Home Fragment will become the loading screen
         */

        setupNavigateToCharacters()
        setupNavigateToComics()
        setupNavigateToSeries()


//        marvelLogo.setOnClickListener {
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.nav_host_fragment, HomeFragment())
//            transaction.commit()
//        }
//
//        charactersButton.setOnClickListener {
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.nav_host_fragment, CharactersFragment())
//            transaction.commit()
//        }
//
//        comicsButton.setOnClickListener {
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.nav_host_fragment, ComicsFragment())
//            transaction.commit()
//        }
//
//        seriesButton.setOnClickListener {
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.nav_host_fragment, SeriesFragment())
//            transaction.commit()
//        }

    }

    fun setupNavigateToCharacters() {
        Log.d("setup nav to comics", "****** set up nav to characters called *******")
        mainActivityViewModel.navigateToCharacters.observe(this, Observer {
            nav_host_fragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                NavHostFragment.findNavController(
                    fragment
                ).navigate(R.id.charactersFragment)
            }
        })
    }

    fun setupNavigateToComics() {
        Log.d("setup nav to comics", "****** set up nav to comics called *******")
        mainActivityViewModel.navigateToComics.observe(this, Observer {
            nav_host_fragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                NavHostFragment.findNavController(
                    fragment
                ).navigate(R.id.comicsFragment)
            }
        })
    }

    fun setupNavigateToSeries() {
        Log.d("setup nav to comics", "****** set up nav to characters called *******")
        mainActivityViewModel.navigateToSeries.observe(this, Observer {
            nav_host_fragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                NavHostFragment.findNavController(
                    fragment
                ).navigate(R.id.seriesFragment)
            }
        })
    }

}
