package com.flatcode.simpleadvancedapps.meals.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentCategoriesMealsBinding
import com.flatcode.simpleadvancedapps.meals.activities.CategoryMealsActivity
import com.flatcode.simpleadvancedapps.meals.adapters.CategoriesAdapter
import com.flatcode.simpleadvancedapps.meals.mvvm.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesMealsBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoriesAdapter: CategoriesAdapter

    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_graph_meals)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoriesMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategories()
        onCategoryClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.submitList(categories)
        }
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.adapter = categoriesAdapter
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(requireContext(), CategoryMealsActivity::class.java).apply {
                putExtra(HomeFragment.CATEGORY_NAME, category.strCategory)
            }
            startActivity(intent)
        }
    }
}