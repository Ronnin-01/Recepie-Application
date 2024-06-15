package com.bldsht.cookbook.bottomsheet

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.bldsht.cookbook.R
import com.bldsht.cookbook.activities.HomeActivity
import com.bldsht.cookbook.activities.MealActivity
import com.bldsht.cookbook.databinding.FragmentMealBottomSheetBinding
import com.bldsht.cookbook.ui.home.HomeFragment
import com.bldsht.cookbook.viewmodel.HomeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "param1"
class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null

    private var _binding: FragmentMealBottomSheetBinding? = null
    private lateinit var viewModel: HomeViewModel
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }

        viewModel = (activity as HomeActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealBottomSheetBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)

        }
        observeBottomSheetMeal()
        onBottomSheetDialogClick()

    }

    private fun onBottomSheetDialogClick() {
       binding.bottomSheetRoot.setOnClickListener {
           if (mealName != null && mealThumb != null){
               val intent = Intent(activity,MealActivity::class.java)
               intent.apply {
                   putExtra(HomeFragment.MEAL_ID,mealId)
                   putExtra(HomeFragment.MEAL_NAME,mealName)
                   putExtra(HomeFragment.MEAL_THUMB,mealThumb)
               }
               startActivity(intent)
           }
       }
    }

    private var mealName: String? = null
    private var mealThumb: String? = null
    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMeal().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgCategory)
            binding.tvMealCountry.text = meal.strArea
            binding.tvMealCategory.text = meal.strCategory
            binding.tvMealNameInBtmsheet.text = meal.strMeal

            mealName = meal.strMeal
            mealThumb = meal.strMealThumb
        })
    }

    companion object {
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}