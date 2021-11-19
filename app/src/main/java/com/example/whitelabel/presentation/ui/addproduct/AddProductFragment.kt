package com.example.whitelabel.presentation.ui.addproduct

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.whitelabel.presentation.ui.viewmodel.AddProductViewModel
import com.example.whitelabel.databinding.AddProductFragmentBinding
import com.example.whitelabel.domain.model.Product
import com.example.whitelabel.presentation.utils.CurrencyTextWatcher
import com.example.whitelabel.presentation.utils.PRODUCT_KEY
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
        viewModel.productItem.observe(viewLifecycleOwner){ product->
            findNavController().run {
                previousBackStackEntry?.savedStateHandle?.set(PRODUCT_KEY, product)
                popBackStack()
            }
        }
    }
    private fun setListener(){
        binding.ivProduct.setOnClickListener { chooseImage() }

        binding.button.setOnClickListener {
            val description = binding.inputDescription.text.toString()
            val price = binding.inputPrice.text.toString()
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