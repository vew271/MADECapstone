package com.example.madecapstone.favorite.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madecapstone.core.domain.model.Movie
import com.example.madecapstone.core.ui.MovieAdapter
import com.example.madecapstone.detail.DetailActivity
import com.example.madecapstone.favorite.R
import com.example.madecapstone.favorite.databinding.FragmentFavoriteMovieBinding
import com.example.madecapstone.favorite.utils.OnSwiped
import com.example.madecapstone.favorite.utils.SwipeHelper
import com.example.madecapstone.utils.Status
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMovieFragment(private val isMovie: Boolean) : Fragment(), View.OnClickListener {
    private var _fragmentFavoriteMovieBinding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _fragmentFavoriteMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentFavoriteMovieBinding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: FavoriteView by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavmovie)

        movieAdapter = MovieAdapter()

        setDataState(Status.LOADING)
        setList()

        with(binding?.rvFavmovie) {
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

    override fun onClick(view: View?) {

    }

    private val itemTouchHelper = SwipeHelper(object : OnSwiped {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movie = movieAdapter.getSwipedData(swipedPosition)
                var state = movie.favorite
                viewModel.setFavorite(movie, !state)
                state = !state
                val snackBar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_ok) {
                    viewModel.setFavorite(movie, !state)
                }
                snackBar.show()
            }
        }
    })

    private fun setList() {
        if (isMovie) {
            viewModel.getFavoriteMovies().observe(viewLifecycleOwner, moviesObserver)
        } else {
            viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, moviesObserver)
        }
    }

    private val moviesObserver = Observer<List<Movie>> { movies ->
        if (movies.isNullOrEmpty()) {
            setDataState(Status.ERROR)
        } else {
            setDataState(Status.SUCCESS)
        }
        movieAdapter.setData(movies)
    }

    private fun setDataState(state: Status) {
        when (state) {
            Status.ERROR -> {
                binding?.progressBar?.visibility = View.GONE
                binding?.ivBlank?.visibility = View.VISIBLE
                binding?.titleEmptyState?.visibility = View.VISIBLE
            }
            Status.LOADING -> {
                binding?.progressBar?.visibility = View.VISIBLE
                binding?.ivBlank?.visibility = View.GONE
                binding?.titleEmptyState?.visibility = View.GONE
            }
            Status.SUCCESS -> {
                binding?.progressBar?.visibility = View.GONE
                binding?.ivBlank?.visibility = View.GONE
                binding?.titleEmptyState?.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvFavmovie?.adapter = null
        _fragmentFavoriteMovieBinding = null
    }
}