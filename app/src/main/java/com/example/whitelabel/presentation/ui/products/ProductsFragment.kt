package com.example.whitelabel.presentation.ui.products

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.whitelabel.presentation.ui.viewmodel.ProductsViewModel
import com.example.whitelabel.R
import com.example.whitelabel.databinding.ProductsFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {


    private  val viewModel: ProductsViewModel by viewModels()
    private  var _binding: ProductsFragmentBinding?= null
    private  val binding: ProductsFragmentBinding get() = _binding!!

    private val productsAdapter = ProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProductsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRycyclerView()
        observerVMEvents()

        viewModel.getProducts()
    }

    fun setRycyclerView(){
        binding.rvProducts.run {
            setHasFixedSize(true)
            adapter = productsAdapter
        }
    }

    fun observerVMEvents(){
        viewModel.productsData.observe(viewLifecycleOwner){ products->
            productsAdapter.submitList(products)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}