package com.example.whitelabel.data

import android.net.Uri
import com.example.whitelabel.BuildConfig
import com.example.whitelabel.domain.model.Product
import com.example.whitelabel.presentation.utils.COLLECTION_PRODUCTS
import com.example.whitelabel.presentation.utils.COLLECTION_ROOT
import com.example.whitelabel.presentation.utils.STORAGE_IMAGES
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.coroutines.suspendCoroutine

class FirebaseProductDataSource(
    firebaseFirestore: FirebaseFirestore,
    firebaseStorage: FirebaseStorage
): ProductDataSource {

    //data/car/products/timestamp/productA
    //data/car/products/timestamp/productB

    private val documentReference = firebaseFirestore
        .document("$COLLECTION_ROOT/${BuildConfig.FIREBASE_FLAVOR_COLLECTION}/")

    private val storeReference = firebaseStorage.reference

    override suspend fun getProducts(): List<Product> {
        return suspendCoroutine { continuation->

            val productReference = documentReference.collection(COLLECTION_PRODUCTS)
            productReference.get().addOnSuccessListener { documents->
                val products = mutableListOf<Product>()
                for(document in documents){
                    document.toObject(Product::class.java).run {
                        products.add(this)
                    }
                }
                continuation.resumeWith(Result.success(products))
            }

            productReference.get().addOnFailureListener { exception->
                continuation.resumeWith(Result.failure(exception))
            }

        }

    }

    override suspend fun uploadProductImage(imageUri: Uri): String {
        //image/car/randomKeyID
        return suspendCoroutine { continuation->
            val randomKey = UUID.randomUUID()
            val childReference = storeReference.child(
                "$STORAGE_IMAGES/${BuildConfig.FIREBASE_FLAVOR_COLLECTION}/$randomKey"
            )
            childReference.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri->
                        val path = uri.toString()
                        continuation.resumeWith(Result.success(path))
                    }
                }.addOnFailureListener{exception->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }

    override suspend fun createProduct(product: Product): Product {
        return suspendCoroutine { continuation ->
            documentReference
                .collection(COLLECTION_PRODUCTS)
                .document(System.currentTimeMillis().toString())
                .set(product)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(product))
                }
                .addOnFailureListener { exception->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }
}