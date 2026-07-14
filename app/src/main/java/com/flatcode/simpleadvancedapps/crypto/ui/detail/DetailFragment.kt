package com.flatcode.simpleadvancedapps.crypto.ui.detail

import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.crypto.base.BaseFragment
import com.flatcode.simpleadvancedapps.crypto.model.detail.DetailResponse
import com.flatcode.simpleadvancedapps.crypto.utils.NetworkResult
import com.flatcode.simpleadvancedapps.crypto.utils.loadImage
import com.flatcode.simpleadvancedapps.crypto.utils.toast
import com.flatcode.simpleadvancedapps.databinding.FragmentDetailCryptoBinding
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment :
    BaseFragment<FragmentDetailCryptoBinding, DetailViewModel>(FragmentDetailCryptoBinding::inflate) {

    private val args: DetailFragmentArgs by navArgs()
    override val viewModel: DetailViewModel by hiltNavGraphViewModels(R.id.nav_graph_crypto)

    override fun onCreateFinished() {
        binding.toolbar.nameSpace.text = DATA.CRYPTO_DETAILS
        viewModel.getDetail(args.symbol, args.coinId)
    }

    override fun initializeListeners() {}

    override fun observeEvents() {
        collectLifecycleFlow(viewModel.detailState) { state ->
            when (state) {
                is NetworkResult.Loading -> handleView(isLoading = true)
                is NetworkResult.Success -> {
                    handleView(isLoading = false)
                    parseData(state.data)
                }

                is NetworkResult.Error -> {
                    handleView(isLoading = false)
                    toast(state.message)
                }
            }
        }
    }

    private fun parseData(response: DetailResponse?) {
        val coin = response?.data?.get(viewModel.symbol)?.firstOrNull()
        coin?.let {
            with(binding) {
                ivDetail.loadImage("${DATA.IMAGE_CRYPTO}${viewModel.coinId}.png")
                tvDetailTitle.text = it.name
                tvDetailSymbol.text = it.symbol
                tvDetailDescription.text = it.description
            }
        }
    }

    private fun handleView(isLoading: Boolean) {
        binding.detailGroup.isVisible = !isLoading
        binding.pbDetail.isVisible = isLoading
    }
}