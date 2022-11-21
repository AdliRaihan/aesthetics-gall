package com.tibz.lpsimulation.common.extension

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftCollectionView
import com.tibz.lpsimulation.activities.reusable.collectionView.SwiftCollectionViewProtocol

fun RecyclerView.linearConstruct(context: Context, delegate: SwiftCollectionViewProtocol, section: Int) {
    this.layoutManager = LinearLayoutManager(context)
    this.adapter = SwiftCollectionView(section, delegate)
}