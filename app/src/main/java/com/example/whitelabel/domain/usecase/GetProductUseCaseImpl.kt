package com.example.whitelabel.domain.usecase

import com.example.whitelabel.data.ProductRepository
import com.example.whitelabel.domain.model.Product

class GetProductUseCaseImpl(
    private val productRepository: ProductRepository
): GetProductsUseCase {
    override suspend fun invoke(): List<Product> {
        return productRepository.getProducts()
    }

}