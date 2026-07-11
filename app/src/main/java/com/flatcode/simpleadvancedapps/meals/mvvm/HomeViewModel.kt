package com.flatcode.simpleadvancedapps.meals.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.meals.db.MealDao
import com.flatcode.simpleadvancedapps.meals.pojo.Category
import com.flatcode.simpleadvancedapps.meals.pojo.CategoryList
import com.flatcode.simpleadvancedapps.meals.pojo.Meal
import com.flatcode.simpleadvancedapps.meals.pojo.MealList
import com.flatcode.simpleadvancedapps.meals.pojo.MealsByCategory
import com.flatcode.simpleadvancedapps.meals.pojo.MealsByCategoryList
import com.flatcode.simpleadvancedapps.meals.retrofit.MealApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealApi: MealApi, private val mealDao: MealDao
) : ViewModel() {

    private val _randomMealLiveData = MutableLiveData<Meal>()
    val randomMealLiveData: LiveData<Meal> get() = _randomMealLiveData

    private val _popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    val popularItemsLiveData: LiveData<List<MealsByCategory>> get() = _popularItemsLiveData

    private val _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>> get() = _categoriesLiveData

    val favoritesMealsLiveData: LiveData<List<Meal>> = mealDao.getAllMeals()

    fun getRandomMeal() {
        mealApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.meals?.firstOrNull()?.let { randomMeal ->
                    _randomMealLiveData.value = randomMeal
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Timber.d(t.message.orEmpty())
            }
        })
    }

    fun getPopularItems() {
        mealApi.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>,
            ) {
                response.body()?.meals?.let { popularItem ->
                    _popularItemsLiveData.value = popularItem
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Timber.d(t.message.orEmpty())
            }
        })
    }

    fun getCategories() {
        mealApi.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    _categoriesLiveData.value = categoryList.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Timber.e(t.message.orEmpty())
            }
        })
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) { mealDao.upsert(meal) }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) { mealDao.delete(meal) }
    }

    fun observeRandomMealLiveData(): LiveData<Meal> = randomMealLiveData
    fun observerPopularItemsLiveData(): LiveData<List<MealsByCategory>> = popularItemsLiveData
    fun observeCategoriesLiveData(): LiveData<List<Category>> = categoriesLiveData
    fun observeFavoritesMealsLiveData(): LiveData<List<Meal>> = favoritesMealsLiveData
}