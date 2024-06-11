package com.bldsht.cookbook.Activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bldsht.cookbook.Adapters.CategoryMealsAdapter
import com.bldsht.cookbook.R
import com.bldsht.cookbook.ViewModel.CategoryMealViewModel
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