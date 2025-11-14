package com.flatcode.simpleadvancedapps.crypto.ui.detail

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.crypto.base.BaseFragment
import com.flatcode.simpleadvancedapps.crypto.model.detail.CoinDetail
import com.flatcode.simpleadvancedapps.crypto.model.detail.DetailResponse
import com.flatcode.simpleadvancedapps.crypto.utils.loadImage
import com.flatcode.simpleadvancedapps.databinding.FragmentDetailCryptoBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject

@AndroidEntryPoint
class DetailFragment :
    BaseFragment<FragmentDetailCryptoBinding, DetailViewModel>(FragmentDetailCryptoBinding::inflate) {

    override val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateFinished() {
        viewModel.getDetail(DATA.API_KEY_CRYPTO, args.symbol)
        binding.toolbar.nameSpace.text = DATA.Crypto_details
    }

    override fun initializeListeners() {}

    override fun observeEvents() {
        with(viewModel) {
            detailResponse.observe(viewLifecycleOwner) {
                parseData(it)
            }
            isLoading.observe(viewLifecycleOwner) {
                handleView(it)
            }
            onError.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun parseData(it: DetailResponse?) {
        val gson = Gson()
        val json = gson.toJson(it?.data)
        val jsonObject = JSONObject(json)
        val jsonArray = jsonObject[args.symbol] as JSONArray

        val coin = gson.fromJson(jsonArray.getJSONObject(0).toString(), CoinDetail::class.java)

        coin?.let {
            with(binding) {
                ivDetail.loadImage(it.logo)
                tvDetailTitle.text = it.name
                tvDetailSymbol.text = it.symbol
                tvDetailDescription.text = it.description
            }
        }
    }

    private fun handleView(isLoading: Boolean = false) {
        binding.detailGroup.isVisible = !isLoading
        binding.pbDetail.isVisible = isLoading
    }
}