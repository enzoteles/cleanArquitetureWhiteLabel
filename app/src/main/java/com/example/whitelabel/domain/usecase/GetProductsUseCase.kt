package com.example.whitelabel.domain.usecase

import com.example.whitelabel.domain.model.Product

interface GetProductsUseCase {

    suspend operator fun invoke(): List<Product>
}