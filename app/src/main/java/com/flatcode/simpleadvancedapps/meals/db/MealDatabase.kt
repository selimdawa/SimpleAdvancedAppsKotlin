package com.flatcode.simpleadvancedapps.meals.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flatcode.simpleadvancedapps.meals.pojo.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null

        fun getInstance(context: Context): MealDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "meal.db"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}