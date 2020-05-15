package com.devmmurray.marvel.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devmmurray.marvel.R
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appStart = supportFragmentManager.beginTransaction()
        appStart.add(R.id.nav_host_fragment, HomeFragment())
        appStart.commit()

        marvelLogo.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, HomeFragment())
            transaction.commit()
        }

        charactersButton.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, CharactersFragment())
            transaction.commit()
        }

        comicsButton.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, ComicsFragment())
            transaction.commit()
        }

        seriesButton.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, SeriesFragment())
            transaction.commit()
        }

    }


    private val exceptionObserver = Observer<Boolean> {
        if (it) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

}
