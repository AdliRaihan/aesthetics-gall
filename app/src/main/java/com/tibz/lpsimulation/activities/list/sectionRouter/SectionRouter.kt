package com.tibz.lpsimulation.activities.list.sectionRouter

import androidx.appcompat.app.AppCompatActivity
import com.tibz.lpsimulation.activities.list.ShopListView
import com.tibz.lpsimulation.activities.list.dataClass.ShopListDataObject
import com.tibz.lpsimulation.common.extension.navigation
import com.tibz.lpsimulation.common.helper.LPConstants

class SectionRouter {
    companion object {
        fun router(parent: AppCompatActivity, name: String) {
            when (name.lowercase()) {
                LPConstants.RouterName.carDealership -> {
                    parent.navigation(ShopListView())
                }
                LPConstants.RouterName.phoneDealership -> {
                    parent.navigation(ShopListView())
                }
                LPConstants.RouterName.supermarket -> {
                    ShopListDataObject.getItemLists("ShopSupermarket", parent) {
                        ShopListView.data = it
                        parent.navigation(ShopListView())
                    }
                }
                else -> {

                }
            }
        }
    }
}