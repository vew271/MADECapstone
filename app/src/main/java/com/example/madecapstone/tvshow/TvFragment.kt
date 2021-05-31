package com.example.madecapstone.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madecapstone.R
import com.example.madecapstone.core.data.Resource
import com.example.madecapstone.core.domain.model.Movie
import com.example.madecapstone.core.ui.MovieAdapter
import com.example.madecapstone.databinding.FragmentTvBinding
import com.example.madecapstone.detail.DetailActivity
import com.example.madecapstone.main.MainActivity
import com.example.madecapstone.main.SearchView
import com.example.madecapstone.utils.Status
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class TvFragment : Fragment(), View.OnClickListener {

    private var fragmentTvBinding: FragmentTvBinding? = null
    private val binding get() = fragmentTvBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTvBinding = FragmentTvBinding.inflate(inflater, container, false)
        val toolbar: Toolbar = activity?.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        searchView = (activity as MainActivity).findViewById(R.id.msv_searc)
        return binding?.root
    }

    private val viewModel: TvView by viewModel()
    private lateinit var tvAdapter: MovieAdapter
    private val searchViewModel: SearchView by viewModel()
    private lateinit var searchView: MaterialSearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvAdapter = MovieAdapter()
        setList()
        observeSearchQuery()
        setSearchList()

        with(binding?.rvTv) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = tvAdapter
        }

        tvAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }
    }

    override fun onClick(view: View?) {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        searchView.setMenuItem(menuItem)
    }

    private fun setList() {
        viewModel.getTvShows().observe(viewLifecycleOwner, tvShowsObserver)
    }

    private val tvShowsObserver = Observer<Resource<List<Movie>>> { tvShow ->
        if (tvShow != null) {
            when (tvShow) {
                is Resource.Loading -> setDataState(Status.LOADING)
                is Resource.Success -> {
                    setDataState(Status.SUCCESS)
                    tvAdapter.setData(tvShow.data)
                }
                is Resource.Error -> {
                    setDataState(Status.ERROR)
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeSearchQuery() {
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchViewModel.setSearchQuery(it) }
                return true
            }

        })
    }

    private fun setSearchList() {
        searchViewModel.tvShowResult.observe(viewLifecycleOwner, { tvShows ->
            if (tvShows.isNullOrEmpty()) {
                setDataState(Status.ERROR)
            } else {
                setDataState(Status.SUCCESS)
            }
            tvAdapter.setData(tvShows)
        })
        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                setDataState(Status.SUCCESS)
                setList()
            }

            override fun onSearchViewClosed() {
                setDataState(Status.SUCCESS)
                setList()
            }
        })
    }

    private fun setDataState(state: Status) {
        when (state) {
            Status.ERROR -> {
                binding?.progressBar?.visibility = View.GONE
                binding?.tvNotfound?.visibility = View.VISIBLE
            }
            Status.LOADING -> {
                binding?.progressBar?.visibility = View.VISIBLE
                binding?.tvNotfound?.visibility = View.GONE
            }
            Status.SUCCESS -> {
                binding?.progressBar?.visibility = View.GONE
                binding?.tvNotfound?.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
        searchView.setOnSearchViewListener(null)
        binding?.rvTv?.adapter = null
        fragmentTvBinding = null
    }
}