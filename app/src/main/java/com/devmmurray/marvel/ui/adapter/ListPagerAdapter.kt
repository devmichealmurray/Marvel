package com.devmmurray.marvel.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devmmurray.marvel.ui.view.fragments.DetailListFragment

class ListPagerAdapter(activity: AppCompatActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount() = itemsCount

    override fun createFragment(position: Int): Fragment {
        return DetailListFragment.getInstance(position)
    }
}