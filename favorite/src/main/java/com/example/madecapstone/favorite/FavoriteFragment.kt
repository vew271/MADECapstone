package com.example.madecapstone.favorite

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.madecapstone.favorite.databinding.FragmentFavoriteBinding
import com.example.madecapstone.favorite.di.favoriteModule
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {
    private var fragmentFavoriteBinding: FragmentFavoriteBinding? = null
    private val binding get() = fragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        val sectionsPagerAdapter = PagerAdapter(context as Context, childFragmentManager)
        binding?.viewPager?.adapter = sectionsPagerAdapter
        binding?.tab?.setupWithViewPager(binding?.viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentFavoriteBinding = null
        binding?.viewPager?.adapter = null
    }
}