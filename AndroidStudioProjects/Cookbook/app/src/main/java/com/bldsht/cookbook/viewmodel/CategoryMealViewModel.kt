package com.bldsht.cookbook.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bldsht.cookbook.datamodels.MealsByCategory
import com.bldsht.cookbook.datamodels.MealsByCategoryList
import com.bldsht.cookbook.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel : ViewModel() {
    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()
    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.api.getMealsByCategories(categoryName).enqueue(object :
            Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let { mealsList ->
                    mealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("CategoryMealsViewModel", t.message.toString())
            }
        })
    }

    fun observeLiveData(): LiveData<List<MealsByCategory>>{
        return  mealsLiveData
    }

}