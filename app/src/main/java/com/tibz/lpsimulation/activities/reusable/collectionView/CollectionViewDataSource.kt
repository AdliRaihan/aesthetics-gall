package com.tibz.lpsimulation.activities.reusable.collectionView

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

interface CollectionViewDataSource {

    // Item Index Get Showed On Screen
    fun onItemShowing(id: String, row: Int, item: ViewBinding)

    // Similar to register cell in swift
    fun registerCell(id: String, parent: ViewGroup): ViewBinding

    // When Item Get Selected
    fun onItemSelected(id: String, row: Int, identifier: String)
}