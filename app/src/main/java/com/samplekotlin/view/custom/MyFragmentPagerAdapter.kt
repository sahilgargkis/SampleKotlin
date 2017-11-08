package com.samplekotlin.view.custom

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

class MyFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var currentFragment: Fragment? = null
        private set

    override fun getItem(position: Int): Fragment? {
        return null
    }

    override fun getCount(): Int {
        return 0
    }

    //...
    override fun setPrimaryItem(container: ViewGroup?, position: Int, `object`: Any) {
        if (currentFragment !== `object`) {
            currentFragment = `object` as Fragment
        }
        super.setPrimaryItem(container, position, `object`)
    }
}