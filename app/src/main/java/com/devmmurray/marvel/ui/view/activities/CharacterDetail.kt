package com.devmmurray.marvel.ui.view.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devmmurray.marvel.R
import com.devmmurray.marvel.data.model.domain.CharacterObject
import com.devmmurray.marvel.ui.adapter.ListPagerAdapter
import com.devmmurray.marvel.ui.viewmodel.CharacterDetailActivityViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character_detail.*

const val CHARACTER_ID = "character_id"

private const val TAG = "CharacterDetail"

class CharacterDetail : AppCompatActivity() {

    private val characterDetailViewModel: CharacterDetailActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "* * * * *  onCreate Called * * * * * * ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        // Set Up ViewPager & TabLayout
        val tabNames = arrayListOf("Comics", "Series")
        val listPagerAdapter = ListPagerAdapter(this, tabNames.size)
        listViewPager.adapter = listPagerAdapter
        TabLayoutMediator(tabLayout, listViewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        // Retrieve Marvel Id passed from Recycler that started the activity
        val marvelId = intent.extras?.getInt(CHARACTER_ID)

        // Call the getCharacter function from the viewModel to return details
        if (marvelId != null) {
            characterDetailViewModel.getCharacter(marvelId)
        }

        characterDetailViewModel.returnedCharacter.observe(this, characterDetailObserver)
    }

    private val characterDetailObserver = Observer<CharacterObject> {
        Picasso.get()
            .load(it.poster)
            .error(R.drawable.marvel_placeholder)
            .placeholder(R.drawable.marvel_placeholder)
            .fit()
            .into(characterImage)


        name.text = it.name
        description.text = it.description
    }

}
