package com.tibz.lpsimulation.activities.reusable.collectionView

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

class SwiftCollectionView <T>
    (
    private val section: MutableList<T>,
    private val delegate: SwiftCollectionViewProtocol,
    private val identifier: String = "",
    ): RecyclerView.Adapter<ViewHolder>() {

    private lateinit var vbView: ViewBinding

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        vbView = delegate.registerCell(parent)
        return ViewHolder(vbView.root)
    }

    override fun getItemCount(): Int {
        return section.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegate.onItemShowing(position, vbView)
        vbView.root.setOnClickListener {
            delegate.onItemSelected(position, identifier)
        }
    }
}