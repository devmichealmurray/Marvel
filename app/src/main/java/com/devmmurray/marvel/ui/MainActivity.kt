package com.devmmurray.marvel.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devmmurray.marvel.R
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.ui.viewmodel.BaseViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseViewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        baseViewModel.refresh("1009351")
    }

    override fun onStart() {
        super.onStart()
        baseViewModel.character.observe(this, characterObserver)
        baseViewModel.exceptionWatcher.observe(this, exceptionObserver)
    }


    private val characterObserver = Observer<CharacterObject> {
        if (it != null) {
            Toast.makeText(this, "Seems to be working???", Toast.LENGTH_SHORT).show()
        }
    }

    private val exceptionObserver = Observer<Boolean> {
        if (it) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

}
