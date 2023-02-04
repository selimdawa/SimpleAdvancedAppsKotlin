package com.flatcode.simpleadvancedapps.meals.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flatcode.simpleadvancedapps.meals.pojo.MealsByCategory
import com.flatcode.simpleadvancedapps.meals.pojo.MealsByCategoryList
import com.flatcode.simpleadvancedapps.meals.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CategoriesMealsViewModel : ViewModel() {

    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.api.getMealsByCategory(categoryName)
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>,
                ) {
                    response.body()?.let { mealsList -> mealsLiveData.value = mealsList.meals }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Timber.tag("CategoryMealsViewModel").e(t.message.toString())
                }
            })
    }

    fun observeCategoriesMealsLiveData(): LiveData<List<MealsByCategory>> {
        return mealsLiveData
    }
}