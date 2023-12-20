package com.ao.e_commerce.views.adapter

import android.icu.lang.UCharacter
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ao.e_commerce.R
import com.ao.e_commerce.databinding.FragmentHomeBinding
import com.ao.e_commerce.databinding.HomeCategoryChipBinding
import com.ao.e_commerce.models.CategoriesCardModel
import com.ao.e_commerce.viewmodels.CategoriesViewModel
import com.ao.e_commerce.views.fragments.BaseFragmentDirections
import com.ao.e_commerce.views.fragments.CategoriesFragment
import com.google.common.base.Ascii.toUpperCase

class HomeCategoryCardAdapter(var binding: FragmentHomeBinding,
                              private var allCategoryList:List<CategoriesCardModel>,
                              private var viewModel: CategoriesViewModel)
    : RecyclerView.Adapter<HomeCategoryCardAdapter.HomeCategoryCardViewHolder>() {

    inner class HomeCategoryCardViewHolder(var design: HomeCategoryChipBinding) :
        RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryCardViewHolder {
        return HomeCategoryCardViewHolder(
            HomeCategoryChipBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeCategoryCardViewHolder, position: Int) {
        val item = allCategoryList[position]
        val design = holder.design

        with(design) {
            if (item.isSelect) {
                categoryText.setTextColor(binding.root.context.getColor(android.R.color.white))
                homeCategoryCardView.setCardBackgroundColor(binding.root.context.getColor(R.color.text_title_color))
            }
            categoryText.text = item.name.split("-").joinToString(" ") { toUpperCase(it) }
            homeCategoryCardView.setOnClickListener {
                changeCategoryList(position)
                viewModel.getProductsByCategory(item.name)
                Log.d("HomeCategoryCardAdapter", "onBindViewHolder: ${item.name}")
                val direction = BaseFragmentDirections.baseToCategories(item)
                Navigation.findNavController(it).navigate(direction)
            }
        }
    }


    override fun getItemCount(): Int {
        return allCategoryList.size
    }


    private fun changeCategoryList(position: Int) {
        val beforeActiveIndex = allCategoryList.indexOfFirst { it.isSelect }
        allCategoryList.forEach { it.isSelect = false }
        allCategoryList[position].isSelect = true
        notifyItemChanged(beforeActiveIndex)
        notifyItemChanged(position)
    }
}