package com.flatcode.simpleadvancedapps.countries.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.utils.downloadFromUrl
import com.flatcode.simpleadvancedapps.utils.placeholderProgressBar
import com.flatcode.simpleadvancedapps.countries.viewModel.DetailViewModel
import com.flatcode.simpleadvancedapps.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel
    private var countryUuid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = DetailFragmentArgs.fromBundle(it).countryId
        }

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.getDataFromRoom(countryUuid)

        binding.toolbar.nameSpace.text = DATA.COUNTRY_DETAILS

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner) { country ->
            country?.let {
                with(binding) {
                    cName.text = country.countryName
                    capName.text = country.countryCapital
                    regionName.text = country.countryRegion
                    langName.text = country.countryLanguage
                    currencyName.text = country.countryCurrency

                    detailImg.downloadFromUrl(
                        false, country.imageURL,
                        placeholderProgressBar(requireContext())
                    )
                    detailImgBlur.downloadFromUrl(
                        true, country.imageURL,
                        placeholderProgressBar(requireContext())
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}