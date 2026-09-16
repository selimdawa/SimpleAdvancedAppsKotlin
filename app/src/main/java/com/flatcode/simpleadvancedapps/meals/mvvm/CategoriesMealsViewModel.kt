package com.flatcode.simpleadvancedapps.meals.mvvm

import androidx.lifecycle.ViewModel
import com.flatcode.simpleadvancedapps.meals.pojo.MealsByCategory
import com.flatcode.simpleadvancedapps.meals.pojo.MealsByCategoryList
import com.flatcode.simpleadvancedapps.meals.retrofit.MealApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoriesMealsViewModel @Inject constructor(private val mealApi: MealApi) : ViewModel() {

    private val _meals = MutableStateFlow(emptyList<MealsByCategory>())
    val meals: StateFlow<List<MealsByCategory>> = _meals

    fun getMealsByCategory(categoryName: String) {
        mealApi.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>,
            ) {
                response.body()?.let { mealsList ->
                    _meals.value = mealsList.meals
                    Timber.d("State updated: meals = ${mealsList.meals}")
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Timber.tag("CategoryMealsViewModel").e(t.message.orEmpty())
            }
        })
    }
}