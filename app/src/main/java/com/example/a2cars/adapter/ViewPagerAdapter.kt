package com.example.a2cars.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.a2cars.adapter.ViewPagerAdapter.Companion.TAB_TITLES

class ViewPagerAdapter(val context: Context,
                        fm:FragmentManager,
                       val list:ArrayList<Fragment>
): FragmentPagerAdapter(fm!!) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    companion object {
        val TAB_TITLES=arrayOf("Chat","Status","Call")
    }
}