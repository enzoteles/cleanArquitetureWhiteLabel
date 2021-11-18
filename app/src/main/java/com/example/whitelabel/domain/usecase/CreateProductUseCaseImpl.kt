package com.example.whitelabel.domain.usecase

import android.net.Uri
import com.example.whitelabel.data.ProductRepository
import com.example.whitelabel.domain.model.Product
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class CreateProductUseCaseImpl @Inject constructor(
    private val uploadProductImageUseCase: UploadProductImageUseCase,
    private val productRepository: ProductRepository
) : CreateProductUseCase{
    override suspend fun invoke(description: String, price: Double, imageUri: Uri): Product {
         return try {
            val imageUrl = uploadProductImageUseCase(imageUri)
             val product = Product(UUID.randomUUID().toString(), description = description, price = price, imageUrl = imageUrl)
             productRepository.createProduct(product)
        } catch (e: Exception){
            throw e
        }
    }
}