package com.devmmurray.marvel.ui.view.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.devmmurray.marvel.R
import com.devmmurray.marvel.ui.adapter.DetailListRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_detail_list.*

class DetailListFragment : Fragment() {

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val listFragment =
                DetailListFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            listFragment.arguments = bundle
            return listFragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater
            .inflate(R.layout.fragment_detail_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailListRecycler.layoutManager = GridLayoutManager(context, 3)

        val position = requireArguments().getInt(ARG_POSITION)
        val listOne = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L")
        val listTwo = listOf("z", "y", "x", "w", "v", "u", "t", "s", "r", "q", "p", "o")

        when (position) {
            0 -> detailListRecycler.adapter = DetailListRecyclerAdapter(listOne)
            else -> detailListRecycler.adapter = DetailListRecyclerAdapter(listTwo)
        }
    }

}
