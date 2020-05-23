package com.devmmurray.marvel.ui.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.devmmurray.marvel.R
import com.devmmurray.marvel.ui.adapter.ListPagerAdapter
import com.devmmurray.marvel.ui.viewmodel.CharacterDetailActivityViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_character_detail.*

const val CHARACTER_ID = "character_id"

class CharacterDetail : AppCompatActivity() {

    private val characterDetailViewModel: CharacterDetailActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
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



    }
}
