package com.devmmurray.marvel.ui.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devmmurray.marvel.R
import com.devmmurray.marvel.ui.view.fragments.CharactersFragment
import com.devmmurray.marvel.ui.view.fragments.ComicsFragment
import com.devmmurray.marvel.ui.view.fragments.SeriesFragment
import com.devmmurray.marvel.ui.viewmodel.MainActivityViewModel


open class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel.checkDatabase()

        val appStart = supportFragmentManager.beginTransaction()
        appStart.add(R.id.nav_host_fragment,
            CharactersFragment()
        )
        appStart.commit()

        // Checking DB to confirm Marvel data has been loaded
        mainActivityViewModel.checkDatabaseLD.observe(this, Observer {
            if (it == null) {
                mainActivityViewModel.getAllCharacters()
            } else {
                Log.d("Main Activity", "******* Database is already loaded ********")
                Toast.makeText(this, "Database is already loaded", Toast.LENGTH_SHORT).show()
            }
        })
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
