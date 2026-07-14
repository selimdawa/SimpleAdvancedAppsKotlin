package com.flatcode.simpleadvancedapps.pokemon.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentListPokeBinding
import com.flatcode.simpleadvancedapps.pokemon.ui.view.adapters.ItemAdapter
import com.flatcode.simpleadvancedapps.pokemon.ui.viewmodel.ApiStatus
import com.flatcode.simpleadvancedapps.pokemon.ui.viewmodel.PokeViewModel
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListPokeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PokeViewModel by hiltNavGraphViewModels(R.id.nav_graph_pokemon)
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListPokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.nameSpace.text = DATA.POKE

        adapter = ItemAdapter()
        binding.recyclerViewPoke.adapter = adapter

        observeApiStatus()
        observeListPokemon()
        onClickItem()
    }

    private fun observeApiStatus() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ApiStatus.LOADING -> {
                    binding.statusOffline.visibility = View.GONE
                    binding.shimmerLoading.visibility = View.VISIBLE
                    binding.recyclerViewPoke.visibility = View.GONE
                }

                ApiStatus.ERROR -> {
                    binding.statusOffline.visibility = View.VISIBLE
                    binding.shimmerLoading.visibility = View.GONE
                    binding.recyclerViewPoke.visibility = View.GONE
                }

                ApiStatus.DONE -> {
                    binding.statusOffline.visibility = View.GONE
                    binding.shimmerLoading.visibility = View.GONE
                    binding.recyclerViewPoke.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeListPokemon() {
        viewModel.pokemonList.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    private fun onClickItem() {
        adapter.onItemClickListener = { poke ->
            val bundle = Bundle().apply { putInt("id", poke.id) }
            findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}