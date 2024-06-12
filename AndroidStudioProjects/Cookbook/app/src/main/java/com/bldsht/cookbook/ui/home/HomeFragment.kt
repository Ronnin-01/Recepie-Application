package com.bldsht.cookbook.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bldsht.cookbook.activities.CategoryMealActivity
import com.bldsht.cookbook.activities.HomeActivity
import com.bldsht.cookbook.activities.MainActivity
import com.bldsht.cookbook.activities.MealActivity
import com.bldsht.cookbook.adapters.CategoriesAdapter
import com.bldsht.cookbook.adapters.MostPopularAdapter
import com.bldsht.cookbook.datamodels.Meal
import com.bldsht.cookbook.viewmodel.HomeViewModel
import com.bldsht.cookbook.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    companion object{
        const val MEAL_ID = "com.bldsht.cookbook.ui.home.idMeal"
        const val MEAL_NAME = "com.bldsht.cookbook.ui.home.nameMeal"
        const val MEAL_THUMB = "com.bldsht.cookbook.ui.home.thumbMeal"
        const val CATEGORY_NAME = "com.bldsht.cookbook.ui.home.categoryName"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as HomeActivity).viewModel
        //viewModel = ViewModelProviders.of(this)[HomeViewModel::class.java]

        popularItemsAdapter = MostPopularAdapter()
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
        preparePopularItemsRecyclerView()

        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClicked()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observeCategoriesLiveData()

        onCategoryClick()


    }

    private fun onCategoryClick() {
       categoriesAdapter.onItemClick = { category ->
           val intent = Intent(activity,CategoryMealActivity::class.java)
           intent.putExtra(CATEGORY_NAME,category.strCategory)
           startActivity(intent)
       }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
               categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClicked() {
      popularItemsAdapter.onItemClick = {meal ->
          val intent = Intent(activity,MealActivity::class.java)
          intent.putExtra(MEAL_ID,meal.idMeal)
          intent.putExtra(MEAL_NAME,meal.strMeal)
          intent.putExtra(MEAL_THUMB,meal.strMealThumb)
          startActivity(intent)
      }
    }

    private fun preparePopularItemsRecyclerView() {
       binding.recViewMealsPopular.apply {
           layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
           adapter = popularItemsAdapter
       }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(mealList = mealList as ArrayList)

        }
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
        viewModel.observeRandomMealLivedata().observe(viewLifecycleOwner
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