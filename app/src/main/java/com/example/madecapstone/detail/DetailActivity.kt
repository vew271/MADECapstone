package com.example.madecapstone.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.madecapstone.R
import com.example.madecapstone.core.domain.model.Movie
import com.example.madecapstone.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIE = "extraMovie"
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailView by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        if (detailMovie != null) {
            populateDetail(detailMovie)
        }

        binding.backButton.setOnClickListener { onBackPressed() }
        binding.share.setOnClickListener { share() }
    }

    private fun populateDetail(movie: Movie) {
        with(binding) {
            titleDetail.text = movie.title
            date.text = movie.releaseDate
            overview.text = movie.overview
            popularity.text = getString(
                R.string.popularity_detail,
                movie.popularity.toString(),
                movie.voteCount.toString(),
                movie.voteAverage.toString()
            )
            userScore.text = movie.voteAverage.toString()
            Glide.with(this@DetailActivity)
                .load(getString(R.string.baseUrlImage, movie.posterPath))
                .into(posterTopBar)
            posterTopBar.tag = movie.posterPath

            Glide.with(this@DetailActivity)
                .load(getString(R.string.baseUrlImage, movie.posterPath))
                .into(subPoster)
            subPoster.tag = movie.posterPath

            var favoriteState = movie.favorite
            setFavoriteState(favoriteState)
            binding.favoriteButton.setOnClickListener {
                favoriteState = !favoriteState
                viewModel.setFavoriteMovie(movie, favoriteState)
                setFavoriteState(favoriteState)
            }
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this.applicationContext,
                    R.drawable.ic_fav_red
                )
            )
        } else {
            binding.favoriteButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this.applicationContext,
                    R.drawable.ic_baseline_favorite_24
                )
            )
        }
    }

    override fun onStop() {
        super.onStop()
        binding.posterTopBar.pause()
    }

    private fun share() {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder.from(this).apply {
            setType(mimeType)
            setChooserTitle(getString(R.string.shareTitle))
            setText(getString(R.string.shareBody))
            startChooser()
        }
    }
}