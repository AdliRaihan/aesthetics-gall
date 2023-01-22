package com.tibz.lpsimulation.activities.reusable.collectionView

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class CollectionViewImp <T> (
    private val sum: MutableList<T>,
    private val delegate: CollectionViewDataSource,
    private val identifier: String = "",
): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private lateinit var vbView: ViewBinding

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        vbView = delegate.registerCell(identifier, parent)
        return ViewHolder(vbView.root)
    }

    override fun getItemCount(): Int {
        return sum.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegate.onItemShowing(identifier, position, vbView)
        vbView.root.setOnClickListener {
            delegate.onItemSelected(identifier, position, identifier)
        }
    }
}