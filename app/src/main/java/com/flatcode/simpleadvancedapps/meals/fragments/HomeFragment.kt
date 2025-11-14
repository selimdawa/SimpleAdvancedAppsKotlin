package com.flatcode.simpleadvancedapps.meals.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.flatcode.simpleadvancedapps.databinding.FragmentHomeMealsBinding
import com.flatcode.simpleadvancedapps.meals.activities.CategoryMealsActivity
import com.flatcode.simpleadvancedapps.meals.activities.MainActivity
import com.flatcode.simpleadvancedapps.meals.activities.MealActivity
import com.flatcode.simpleadvancedapps.meals.adapters.CategoriesAdapter
import com.flatcode.simpleadvancedapps.meals.adapters.MostPopularAdapter
import com.flatcode.simpleadvancedapps.meals.mvvm.HomeViewModel
import com.flatcode.simpleadvancedapps.meals.pojo.Meal
import com.flatcode.simpleadvancedapps.meals.pojo.MealsByCategory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeMealsBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.flatcode.simpleadvancedapps.i20.fragments.idMeal"
        const val MEAL_NAME = "com.flatcode.simpleadvancedapps.i20.fragments.nameMeal"
        const val MEAL_THUMB = "com.flatcode.simpleadvancedapps.i20.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.flatcode.simpleadvancedapps.i20.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeMealsBinding.inflate(inflater, container, false)
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

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply { adapter = categoriesAdapter }
    }

    private fun observerCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategoryList(categories)
        }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recPopular.apply {
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observerPopularItemsLiveData().observe(viewLifecycleOwner) { mealList ->
            popularItemsAdapter.setMeals(mealList = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomCardView.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }
}