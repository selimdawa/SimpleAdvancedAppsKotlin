package com.flatcode.simpleadvancedapps.crypto.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.crypto.base.BaseFragment
import com.flatcode.simpleadvancedapps.crypto.model.home.Data
import com.flatcode.simpleadvancedapps.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {

    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData(DATA.API_KEY_CRYPTO, DATA.LIMIT_CRYPTO)
    }

    override fun onCreateFinished() {
        binding.toolbar.nameSpace.text = DATA.CRYPTO
    }

    override fun initializeListeners() {
    }

    override fun observeEvents() {
        with(viewModel) {
            cryptoResponse.observe(viewLifecycleOwner) {
                it?.let {
                    it.data?.let { it1 -> setRecycler(it1) }
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                handleViews(it)
            }
            onError.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setRecycler(data: List<Data>) {
        val mAdapter = HomeRecyclerAdapter(object : ItemClickListener {
            override fun onItemClick(coin: Data) {
                if (coin.symbol != null) {
                    val navigation =
                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(coin.symbol)
                    Navigation.findNavController(requireView()).navigate(navigation)
                }
            }
        })
        binding.rvHome.adapter = mAdapter
        mAdapter.setList(data)
    }

    private fun handleViews(isLoading: Boolean = false) {
        binding.rvHome.isVisible = !isLoading
        binding.pbHome.isVisible = isLoading
    }
}