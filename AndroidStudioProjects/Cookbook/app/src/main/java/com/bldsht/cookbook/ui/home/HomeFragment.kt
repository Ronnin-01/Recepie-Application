package com.bldsht.cookbook.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bldsht.cookbook.Activities.MealActivity
import com.bldsht.cookbook.DataModels.Meal
import com.bldsht.cookbook.DataModels.MealList
import com.bldsht.cookbook.Retrofit.RetrofitInstance
import com.bldsht.cookbook.ViewModel.HomeViewModel
import com.bldsht.cookbook.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    companion object{
        const val MEAL_ID = "com.bldsht.cookbook.ui.home.idMeal"
        const val MEAL_NAME = "com.bldsht.cookbook.ui.home.nameMeal"
        const val MEAL_THUMB = "com.bldsht.cookbook.ui.home.thumbMeal"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeMVVM:HomeViewModel
    private lateinit var randomMeal:Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMVVM = ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMVVM.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()
    }

    private fun onRandomMealClick() {
       binding.randomMeal.setOnClickListener {
           val intent = Intent(activity,MealActivity::class.java)
           intent.putExtra(MEAL_ID,randomMeal.idMeal)
           intent.putExtra(MEAL_NAME,randomMeal.strMeal)
           intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
           startActivity(intent)
       }
    }

    private fun observeRandomMeal() {
        homeMVVM.observeRandomMealLivedata().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}