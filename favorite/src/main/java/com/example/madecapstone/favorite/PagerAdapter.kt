package com.example.madecapstone.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.madecapstone.favorite.favorite.FavoriteMovieFragment

class PagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(
        R.string.movie,
        R.string.tvshow
    )

    private val fragment: List<Fragment> = listOf(
        FavoriteMovieFragment(isMovie = true),
        FavoriteMovieFragment(isMovie = false),
    )

    override fun getPageTitle(position: Int): CharSequence {
        return mContext.getString(tabTitles[position])
    }

    override fun getCount() = tabTitles.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }
}