package com.bldsht.cookbook.ui.Categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bldsht.cookbook.activities.HomeActivity
import com.bldsht.cookbook.adapters.CategoriesAdapter
import com.bldsht.cookbook.adapters.CategoryMealsAdapter
import com.bldsht.cookbook.databinding.FragmentCategoryBinding
import com.bldsht.cookbook.viewmodel.HomeViewModel

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel : HomeViewModel


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as HomeActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategories()
    }

    private fun observeCategories() {
       viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner,Observer{categories->
           categoriesAdapter.setCategoryList(categories)
       })
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.favoriteRecyclerView.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}