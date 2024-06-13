package com.bldsht.cookbook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bldsht.cookbook.datamodels.Meal
import com.bldsht.cookbook.datamodels.MealsByCategory

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Update
    suspend fun updateMeal(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query ("SELECT * FROM mealInformation")
    fun getAllMeals(): LiveData<List<Meal>>





}