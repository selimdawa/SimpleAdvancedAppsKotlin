package com.flatcode.simpleadvancedapps.meals.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ActivityCategoryMealsBinding
import com.flatcode.simpleadvancedapps.meals.adapters.CategoryMealsAdapter
import com.flatcode.simpleadvancedapps.meals.fragments.HomeFragment
import com.flatcode.simpleadvancedapps.meals.mvvm.CategoriesMealsViewModel
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryMealsActivity : AppCompatActivity() {

    private var _binding: ActivityCategoryMealsBinding? = null
    private val binding get() = _binding!!

    private val categoryMealsViewModel: CategoriesMealsViewModel by viewModels()
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.nameSpace.text = DATA.CATEGORY_MEALS

        prepareRecyclerView()
        onPopularItemClick()

        categoryMealsViewModel.getMealsByCategory(
            intent.getStringExtra(HomeFragment.CATEGORY_NAME).orEmpty()
        )
        categoryMealsViewModel.observeCategoriesMealsLiveData().observe(this) { mealList ->
            binding.toolbar.nameSpace.text = getString(
                R.string.category_meals_count, DATA.CATEGORY_MEALS, mealList.size
            )
            categoryMealsAdapter.submitList(mealList)
        }
    }

    private fun onPopularItemClick() {
        categoryMealsAdapter.onItemClick = { meal ->
            val intent = Intent(applicationContext, MealActivity::class.java).apply {
                putExtra(HomeFragment.MEAL_ID, meal.idMeal)
                putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
                putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            }
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.adapter = categoryMealsAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}