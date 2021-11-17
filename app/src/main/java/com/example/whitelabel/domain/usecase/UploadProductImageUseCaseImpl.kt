package com.example.whitelabel.domain.usecase

import android.net.Uri
import com.example.whitelabel.data.ProductRepository

class UploadProductImageUseCaseImpl(
    private val productRepository: ProductRepository
): UploadProductImageUseCase {

    override suspend fun invoke(imageUri: Uri): String {
        return productRepository.uploadProductImage(imageUri)
    }
}