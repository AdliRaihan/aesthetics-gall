package com.tibz.lpsimulation.activities.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.tibz.lpsimulation.activities.base.BaseCollectionViewController
import com.tibz.lpsimulation.activities.list.dataClass.JobListDataObject
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetProtocol
import com.tibz.lpsimulation.activities.reusable.bottomsheets.BottomSheetView
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftCollectionView
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftCollectionViewProtocol
import com.tibz.lpsimulation.common.extension.currencyFormat
import com.tibz.lpsimulation.common.extension.linearConstruct
import com.tibz.lpsimulation.common.extension.randomizerByRange
import com.tibz.lpsimulation.databinding.BottomSheetConfirmationBinding
import com.tibz.lpsimulation.databinding.CellJobListBinding

class JobListViewController: BaseCollectionViewController(), SwiftCollectionViewProtocol,
    BottomSheetProtocol {

    companion object {
        var data: JobListDataObject.Root? = null
    }

    private var bottomSheet: BottomSheetView? = null
    private lateinit var bottomSheetContent: BottomSheetConfirmationBinding

    @SuppressLint("SetTextI18n")
    override fun parentFinished() {
        bottomSheetContent = BottomSheetConfirmationBinding.inflate(layoutInflater)
        bottomSheet = BottomSheetView.create(
            this,
            bottomSheetContent,
            this)
        data?.jobs?.let {
            parentViewBinding
                .baseRecyclerView
                .layoutManager = LinearLayoutManager(
                this)
            parentViewBinding
                .baseRecyclerView
                .adapter = SwiftCollectionView(it.toMutableList(), this)
        }
    }

    override fun registerCell(parent: ViewGroup): ViewBinding {
        return CellJobListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onItemShowing(row: Int, item: ViewBinding) {
        parentViewBinding.errMessageInfo.visibility = View.GONE
        data?.jobs?.let {
            if (it.size < row) return
            val itemData = it[row]
            (item as? CellJobListBinding)?.also {
                val salary = randomizerByRange(itemData.level.salaryRange.from, itemData.level.salaryRange.to)
                item.jobPositionLabel.text = itemData.position
                item.jobSalaryLabel.text = salary.toString().currencyFormat() + ".00 / ${itemData.type}"

                if (itemData.level.positionLevel.isEmpty()) item.jobPositionLevelLabel.visibility = View.GONE
                item.jobPositionLevelLabel.text = "${itemData.level.positionLevel} (${itemData.level.experience})"

                if (itemData.skills.isEmpty()) item.skillContainer.visibility = View.GONE
                item.jobSkillRequirementLabel.text = itemData.skills.joinToString { value -> value }
                item.jobRequirementLabel.text = itemData.educationLevel.joinToString { value -> value }
            }
        }
    }

    override fun onItemSelected(row: Int, identifier: String) {
        bottomSheet?.dialog?.show()
    }
}