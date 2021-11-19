package com.example.whitelabel.presentation.ui.products

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import com.example.whitelabel.presentation.ui.viewmodel.ProductsViewModel
import com.example.whitelabel.R
import com.example.whitelabel.databinding.ProductsFragmentBinding
import com.example.whitelabel.domain.model.Product
import com.example.whitelabel.presentation.utils.PRODUCT_KEY
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
        observerNavBackStack()
        observerVMEvents()

        viewModel.getProducts()
    }

    fun setRycyclerView(){
        binding.rvProducts.run {
            setHasFixedSize(true)
            adapter = productsAdapter
        }
        binding.fab.setOnClickListener {
            findNavController()
                .navigate(ProductsFragmentDirections.actionProductsFragmentToAddProductFragment())
        }
    }

    private fun observerNavBackStack(){
        findNavController().run {
            val navBackStackEntry = getBackStackEntry(R.id.productsFragment)
            val saveStateHandle = navBackStackEntry.savedStateHandle
            val observer = LifecycleEventObserver{_, event ->

                if(event == Lifecycle.Event.ON_RESUME && saveStateHandle.contains(PRODUCT_KEY)){
                    val product = saveStateHandle.get<Product>(PRODUCT_KEY)
                    val oldList = productsAdapter.currentList
                    val newList = oldList.toMutableList().apply {
                        add(product)
                    }
                    productsAdapter.submitList(newList)
                    binding.rvProducts.smoothScrollToPosition(newList.size -1)
                    saveStateHandle.remove<Product>(PRODUCT_KEY )
                }
            }

            navBackStackEntry.lifecycle.addObserver(observer)
            viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver{_, event->
                if(event == Lifecycle.Event.ON_DESTROY){
                    navBackStackEntry.lifecycle.removeObserver(observer)
                }
            })

        }
    }

    fun observerVMEvents(){
        viewModel.productsData.observe(viewLifecycleOwner){ products->
            productsAdapter.submitList(products)
        }

        viewModel.addButtonVisibilityData.observe(viewLifecycleOwner){ visibility->
            binding.fab.visibility = visibility
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}