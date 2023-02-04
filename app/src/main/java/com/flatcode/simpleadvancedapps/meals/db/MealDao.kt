package com.flatcode.simpleadvancedapps.meals.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flatcode.simpleadvancedapps.meals.pojo.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(meal: Meal)

    @Delete
    fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeal(): LiveData<List<Meal>>
}