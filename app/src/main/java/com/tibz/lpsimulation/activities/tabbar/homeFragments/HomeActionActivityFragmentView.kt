package com.tibz.lpsimulation.activities.tabbar.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tibz.lpsimulation.activities.base.BaseCollectionViewController
import com.tibz.lpsimulation.activities.list.JobListViewController
import com.tibz.lpsimulation.activities.list.SectionListViewController
import com.tibz.lpsimulation.activities.list.dataClass.JobListDataObject
import com.tibz.lpsimulation.activities.list.dataClass.SectionDataObject
import com.tibz.lpsimulation.common.extension.navigation
import com.tibz.lpsimulation.common.helper.LPConstants
import com.tibz.lpsimulation.databinding.HomeFragmentsActionsActivitiesBinding

class HomeActionActivityFragmentView(private val parent: AppCompatActivity): Fragment() {
    private lateinit var viewBinding: HomeFragmentsActionsActivitiesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = HomeFragmentsActionsActivitiesBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.menu1.setOnClickListener {
            BaseCollectionViewController.stringTitle = LPConstants.NavigationName.headerActivity
            SectionDataObject.getSectionShop(LPConstants.FileID.activityJSON, parent) { value ->
                SectionListViewController.data = value
                parent.navigation(SectionListViewController())
            }
        }
        viewBinding.menu2.setOnClickListener {
            BaseCollectionViewController.stringTitle = LPConstants.NavigationName.headerActivity
            SectionDataObject.getSectionShop(LPConstants.FileID.activityJSON, parent) { value ->
                SectionListViewController.data = value
                parent.navigation(SectionListViewController())
            }
        }
        viewBinding.menu3.setOnClickListener {
            BaseCollectionViewController.stringTitle = LPConstants.NavigationName.headerJobs
            JobListDataObject.getData(parent) {
                JobListViewController.data = it
                parent.navigation(JobListViewController())
            }
        }
        viewBinding.menu4.setOnClickListener {
            BaseCollectionViewController.stringTitle = LPConstants.NavigationName.headerTravel
            SectionDataObject.getSectionShop(LPConstants.FileID.travelJSON, parent) { value ->
                SectionListViewController.data = value
                parent.navigation(SectionListViewController())
            }
        }
        viewBinding.menu5.setOnClickListener {
            BaseCollectionViewController.stringTitle = LPConstants.NavigationName.headerActivity
            SectionDataObject.getSectionShop(LPConstants.FileID.activityJSON, parent) { value ->
                SectionListViewController.data = value
                parent.navigation(SectionListViewController())
            }
        }
    }
}