package com.devmmurray.marvel.ui.view.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devmmurray.marvel.R
import com.devmmurray.marvel.ui.view.fragments.CharactersFragment
import com.devmmurray.marvel.ui.view.fragments.ComicsFragment
import com.devmmurray.marvel.ui.view.fragments.HomeFragment
import com.devmmurray.marvel.ui.view.fragments.SeriesFragment
import com.devmmurray.marvel.ui.viewmodel.MainActivityViewModel


open class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        application.deleteDatabase("characters-db")

        val loading = supportFragmentManager.beginTransaction()
        loading.add(R.id.nav_host_fragment,
            HomeFragment()
        )
        loading.commit()

        // Checking DB to confirm Marvel data has been loaded
        mainActivityViewModel.checkDatabase()

        // Observer to confirm db is loaded and ready to use
        mainActivityViewModel.dataBaseIsLoaded.observe(this, dataBaseObserver)
        // Observer updates user with loading progress Toast messages
        mainActivityViewModel.dataLoadingToasts.observe(this, loadingToastsObserver)
    }


    private val dataBaseObserver = Observer<Boolean> {
        if (it) {
            val appStart = supportFragmentManager.beginTransaction()
            appStart.add(R.id.nav_host_fragment,
                CharactersFragment()
            )
            appStart.commit()
        }
    }

    private val loadingToastsObserver = Observer<Int> {
        val message = when (it) {
            1 -> "Character Data Loaded"
            2 -> "Comic Data Loaded"
            else -> "Series Data Loaded"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun goToCharactersFragment(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment,
            CharactersFragment()
        )
        transaction.commit()
    }

    fun goToComicsFragment(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment,
            ComicsFragment()
        )
        transaction.commit()
    }

    fun goToSeriesFragment(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment,
            SeriesFragment()
        )
        transaction.commit()
    }

}
