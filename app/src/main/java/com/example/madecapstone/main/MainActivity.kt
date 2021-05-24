package com.example.madecapstone.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.madecapstone.R
import com.example.madecapstone.databinding.ActivityMainBinding
import com.example.madecapstone.movie.MovieFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationChange(MovieFragment())
    }

    private fun moveToFavoriteFragment() {
        val fragment = instantiateFragment()
        Log.d("fragmentName", fragment.toString())
        if (fragment != null) {
            navigationChange(fragment)
        }
    }

    private fun instantiateFragment(): Fragment? {
        return try {
            Class.forName("com.example.madecapstone.favorite.FavoriteFragment").newInstance() as Fragment
        } catch (e: Exception) {
            Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun navigationChange(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}