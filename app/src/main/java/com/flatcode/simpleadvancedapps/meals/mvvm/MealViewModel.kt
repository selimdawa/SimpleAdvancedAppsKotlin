package com.flatcode.simpleadvancedapps.meals.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.meals.db.MealDao
import com.flatcode.simpleadvancedapps.meals.pojo.Meal
import com.flatcode.simpleadvancedapps.meals.pojo.MealList
import com.flatcode.simpleadvancedapps.meals.retrofit.MealApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealApi: MealApi, private val mealDao: MealDao
) : ViewModel() {

    val mealDetails: StateFlow<Meal?>
        field = MutableStateFlow(null)

    fun getMealDetail(id: String) {
        mealApi.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.meals?.firstOrNull()?.let { meal ->
                    (mealDetails as MutableStateFlow).value = meal
                    Timber.d("State updated: mealDetails = $meal")
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Timber.tag("MealActivity").d(t.message.orEmpty())
            }
        })
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) { mealDao.upsert(meal) }
    }

    val favoritesMeals: StateFlow<List<Meal>> = mealDao.getAllMeals()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) { mealDao.delete(meal) }
    }
}