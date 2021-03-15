package com.open.weibo.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.open.core_base.adapter.CommonFragmentPagerAdapter
import com.open.weibo.main.fragment.HomelineFragment
import com.open.weibo.main.fragment.ProfileFragment

class HomeFragmentAdapter(activity:FragmentActivity) :
    CommonFragmentPagerAdapter(activity) {

    private val fragmentList: List<Fragment> = listOf(HomelineFragment(), ProfileFragment())

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

}