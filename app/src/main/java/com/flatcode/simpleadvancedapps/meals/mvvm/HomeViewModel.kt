package com.flatcode.simpleadvancedapps.meals.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.meals.db.MealDatabase
import com.flatcode.simpleadvancedapps.meals.pojo.*
import com.flatcode.simpleadvancedapps.meals.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class HomeViewModel(private val mealDatabase: MealDatabase) : ViewModel() {

    private val _randomMealLiveData = MutableLiveData<Meal>()
    val randomMealLiveData: LiveData<Meal> get() = _randomMealLiveData

    private val _popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    val popularItemsLiveData: LiveData<List<MealsByCategory>> get() = _popularItemsLiveData

    private val _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>> get() = _categoriesLiveData

    val favoritesMealsLiveData: LiveData<List<Meal>> = mealDatabase.mealDao().getAllMeals()

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
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
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
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
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
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
        viewModelScope.launch(Dispatchers.IO) {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun observeRandomMealLiveData(): LiveData<Meal> = randomMealLiveData

    fun observerPopularItemsLiveData(): LiveData<List<MealsByCategory>> = popularItemsLiveData

    fun observeCategoriesLiveData(): LiveData<List<Category>> = categoriesLiveData

    fun observeFavoritesMealsLiveData(): LiveData<List<Meal>> = favoritesMealsLiveData
}