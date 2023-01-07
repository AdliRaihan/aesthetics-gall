package com.tibz.lpsimulation.activities.tabbar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tibz.lpsimulation.activities.tabbar.homeFragments.HomeActionActivityFragmentView
import com.tibz.lpsimulation.activities.tabbar.homeFragments.HomePropertyFragmentView
import com.tibz.lpsimulation.activities.tabbar.homeFragments.HomeWalletFragmentView
import com.tibz.lpsimulation.common.extension.loadImage
import com.tibz.lpsimulation.common.network.unsplash.unsplashModel.UnsplashPhotos
import com.tibz.lpsimulation.databinding.HomeFragmentsOwnedPropertyBinding
import com.tibz.lpsimulation.databinding.TabbarHomeGameViewBinding

class HomeGameView(private val parentHolder: AppCompatActivity): Fragment() {
    private lateinit var viewBinding: TabbarHomeGameViewBinding

    private var userWallet = HomeWalletFragmentView(
        parentHolder
    )

    private var userOwnedProperty = HomePropertyFragmentView(
        parentHolder
    )

    private var userActivityMenu = HomeActionActivityFragmentView(
        parentHolder
    )

    private var viewModel = HomeTabbarViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = TabbarHomeGameViewBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragments()
        loadAPI()
    }

    private fun setupFragments() {
        parentHolder
            .supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.userPlayerWallet.id, userWallet)
            .commit()

        parentHolder
            .supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.userPlayerOwnedProperty.id, userOwnedProperty)
            .commit()

        parentHolder
            .supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.userPlayerMenu.id, userActivityMenu)
            .commit()
    }

    private fun loadAPI() {
        viewModel.getDarkPhotos {
            if (it?.result?.isEmpty() == true) { return@getDarkPhotos }
            it?.result?.first().let { data ->
                this.viewBinding
                    .unsplashBannerSmall
                    .loadImage(data?.urls?.regular)
                data?.urls?.regular?.let { it1 -> Log.d("Loading image for", it1) }
                Log.d("Info", "Loading should be finished by now")
            }
        }
    }
}