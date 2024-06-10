package com.bldsht.cookbook.Retrofit

import com.bldsht.cookbook.DataModels.Meal
import com.bldsht.cookbook.DataModels.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>
}