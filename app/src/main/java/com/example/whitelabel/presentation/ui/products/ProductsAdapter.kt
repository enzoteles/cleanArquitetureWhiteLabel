package com.example.whitelabel.presentation.ui.products

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whitelabel.databinding.ItemProductBinding
import com.example.whitelabel.domain.model.Product

class ProductsAdapter: ListAdapter<Product, ProductsAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    class ProductViewHolder(
        private val itemBinding: ItemProductBinding
    ): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(product: Product){
            itemBinding.run {
                Glide.with(itemView)
                    .load(product.imageUrl)
                    .fitCenter()
                    .into(imageView)

                textView.text = product.description
            }
        }

        companion object{
            fun create (parent: ViewGroup): ProductViewHolder{
                val itemBinding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ProductViewHolder(itemBinding)
            }
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>(){
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position
        ))
    }


}