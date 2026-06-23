package com.flatcode.simpleadvancedapps.meals.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.Unit.THEME
import com.flatcode.simpleadvancedapps.databinding.ActivityCategoryMealsBinding
import com.flatcode.simpleadvancedapps.meals.adapters.CategoryMealsAdapter
import com.flatcode.simpleadvancedapps.meals.fragments.HomeFragment
import com.flatcode.simpleadvancedapps.meals.mvvm.CategoriesMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsViewModel: CategoriesMealsViewModel
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter
    private val context = this@CategoryMealsActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.nameSpace.text = DATA.Category_Meals

        prepareRecyclerView()
        onPopularItemClick()

        categoryMealsViewModel = ViewModelProvider(this)[CategoriesMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealsViewModel.observeCategoriesMealsLiveData().observe(this) { mealList ->
            binding.toolbar.nameSpace.text = getString(
                R.string.category_meals_count,
                DATA.Category_Meals,
                mealList.size
            )
            categoryMealsAdapter.submitList(mealList)
        }
    }

    private fun onPopularItemClick() {
        categoryMealsAdapter.onItemClick = { meal ->
            val intent = Intent(applicationContext, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            adapter = categoryMealsAdapter
        }
    }
}