package com.tibz.lpsimulation.activities.reusable.views

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.tibz.lpsimulation.databinding.CompImageDockerBinding
import java.lang.ref.WeakReference

class LPImageDockerView<T>(
    private val delegate: WeakReference<LPImageDockerViewInit>?,
    private val uris: MutableList<T>
): BaseAdapter() {
    interface LPImageDockerViewInit {
        fun createView(viewGroup: ViewGroup, position: Int): View
    }
    override fun getCount(): Int {
        return uris.size - 1
    }
    override fun getItem(p0: Int): Any {
        return uris
    }
    override fun getItemId(p0: Int): Long {
        return 0
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View? {
        if (p2 == null) return null
        return delegate?.get()?.createView(p2, position)
    }
}