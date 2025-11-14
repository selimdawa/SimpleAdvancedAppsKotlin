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

    val randomMealLiveData = MutableLiveData<Meal>()
    val popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    val categoriesLiveData = MutableLiveData<List<Category>>()
    var favoritesMealsLiveData = mealDatabase.mealDao().getAllMeal()

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Timber.d(t.message.toString())
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
                    if (response.body() != null) {
                        val popularItem = response.body()!!.meals
                        popularItemsLiveData.value = popularItem
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Timber.d(t.message.toString())
                }
            })
    }

    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    categoriesLiveData.value = categoryList.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Timber.e(t.message.toString())
            }
        })
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observerPopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observeFavoritesMealsLiveData(): LiveData<List<Meal>> {
        return favoritesMealsLiveData
    }
}