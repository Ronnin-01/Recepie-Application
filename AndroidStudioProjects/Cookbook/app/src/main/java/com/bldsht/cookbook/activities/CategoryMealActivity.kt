package com.bldsht.cookbook.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bldsht.cookbook.adapters.CategoryMealsAdapter
import com.bldsht.cookbook.viewmodel.CategoryMealViewModel
import com.bldsht.cookbook.databinding.ActivityCategoryMealBinding
import com.bldsht.cookbook.ui.home.HomeFragment

class CategoryMealActivity : AppCompatActivity() {
    lateinit var binding : ActivityCategoryMealBinding
    lateinit var categoryMealsViewModel: CategoryMealViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProviders.of(this)[CategoryMealViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observeLiveData().observe(this) { mealsList ->
            mealsList.forEach {
                binding.tvCategoryCount.text = mealsList.size.toString()
                categoryMealsAdapter.setMealsList(mealsList)
            }
        }
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}