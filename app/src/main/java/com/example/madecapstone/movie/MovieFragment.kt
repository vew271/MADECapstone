package com.example.madecapstone.movie

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
import com.example.madecapstone.databinding.FragmentMovieBinding
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
class MovieFragment : Fragment(), View.OnClickListener {
    private var _fragmentMovieBinding: FragmentMovieBinding? = null
    private val binding get() = _fragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieBinding = FragmentMovieBinding.inflate(inflater, container, false)
        val toolbar: Toolbar = activity?.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        searchView = (activity as MainActivity).findViewById(R.id.msv_searc)
        return binding?.root
    }

    private val viewModel: MovieView by viewModel()
    private lateinit var movieAdapter: MovieAdapter
    private val searchViewModel: SearchView by viewModel()
    private lateinit var searchView: MaterialSearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter()
        setList()
        observeSearchQuery()
        setSearchList()

        with(binding?.rvMovie) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }

        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        searchView.setMenuItem(menuItem)
    }

    private fun setList() {
        viewModel.getMovies().observe(viewLifecycleOwner, moviesObserver)
    }

    private val moviesObserver = Observer<Resource<List<Movie>>> { movies ->
        if (movies != null) {
            when (movies) {
                is Resource.Loading -> setDataState(Status.LOADING)
                is Resource.Success -> {
                    setDataState(Status.SUCCESS)
                    movieAdapter.setData(movies.data)
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
        searchViewModel.movieResult.observe(viewLifecycleOwner, { movies ->
            if (movies.isNullOrEmpty()) {
                setDataState(Status.ERROR)
            } else {
                setDataState(Status.SUCCESS)
            }
            movieAdapter.setData(movies)
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
        binding?.rvMovie?.adapter = null
        _fragmentMovieBinding = null
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}