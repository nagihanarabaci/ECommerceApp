package com.ao.e_commerce.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.ao.e_commerce.R
import com.ao.e_commerce.databinding.FragmentProductsDetailBinding
import com.ao.e_commerce.models.Product
import com.ao.e_commerce.utils.SharedPreferencesHelper
import com.ao.e_commerce.utils.extensions.showToast
import com.ao.e_commerce.views.adapter.ProductDetailImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsDetailFragment : Fragment() {
    private var _binding: FragmentProductsDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var product: Product
    private var productCount: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsDetailBinding.inflate(layoutInflater, container, false)
        setFragmentView()
        return binding.root
    }


    private fun setFragmentView() {
        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        setProductData()
        setRateBarOptions()
        setProductImageSlider()
        favoritesOptions()
        countOptions()
        binding.productDetailAddToCart.setOnClickListener {
            addToBasket()
        }
    }

    private fun addToBasket(){
        with(binding){
            val count = textViewProductCount.text.toString()
            val sharedPreferences = SharedPreferencesHelper.getSharedPreferences(root.context)
            SharedPreferencesHelper.addToBasket(sharedPreferences, product.id.toString(), count)
            root.showToast("${product.title} added to basket")
        }
    }

    private fun setProductData() {
        val bundle: ProductsDetailFragmentArgs by navArgs()
        product = bundle.product

        /// Set title
        binding.textViewProductName.text = product.title

        /// Set description
        binding.textViewProductDescription.text = product.description

        /// Set price and stock
        "${product.price} $".also { binding.textViewProductPrice.text = it }
        "${product.stock} ".also { binding.textViewProductStock.text = it }

        /// Set stock value color
        binding.textViewProductStock.setTextColor(
            if (product.stock > 50) binding.root.context.getColor(
                R.color.primary
            ) else binding.root.context.getColor(R.color.error)
        )
    }

    private fun setRateBarOptions() {
        val rate = product.rating.toInt()
        val notRate = 5 - rate
        for (i in 1..rate) {
            val filledImageView = ImageView(binding.root.context)
            filledImageView.setImageResource(R.drawable.star_filled)
            binding.ratebar.addView(filledImageView)
        }
        for (i in 1..notRate) {
            val notFilledImageView = ImageView(binding.root.context)
            notFilledImageView.setImageResource(R.drawable.star_outline)
            binding.ratebar.addView(notFilledImageView)
        }
    }

    private fun setProductImageSlider() {
        binding.productDetailImageSlider.adapter = ProductDetailImageAdapter(product.images)
        for (i in 0 until product.images.size) {
            val dotView = R.layout.product_detail_indicator_dot
            val inflater = LayoutInflater.from(requireContext())
            val view = inflater.inflate(dotView, binding.root, false)
            binding.productDetailPageIndicator.addView(view)
        }
        binding.productDetailImageSlider.registerOnPageChangeCallback(object :
            androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                for (i in 0 until product.images.size) {
                    val view = binding.productDetailPageIndicator.getChildAt(i)
                    if (i == position) {
                        view.setBackgroundResource(R.drawable.circle_dot)
                    } else {
                        view.setBackgroundResource(R.drawable.circle_dot_inactive)
                    }
                }
            }
        })
    }

    private fun favoritesOptions() {
        val sharedPreferences = SharedPreferencesHelper.getSharedPreferences(binding.root.context)
        var getFavorites = SharedPreferencesHelper.getFavorites(sharedPreferences)
        fun isFavorite(): Boolean {
            return getFavorites != null && getFavorites!!.trim()
                .isNotEmpty() && getFavorites!!.contains(product.id.toString())
        }

        if (isFavorite()) {
            binding.buttonFavorite.setImageResource(R.drawable.im_favorite_fill)
        }

        fun setChanger(value: String, message: String, imRes: Int) {
            getFavorites?.let {
                SharedPreferencesHelper.putFavorites(sharedPreferences, value)
            }
            binding.root.showToast(message)
            binding.buttonFavorite.setImageResource(imRes)
        }

        binding.buttonFavorite.setOnClickListener {
            if (isFavorite()) {
                val newValue = getFavorites!!.replace(product.id.toString(), "")
                setChanger(
                    newValue, "Remove to favorites", R.drawable.im_favorite
                )
                getFavorites = newValue
            } else {
                setChanger(
                    getFavorites + "," + product.id.toString(),
                    "Add to favorites",
                    R.drawable.im_favorite_fill
                )
                getFavorites += "," + product.id.toString()
            }
        }
    }

    private fun countOptions() {
        binding.buttonIncrease.setOnClickListener {
            productCount++
            val totalPrice = product.price * productCount
            "$totalPrice $".also { binding.textViewProductPrice.text = it }
            binding.textViewProductCount.text = productCount.toString()

        }

        binding.buttonDecrease.setOnClickListener {
            if (productCount > 1) {
                productCount--
                val totalPrice = product.price * productCount
                "$totalPrice $".also { binding.textViewProductPrice.text = it }
                binding.textViewProductCount.text = productCount.toString()
                if (productCount == 0) {
                    binding.textViewProductCount.text = "1"
                    "${product.price} $".also { binding.textViewProductPrice.text = it }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}