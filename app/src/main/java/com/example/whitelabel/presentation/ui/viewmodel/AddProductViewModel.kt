package com.example.whitelabel.presentation.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whitelabel.R
import com.example.whitelabel.domain.usecase.CreateProductUseCase
import com.example.whitelabel.presentation.utils.fromCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val createProductUseCase: CreateProductUseCase
) : ViewModel() {

    private val _imageUriErrorResId = MutableLiveData<Int>()
    val imageUriErrorResId: LiveData<Int> = _imageUriErrorResId

    private val _descriptionFieldErrorResId = MutableLiveData<Int?>()
    val descriptionFieldErrorResId: LiveData<Int?> = _descriptionFieldErrorResId

    private val _priceFieldErrorResId = MutableLiveData<Int>()
    val priceFieldErrorResId:LiveData<Int> = _priceFieldErrorResId

    private var isFormaValid = true


    fun createProduct(description: String, price: String, imageUri: Uri?) = viewModelScope.launch {
        isFormaValid = true

        _imageUriErrorResId.value = getDrawableResIdIfNull(imageUri)
        _descriptionFieldErrorResId.value = getErrorStringResIdIfEmpty(description)
        _priceFieldErrorResId.value = getErrorStringResIdIfEmpty(price)


        if(isFormaValid){
            try {
                val product = createProductUseCase(description, price.fromCurrency(), imageUri!!)
            }catch (e: Exception){
                Log.d("CreateProduct", e.toString())
            }
        }
    }

    private fun getErrorStringResIdIfEmpty(value: String): Int?{
        return if(value.isEmpty()){
            isFormaValid = false
            R.string.add_product_field_error
        }else null
    }

    private fun getDrawableResIdIfNull(value: Uri?): Int{
        return if(value == null){
            isFormaValid = false
            R.drawable.background_product_image_error
        } else R.drawable.background_product_image
    }
}