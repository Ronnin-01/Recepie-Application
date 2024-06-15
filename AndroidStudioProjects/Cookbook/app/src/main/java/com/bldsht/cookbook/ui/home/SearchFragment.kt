package com.bldsht.cookbook.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bldsht.cookbook.R
import com.bldsht.cookbook.activities.HomeActivity
import com.bldsht.cookbook.adapters.MealsAdapter
import com.bldsht.cookbook.databinding.FragmentMealBottomSheetBinding
import com.bldsht.cookbook.databinding.FragmentSearchBinding
import com.bldsht.cookbook.datamodels.Meal
import com.bldsht.cookbook.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var searchRecyclerViewAdapter : MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as HomeActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
    return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.icSearch.setOnClickListener {
            searchMeals()
        }
        observeSearchMealLiveData()

        var searchJob : Job? = null
        binding.edSearch.addTextChangedListener{ searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(searchQuery.toString())
            }
        }
    }

    private fun observeSearchMealLiveData() {
        viewModel.observeSearchMealLiveData().observe(viewLifecycleOwner, Observer {
            mealList ->
            searchRecyclerViewAdapter.differ.submitList(mealList)
        })
    }

    private fun searchMeals() {
        val searchQuery =  binding.edSearch.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdapter = MealsAdapter()
        binding.rvSearchMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = searchRecyclerViewAdapter
        }
    }
}