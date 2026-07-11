package com.flatcode.simpleadvancedapps.meals.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.FragmentHomeMealsBinding
import com.flatcode.simpleadvancedapps.meals.activities.CategoryMealsActivity
import com.flatcode.simpleadvancedapps.meals.activities.MealActivity
import com.flatcode.simpleadvancedapps.meals.adapters.CategoriesAdapter
import com.flatcode.simpleadvancedapps.meals.adapters.MostPopularAdapter
import com.flatcode.simpleadvancedapps.meals.mvvm.HomeViewModel
import com.flatcode.simpleadvancedapps.meals.pojo.Meal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeMealsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_graph_meals)

    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "idMeal"
        const val MEAL_NAME = "nameMeal"
        const val MEAL_THUMB = "thumbMeal"
        const val CATEGORY_NAME = "categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observerCategoriesLiveData()
        onCategoryClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(requireContext(), CategoryMealsActivity::class.java).apply {
                putExtra(CATEGORY_NAME, category.strCategory)
            }
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.adapter = categoriesAdapter
    }

    private fun observerCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.submitList(categories)
        }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(requireContext(), MealActivity::class.java).apply {
                putExtra(MEAL_ID, meal.idMeal)
                putExtra(MEAL_NAME, meal.strMeal)
                putExtra(MEAL_THUMB, meal.strMealThumb)
            }
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recPopular.adapter = popularItemsAdapter
    }

    private fun observePopularItemsLiveData() {
        viewModel.observerPopularItemsLiveData().observe(viewLifecycleOwner) { mealList ->
            popularItemsAdapter.submitList(mealList)
        }
    }

    private fun onRandomMealClick() {
        binding.randomCardView.setOnClickListener {
            if (::randomMeal.isInitialized) {
                val intent = Intent(requireContext(), MealActivity::class.java).apply {
                    putExtra(MEAL_ID, randomMeal.idMeal)
                    putExtra(MEAL_NAME, randomMeal.strMeal)
                    putExtra(MEAL_THUMB, randomMeal.strMealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            meal?.let {
                Glide.with(this).load(it.strMealThumb).into(binding.imgRandomMeal)

                this.randomMeal = it
            }
        }
    }
}