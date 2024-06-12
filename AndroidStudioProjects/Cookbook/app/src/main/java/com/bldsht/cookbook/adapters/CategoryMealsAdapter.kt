package com.bldsht.cookbook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bldsht.cookbook.datamodels.MealsByCategory
import com.bldsht.cookbook.databinding.MealItemBinding
import com.bumptech.glide.Glide

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>(){
    private var mealsList = ArrayList<MealsByCategory>()

    fun setMealsList(mealsList: List<MealsByCategory>) {
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class CategoryMealsViewModel(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryMealsAdapter.CategoryMealsViewModel {
       return CategoryMealsViewModel(
           MealItemBinding.inflate(LayoutInflater.from(parent.context))
       )
    }

    override fun onBindViewHolder(
        holder: CategoryMealsAdapter.CategoryMealsViewModel,
        position: Int
    ) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealsList[position].strMeal
        }

    override fun getItemCount(): Int {
       return mealsList.size
    }
}