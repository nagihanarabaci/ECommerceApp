package com.ao.e_commerce.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ao.e_commerce.databinding.FragmentProductsDetailBinding
import com.ao.e_commerce.databinding.ProductDetailImageViewerBinding
import com.bumptech.glide.Glide

class ProductDetailImageAdapter(private var list:List<String>): RecyclerView.Adapter<ProductDetailImageAdapter.ProductDetailImageViewHolder>() {

    inner class ProductDetailImageViewHolder(var design: ProductDetailImageViewerBinding) :
        RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDetailImageViewHolder {
        val binding = ProductDetailImageViewerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductDetailImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductDetailImageViewHolder, position: Int) {
        val design = holder.design
        val item = list[position]
        Glide.with(design.root.context).load(item).into(design.productDetailImageViewer)
    }
}