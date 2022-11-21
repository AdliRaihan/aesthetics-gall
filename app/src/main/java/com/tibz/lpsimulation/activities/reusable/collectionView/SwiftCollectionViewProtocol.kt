package com.tibz.lpsimulation.activities.reusable.collectionView

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

interface SwiftCollectionViewProtocol {

    // Item Index Get Showed On Screen
    fun onItemShowing(row: Int, item: ViewBinding)

    // Similar to register cell in swift
    fun registerCell(parent: ViewGroup): ViewBinding

    // When Item Get Selected
    fun onItemSelected(row: Int, identifier: String)
}