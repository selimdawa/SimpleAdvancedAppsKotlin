package com.flatcode.simpleadvancedapps.countries.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.countries.Util.downloadFromUrl
import com.flatcode.simpleadvancedapps.countries.Util.placeholderProgressBar
import com.flatcode.simpleadvancedapps.countries.ViewModel.DetailViewModel
import com.flatcode.simpleadvancedapps.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null
    private lateinit var viewModel: DetailViewModel
    private var countryUuid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(LayoutInflater.from(context), container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = DetailFragmentArgs.fromBundle(it).countryId
        }

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.getDataFromRoom(countryUuid)

        binding!!.toolbar.nameSpace.text = DATA.Country_details

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner) { country ->
            country?.let {
                binding!!.cName.text = country.countryName
                binding!!.capName.text = country.countryCapital
                binding!!.regionName.text = country.countryRegion
                binding!!.langName.text = country.countryLanguage
                binding!!.currencyName.text = country.countryCurrency
                binding!!.detailImg.downloadFromUrl(
                    false, country.imageURL,
                    placeholderProgressBar(requireContext())
                )
                binding!!.detailImgBlur.downloadFromUrl(
                    true, country.imageURL,
                    placeholderProgressBar(requireContext())
                )
            }
        }
    }
}