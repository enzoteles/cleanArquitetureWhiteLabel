package com.example.whitelabel.presentation.ui.fragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.whitelabel.presentation.ui.viewmodel.ProductsViewModel
import com.example.whitelabel.R
import com.example.whitelabel.databinding.ProductsFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductsFragment : Fragment() {

    private lateinit var viewModel: ProductsViewModel
    private  var _binding: ProductsFragmentBinding?= null
    private  val binding: ProductsFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProductsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToAddProductFragment())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}