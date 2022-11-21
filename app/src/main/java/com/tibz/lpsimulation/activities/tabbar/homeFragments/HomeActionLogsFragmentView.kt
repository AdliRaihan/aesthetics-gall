package com.tibz.lpsimulation.activities.tabbar.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tibz.lpsimulation.databinding.HomeFragmentsActionsLogsBinding
import com.tibz.lpsimulation.databinding.ViewActivityLogBinding

class HomeActionLogsFragmentView(private val parent: AppCompatActivity): Fragment() {
    private lateinit var viewBinding: HomeFragmentsActionsLogsBinding

    private var actionsLog: Array<ViewActivityLogBinding> = arrayOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = HomeFragmentsActionsLogsBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addLogs()
        addLogs()
        addLogs()
        addLogs()
        addLogs()
    }

    fun addLogs() {
        actionsLog += ViewActivityLogBinding.inflate(layoutInflater)
        viewBinding.verticalViewHolder.addView(actionsLog.last().root)
    }
}