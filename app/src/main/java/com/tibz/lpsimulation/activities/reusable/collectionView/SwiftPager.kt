package com.tibz.lpsimulation.activities.reusable.collectionView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SwiftPager(
    fragment: FragmentActivity,
    private var viewBindings: Array<Fragment>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return viewBindings.size
    }
    override fun createFragment(position: Int): Fragment {
        return viewBindings[position]
    }
}