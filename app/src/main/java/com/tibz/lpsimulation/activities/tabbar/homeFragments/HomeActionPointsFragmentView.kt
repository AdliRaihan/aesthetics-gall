package com.tibz.lpsimulation.activities.tabbar.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tibz.lpsimulation.databinding.HomeFragmentsActionsPointsBinding

class HomeActionPointsFragmentView(private val parent: AppCompatActivity): Fragment() {
    private lateinit var viewBinding: HomeFragmentsActionsPointsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = HomeFragmentsActionsPointsBinding.inflate(layoutInflater)
        return viewBinding.root
    }
}