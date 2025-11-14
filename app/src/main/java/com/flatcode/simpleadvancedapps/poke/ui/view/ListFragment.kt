package com.flatcode.simpleadvancedapps.poke.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.databinding.FragmentListPokeBinding
import com.flatcode.simpleadvancedapps.poke.domain.SelectedListener
import com.flatcode.simpleadvancedapps.poke.ui.view.adapters.ItemAdapter
import com.flatcode.simpleadvancedapps.poke.ui.viewmodel.ApiStatus
import com.flatcode.simpleadvancedapps.poke.ui.viewmodel.PokeViewModel


class ListFragment : Fragment() {

    private var _binding: FragmentListPokeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PokeViewModel by viewModels()
    private lateinit var adapter: ItemAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var listener: SelectedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as SelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context you must implement the listener")
        }
    }

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
        recyclerView = binding.recyclerViewPoke
        recyclerView.adapter = adapter

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
        adapter.onItemClickListener = { poke -> listener.onSelected(poke.id) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}