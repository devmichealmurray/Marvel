package com.devmmurray.marvel.ui.view.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devmmurray.marvel.R
import com.devmmurray.marvel.ui.view.fragments.CharactersFragment
import com.devmmurray.marvel.ui.view.fragments.ComicsFragment
import com.devmmurray.marvel.ui.view.fragments.HomeFragment
import com.devmmurray.marvel.ui.view.fragments.SeriesFragment
import com.devmmurray.marvel.ui.viewmodel.MainActivityViewModel

private const val TAG = "Main Activity"

open class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loading = supportFragmentManager.beginTransaction()
        loading.add(
            R.id.nav_host_fragment,
            HomeFragment()
        )
        loading.commit()

        mainActivityViewModel.countCharacters()

        // Checking DB to confirm Marvel data has been loaded
//        mainActivityViewModel.loadCharacters()
//        mainActivityViewModel.loadComics()
//        mainActivityViewModel.loadSeries()

        mainActivityViewModel.characterUpToDate.observe(this, characterObserver)
    }


    private val characterObserver = Observer<Boolean> {
        if (it) {
            val appStart = supportFragmentManager.beginTransaction()
            appStart.add(
                R.id.nav_host_fragment,
                CharactersFragment()
            )
            appStart.commit()
        }
    }

    fun goToCharactersFragment(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.nav_host_fragment,
            CharactersFragment()
        )
        transaction.commit()
    }

    fun goToComicsFragment(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.nav_host_fragment,
            if (mainActivityViewModel.comicsUpToDate.value == true) ComicsFragment() else HomeFragment()
        )
        transaction.commit()
    }

    fun goToSeriesFragment(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.nav_host_fragment,
            if (mainActivityViewModel.seriesUpToDate.value == true) SeriesFragment() else HomeFragment()
        )
        transaction.commit()
    }

}
