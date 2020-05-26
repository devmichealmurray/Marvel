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
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "Main Activity"

open class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loading = supportFragmentManager.beginTransaction()
        loading.add(R.id.nav_host_fragment, HomeFragment())
            .commit()

        mainActivityViewModel.checkForUpdate()
        mainActivityViewModel.characterUpToDate.observe(this, characterUpdateObserver)

//        mainActivityViewModel.deleteData()
    }


    private val characterUpdateObserver = Observer<Boolean> {
        if (it) {
            val appStart = supportFragmentManager.beginTransaction()
            appStart.replace(
                R.id.nav_host_fragment,
                CharactersFragment()
            )
            appStart.commit()
            toolbar.visibility = View.VISIBLE
        }
    }

    fun goToCharactersFragment(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.nav_host_fragment,
            CharactersFragment()
        )
        transaction.commit()
        toolbar.visibility = View.VISIBLE
    }

    fun goToComicsFragment(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.nav_host_fragment,
            ComicsFragment()
        )
        transaction.commit()
        toolbar.visibility = View.VISIBLE
    }

    fun goToSeriesFragment(view: View) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.nav_host_fragment,
            SeriesFragment()
        )
        transaction.commit()
        toolbar.visibility = View.VISIBLE
    }

}
