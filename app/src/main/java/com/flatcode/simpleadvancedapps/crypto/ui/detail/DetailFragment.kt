package com.flatcode.simpleadvancedapps.crypto.ui.detail

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.crypto.base.BaseFragment
import com.flatcode.simpleadvancedapps.crypto.model.detail.CoinDetail
import com.flatcode.simpleadvancedapps.crypto.model.detail.DetailResponse
import com.flatcode.simpleadvancedapps.crypto.utils.loadImage
import com.flatcode.simpleadvancedapps.databinding.FragmentDetailCryptoBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment :
    BaseFragment<FragmentDetailCryptoBinding, DetailViewModel>(FragmentDetailCryptoBinding::inflate) {

    @Inject
    lateinit var gson: Gson

    override val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateFinished() {
        viewModel.getDetail(DATA.API_KEY_CRYPTO, args.symbol)
        binding.toolbar.nameSpace.text = DATA.Crypto_details
    }

    override fun initializeListeners() {}

    override fun observeEvents() {
        with(viewModel) {
            detailResponse.observe(viewLifecycleOwner) { response ->
                parseData(response)
            }
            isLoading.observe(viewLifecycleOwner) { loading ->
                handleView(loading)
            }
            onError.observe(viewLifecycleOwner) { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun parseData(response: DetailResponse?) {
        try {
            val json = gson.toJson(response?.data)
            val jsonObject = JSONObject(json)
            val jsonArray = jsonObject.optJSONArray(args.symbol) ?: JSONArray()
            val firstObject = jsonArray.optJSONObject(0)?.toString()

            if (!firstObject.isNullOrEmpty()) {
                val coin = gson.fromJson(firstObject, CoinDetail::class.java)
                coin?.let {
                    with(binding) {
                        ivDetail.loadImage("${DATA.IMAGE_CRYPTO}${args.coinId}.png")
                        tvDetailTitle.text = it.name
                        tvDetailSymbol.text = it.symbol
                        tvDetailDescription.text = it.description
                    }
                }
            }
        } catch (_: Exception) {
        }
    }

    private fun handleView(isLoading: Boolean) {
        with(binding) {
            detailGroup.isVisible = !isLoading
            pbDetail.isVisible = isLoading
        }
    }
}