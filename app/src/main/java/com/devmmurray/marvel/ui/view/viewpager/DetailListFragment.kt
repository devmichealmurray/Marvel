package com.devmmurray.marvel.ui.view.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.devmmurray.marvel.R
import com.devmmurray.marvel.data.model.domain.Comic
import com.devmmurray.marvel.data.model.domain.Series
import com.devmmurray.marvel.ui.adapter.DetailListRecyclerAdapter
import com.devmmurray.marvel.ui.viewmodel.CharacterDetailActivityViewModel
import kotlinx.android.synthetic.main.fragment_detail_list.*

class DetailListFragment : Fragment() {

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val listFragment = DetailListFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            listFragment.arguments = bundle
            return listFragment
        }
    }

    private val detailFragmentViewModel: CharacterDetailActivityViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater
            .inflate(R.layout.fragment_detail_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailFragmentViewModel.returnedComics.observe(viewLifecycleOwner, characterComicsObserver)
        detailFragmentViewModel.returnedSeries.observe(viewLifecycleOwner, characterSeriesObserver)

        detailListRecycler.layoutManager = GridLayoutManager(context, 3)

        when (requireArguments().getInt(ARG_POSITION)) {
            0 -> characterComicsObserver
            else -> characterSeriesObserver
        }
    }

    private val characterComicsObserver = Observer<ArrayList<Comic>> {
        it?.let {
            detailListRecycler.adapter = DetailListRecyclerAdapter(it as ArrayList<Any>, "comic")
        }
    }

    private val characterSeriesObserver = Observer<ArrayList<Series>> {
        it?.let {
            detailListRecycler.adapter = DetailListRecyclerAdapter(it as ArrayList<Any>, "series")
        }
    }

}
