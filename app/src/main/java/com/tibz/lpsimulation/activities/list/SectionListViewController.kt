package com.tibz.lpsimulation.activities.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.tibz.lpsimulation.activities.base.BaseCollectionViewController
import com.tibz.lpsimulation.activities.list.dataClass.SectionDataObject
import com.tibz.lpsimulation.activities.list.sectionRouter.SectionRouter
import com.tibz.lpsimulation.activities.list.sectionRouter.SectionRouter.Companion.router
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetProtocol
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetView
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftCollectionViewProtocol
import com.tibz.lpsimulation.common.extension.linearConstruct
import com.tibz.lpsimulation.databinding.BottomSheetConfirmationBinding
import com.tibz.lpsimulation.databinding.CellSectionListBinding

class SectionListViewController: BaseCollectionViewController(), SwiftCollectionViewProtocol,
    BottomSheetProtocol {

    companion object {
        var data: SectionDataObject.Root? = null
    }

    private var bottomSheet: BottomSheetView? = null
    private lateinit var bottomSheetContent: BottomSheetConfirmationBinding
    override fun parentFinished() {
        bottomSheetContent = BottomSheetConfirmationBinding.inflate(layoutInflater)
        bottomSheet = BottomSheetView.create(this, bottomSheetContent, this)
        parentViewBinding.baseRecyclerView.linearConstruct(this,this, data?.sections?.size ?: 0)
    }

    override fun registerCell(parent: ViewGroup): ViewBinding {
        return CellSectionListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onItemShowing(row: Int, item: ViewBinding) {
        data?.sections?.let {
            if (it.size < row) return
            val itemData = it[row]
            (item as? CellSectionListBinding)?.let {
                item.placeNameLabel.text = itemData.name
                item.placeDescriptionLabel.text = itemData.description
                item.placeDistanceLabel.text = "${itemData.distance.toString()} ${data?.distanceMeter ?: ""}"
            }
        }
    }

    override fun onItemSelected(row: Int, identifier: String) {
        data?.sections?.let {
            if (it.size < row) return
            val itemData = it[row]
            stringTitle = itemData.name
            SectionRouter.router(this, itemData.name)
        }
    }
}