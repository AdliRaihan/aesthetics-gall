package com.tibz.lpsimulation.activities.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.tibz.lpsimulation.activities.base.BaseCollectionViewController
import com.tibz.lpsimulation.activities.list.dataClass.ShopListDataObject
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetProtocol
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetView
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftCollectionViewProtocol
import com.tibz.lpsimulation.common.extension.currencyFormat
import com.tibz.lpsimulation.common.extension.linearConstruct
import com.tibz.lpsimulation.databinding.BottomSheetConfirmationBinding
import com.tibz.lpsimulation.databinding.CellItemListBinding

class ShopListView: BaseCollectionViewController(), SwiftCollectionViewProtocol,
    BottomSheetProtocol {

    companion object {
        var data: ShopListDataObject.Root? = null
    }
    private var bottomSheet: BottomSheetView? = null
    private lateinit var bottomSheetContent: BottomSheetConfirmationBinding
    override fun parentFinished() {
        bottomSheetContent = BottomSheetConfirmationBinding.inflate(layoutInflater)
        bottomSheet = BottomSheetView.create(this, bottomSheetContent, this)
        parentViewBinding.baseRecyclerView.linearConstruct(this,this, data?.items?.size ?: 0)
    }

    override fun registerCell(parent: ViewGroup): ViewBinding {
        return CellItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onItemShowing(row: Int, item: ViewBinding) {
        data?.items?.let {
            if (it.size < row) return
            val itemData = it[row]
            (item as? CellItemListBinding)?.apply {
                this.itemName.text = itemData.name
                this.itemDescription.text = itemData.description
                this.itemPrice.text = itemData.price.toString().currencyFormat() + ".00"
            }
        }
    }

    override fun onItemSelected(row: Int, identifier: String) {
        bottomSheet?.dialog?.show()
    }
}