package com.open.weibo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.open.core_base.adapter.CommonFragmentPagerAdapter
import com.open.weibo.main.fragment.HomelineFragment
import com.open.weibo.main.fragment.ProfileFragment

class HomeFragmentAdapter(fragmentManager: FragmentManager) :
    CommonFragmentPagerAdapter(fragmentManager) {
    private val fragmentList: List<Fragment> = listOf(HomelineFragment(), ProfileFragment())
    override fun getCount(): Int = fragmentList.size

    override fun getItem(position: Int): Fragment = fragmentList[position]

}