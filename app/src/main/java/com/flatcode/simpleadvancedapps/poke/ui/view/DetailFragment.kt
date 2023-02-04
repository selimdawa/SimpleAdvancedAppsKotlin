package com.flatcode.simpleadvancedapps.poke.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.databinding.FragmentDetailPokeBinding
import com.flatcode.simpleadvancedapps.poke.ui.viewmodel.ApiStatusDetail
import com.flatcode.simpleadvancedapps.poke.ui.viewmodel.DetailsViewModel
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailPokeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idP = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailPokeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.nameSpace.text = DATA.Details_Poke

        observeStatus()
        observe()
    }

    private fun observe() {
        viewModel.pokeDetails.observe(viewLifecycleOwner) { pokemon ->

            if (pokemon.types.size > 1) {
                binding.tvType1.text = pokemon.types[0]
                binding.tvType1
                binding.tvType2.text = pokemon.types[1]
                binding.tvType2.visibility = View.VISIBLE
            } else {
                binding.tvType1.text = pokemon.types[0]
                binding.tvType2.visibility = View.GONE
            }

            Picasso.get().load(pokemon.img)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(binding.image)

            binding.collapsingToolbar.title = pokemon.name
            binding.tvHp.text = pokemon.hp.toString()
            binding.speed.text = pokemon.speed.toString()
            binding.attack.text = pokemon.attack.toString()
            binding.defense.text = pokemon.defense.toString()
            binding.specialAttack.text = pokemon.specialAttack.toString()
            binding.specialDefense.text = pokemon.specialDefense.toString()
            binding.height.text = getString(R.string.metro, pokemon.height.toString())
            binding.weight.text = getString(R.string.kilo, pokemon.weight.toString())
        }
    }

    private fun observeStatus() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ApiStatusDetail.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.appBar.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.GONE
                    binding.statusOffline.visibility = View.GONE
                }
                ApiStatusDetail.DONE -> {
                    binding.progressBar.visibility = View.GONE
                    binding.appBar.visibility = View.VISIBLE
                    binding.nestedScrollView.visibility = View.VISIBLE
                    binding.statusOffline.visibility = View.GONE
                }
                ApiStatusDetail.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.appBar.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.GONE
                    binding.statusOffline.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var idP = 0
    }
}