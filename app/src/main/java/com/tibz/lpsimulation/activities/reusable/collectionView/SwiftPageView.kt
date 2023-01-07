package com.tibz.lpsimulation.activities.reusable.collectionView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.tibz.lpsimulation.R

class SwiftPageView(
    private var injectedLayout: ViewBinding
): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return injectedLayout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}