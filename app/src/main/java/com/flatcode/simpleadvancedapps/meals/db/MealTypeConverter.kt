package com.flatcode.simpleadvancedapps.meals.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.flatcode.simpleadvancedapps.Unit.DATA

@TypeConverters
class MealTypeConverter {

    @TypeConverter
    fun fromAnyToString(attribute: Any?): String {
        if (attribute == null) return DATA.EMPTY
        return attribute as String
    }

    @TypeConverter
    fun fromStringToAny(attribute: String?): Any {
        if (attribute == null) return DATA.EMPTY
        return attribute
    }
}