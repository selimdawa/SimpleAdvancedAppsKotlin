package com.flatcode.simpleadvancedapps.meals.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simpleadvancedapps.meals.db.MealDatabase
import com.flatcode.simpleadvancedapps.meals.pojo.Meal
import com.flatcode.simpleadvancedapps.meals.pojo.MealList
import com.flatcode.simpleadvancedapps.meals.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MealViewModel(val mealDatabase: MealDatabase) : ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id: String) {
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                } else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Timber.tag("MealActivity").d(t.message.toString())
            }
        })
    }

    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) { mealDatabase.mealDao().upsert(meal) }
    }
}