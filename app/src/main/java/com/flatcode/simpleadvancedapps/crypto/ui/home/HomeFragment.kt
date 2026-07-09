package com.flatcode.simpleadvancedapps.crypto.ui.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.crypto.base.BaseFragment
import com.flatcode.simpleadvancedapps.crypto.model.home.Data
import com.flatcode.simpleadvancedapps.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {

    override val viewModel by viewModels<HomeViewModel>()
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
        with(viewModel) {
            cryptoList.observe(viewLifecycleOwner) { dataList ->
                dataList?.let {
                    mAdapter.setList(it)
                    isCurrentlyLoadingMore = false
                }
            }
            isLoading.observe(viewLifecycleOwner) { loading ->
                handleViews(loading)
            }
            onError.observe(viewLifecycleOwner) { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                isCurrentlyLoadingMore = false
            }
        }
    }

    private fun setupRecyclerView() {
        mAdapter = HomeRecyclerAdapter(object : ItemClickListener {
            override fun onItemClick(
                coin: Data, ivRowImage: ImageView, tvRowTitle: TextView, tvRowSymbol: TextView
            ) {
                val symbol = coin.symbol
                val id = coin.id
                if (!symbol.isNullOrEmpty() && id != null) {
                    val navigation = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        symbol = symbol,
                        coinId = id
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

                        if (!isCurrentlyLoadingMore && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                            isCurrentlyLoadingMore = true
                            viewModel.loadNextPage(DATA.API_KEY_CRYPTO)
                        }
                    }
                }
            })
        }
    }

    private fun handleViews(isLoading: Boolean) {
        with(binding) {
            if (viewModel.isFirstPage()) {
                rvHome.isVisible = !isLoading
                pbHome.isVisible = isLoading
            } else {
                pbHome.isVisible = false
            }
        }
    }
}