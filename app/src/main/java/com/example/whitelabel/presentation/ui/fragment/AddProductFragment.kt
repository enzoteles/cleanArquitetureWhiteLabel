package com.example.whitelabel.presentation.ui.fragment

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.whitelabel.presentation.ui.viewmodel.AddProductViewModel
import com.example.whitelabel.R
import com.example.whitelabel.databinding.AddProductFragmentBinding
import com.example.whitelabel.databinding.ProductsFragmentBinding
import com.example.whitelabel.presentation.utils.CurrencyTextWatcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : BottomSheetDialogFragment() {

    private  var _binding: AddProductFragmentBinding?= null
    private  val binding: AddProductFragmentBinding get() = _binding!!
    private var imageUri:Uri ?= null

    private val viewModel: AddProductViewModel by viewModels()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        imageUri = uri
        binding.ivProduct.setImageURI(imageUri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVMEvents()
        setListener()
    }
    private fun observeVMEvents(){
        viewModel.imageUriErrorResId.observe(viewLifecycleOwner){ drawableResId->
            binding.ivProduct.setBackgroundResource(drawableResId)
        }
        viewModel.descriptionFieldErrorResId.observe(viewLifecycleOwner){ stringResId ->
            binding.inputLayoutDescription.error = stringResId.toString()

        }
        viewModel.priceFieldErrorResId.observe(viewLifecycleOwner){ stringResId ->
            binding.inputLayoutPrice.error = stringResId.toString()
        }
    }
    private fun setListener(){
        binding.ivProduct.setOnClickListener { chooseImage() }

        binding.button.setOnClickListener {
            val description = binding.inputDescription.toString()
            val price = binding.inputPrice.toString()
            viewModel.createProduct(description = description, price = price, imageUri = imageUri)
        }

        binding.inputPrice.run {
            addTextChangedListener(CurrencyTextWatcher(this))
        }
    }

    private fun chooseImage(){
        getContent.launch("image/*")
    }


}