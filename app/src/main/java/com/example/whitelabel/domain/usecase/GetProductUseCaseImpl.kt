package com.example.whitelabel.domain.usecase

import com.example.whitelabel.data.ProductRepository
import com.example.whitelabel.domain.model.Product
import javax.inject.Inject

class GetProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
): GetProductsUseCase {
    override suspend fun invoke(): List<Product> {
        return productRepository.getProducts()
    }

}