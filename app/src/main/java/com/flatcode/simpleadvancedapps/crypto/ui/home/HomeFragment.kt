package com.flatcode.simpleadvancedapps.crypto.ui.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.crypto.base.BaseFragment
import com.flatcode.simpleadvancedapps.crypto.model.home.Data
import com.flatcode.simpleadvancedapps.crypto.utils.DataStoreManager
import com.flatcode.simpleadvancedapps.crypto.utils.toast
import com.flatcode.simpleadvancedapps.databinding.FragmentHomeBinding
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_graph_crypto)
    private lateinit var mAdapter: HomeRecyclerAdapter
    private var isCurrentlyLoadingMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData(DATA.API_KEY_CRYPTO, DATA.LIMIT_CRYPTO)
    }

    override fun onCreateFinished() {
        binding.toolbar.nameSpace.text = DATA.CRYPTO
        setupRecyclerView()
    }

    override fun initializeListeners() {}

    override fun observeEvents() {
        collectLifecycleFlow(viewModel.cryptoList) { dataList ->
            mAdapter.setList(dataList)
            isCurrentlyLoadingMore = false
        }

        collectLifecycleFlow(viewModel.isLoading) { loading ->
            handleViews(loading)
        }

        collectLifecycleFlow(viewModel.error) { message ->
            toast(message)
            if (message != null) isCurrentlyLoadingMore = false
        }
    }

    private fun setupRecyclerView() {
        mAdapter = HomeRecyclerAdapter(object : ItemClickListener {
            override fun onItemClick(
                coin: Data,
                ivRowImage: ImageView,
                tvRowTitle: TextView,
                tvRowSymbol: TextView,
            ) {
                val symbol = coin.symbol
                val id = coin.id
                if (!symbol.isNullOrEmpty() && (id != null)) {
                    lifecycleScope.launch {
                        dataStoreManager.saveLastNav(id, symbol)
                    }
                    val navigation = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        symbol = symbol,
                        coinId = id,
                    )
                    findNavController().navigate(navigation)
                }
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        with(binding.rvHome) {
            this.layoutManager = layoutManager
            this.adapter = mAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount
                        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                        if ((!isCurrentlyLoadingMore) && (visibleItemCount + firstVisibleItemPosition >= totalItemCount)) {
                            isCurrentlyLoadingMore = true
                            viewModel.loadNextPage(DATA.API_KEY_CRYPTO)
                        }
                    }
                }
            })
        }
    }

    private fun handleViews(isLoading: Boolean) {
        if (viewModel.isFirstPage()) {
            binding.rvHome.isVisible = !isLoading
            binding.pbHome.isVisible = isLoading
        } else {
            binding.pbHome.isVisible = false
        }
    }
}