package com.bldsht.cookbook.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bldsht.cookbook.DataModels.Meal
import com.bldsht.cookbook.R
import com.bldsht.cookbook.ViewModel.MealViewModel
import com.bldsht.cookbook.databinding.ActivityMainBinding
import com.bldsht.cookbook.databinding.ActivityMealBinding
import com.bldsht.cookbook.ui.home.HomeFragment
import com.bumptech.glide.Glide

class MealActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMealBinding
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var youtubeLink : String
    private lateinit var mealMVVM : MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealMVVM = ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealInformationFromIntent()

        setInformationInViews()

        loadingCase()
        mealMVVM.getMealDetails(mealId)
        observeMealDetailsLiveData()

        onClickYoutube()
    }

    private fun onClickYoutube() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                // Handle the case where no activity can handle the intent
                Log.e("MealActivity", "No Activity found to handle the intent")
            }
        }
    }

    private fun observeMealDetailsLiveData() {
       mealMVVM.observeMealDetailsLiveData().observe(this
       ) { value ->
           onResponseCase()
           val meal = value
           binding.tvCategoryInfo.text = "Category : ${meal.strCategory}"
           binding.tvAreaInfo.text = "Area : ${meal.strArea}"
           binding.tvInstructions.text = meal.strInstructions

           youtubeLink = meal.strYoutube
       }
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!


    }
    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }
    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}