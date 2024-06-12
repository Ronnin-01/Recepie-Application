package com.bldsht.cookbook.database

import android.view.inputmethod.TextAttribute
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class TypeConverter {
@TypeConverter
    fun fromAnyToString(attribute: Any?):String{
        if(attribute == null)
            return ""
        return attribute as String
    }
@TypeConverter
    fun fromStringToAny(attribute: String?):Any{
        if (attribute == null)
            return ""
        return attribute
    }
}