package com.flatcode.simpleadvancedapps.meals.mvvm

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
class HomeViewModel @Inject constructor(
    private val mealApi: MealApi, private val mealDao: MealDao
) : ViewModel() {

    val randomMeal: StateFlow<Meal?>
        field = MutableStateFlow(null)

    val popularItems: StateFlow<List<MealsByCategory>>
        field = MutableStateFlow(emptyList())

    val categories: StateFlow<List<Category>>
        field = MutableStateFlow(emptyList())

    val favoritesMeals: StateFlow<List<Meal>> = mealDao.getAllMeals()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getRandomMeal() {
        mealApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.meals?.firstOrNull()?.let { meal ->
                    (randomMeal as MutableStateFlow).value = meal
                    Timber.d("State updated: randomMeal = $meal")
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
                    (popularItems as MutableStateFlow).value = popularItem
                    Timber.d("State updated: popularItems = $popularItem")
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
                    (categories as MutableStateFlow).value = categoryList.categories
                    Timber.d("State updated: categories = ${categoryList.categories}")
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
}