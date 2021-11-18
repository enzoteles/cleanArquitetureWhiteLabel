package com.example.whitelabel.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.whitelabel.domain.usecase.GetProductsUseCase

class ProductsViewModel(
    val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel
}