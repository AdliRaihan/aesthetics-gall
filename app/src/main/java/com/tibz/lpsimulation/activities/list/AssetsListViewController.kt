package com.tibz.lpsimulation.activities.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.tibz.lpsimulation.activities.base.BaseCollectionViewController
import com.tibz.lpsimulation.activities.list.dataClass.SectionDataObject
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetProtocol
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetView
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftCollectionView
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftCollectionViewProtocol
import com.tibz.lpsimulation.databinding.BottomSheetConfirmationBinding
import com.tibz.lpsimulation.databinding.CellJobListBinding
import com.tibz.lpsimulation.databinding.CellOwnedAssetsBinding

class AssetsListViewController: BaseCollectionViewController(), SwiftCollectionViewProtocol,
    BottomSheetProtocol {

    companion object {
        var data: Array<SectionDataObject> = arrayOf()
    }

    private var bottomSheet: BottomSheetView? = null
    private lateinit var bottomSheetContent: BottomSheetConfirmationBinding
    override fun parentFinished() {
        bottomSheetContent = BottomSheetConfirmationBinding.inflate(layoutInflater)
        bottomSheet = BottomSheetView.create(this, bottomSheetContent, this)
        parentViewBinding.baseRecyclerView.layoutManager = LinearLayoutManager(this)
//        parentViewBinding.baseRecyclerView.adapter = SwiftCollectionView(5, this)
    }

    override fun registerCell(parent: ViewGroup): ViewBinding {
        return CellOwnedAssetsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun onItemShowing(row: Int, item: ViewBinding) {
    }

    override fun onItemSelected(row: Int, identifier: String) {
        bottomSheet?.dialog?.show()
    }
}