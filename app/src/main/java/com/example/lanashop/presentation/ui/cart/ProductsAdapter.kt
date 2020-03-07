package com.example.lanashop.presentation.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.lanashop.R
import com.example.lanashop.domain.model.Product
import kotlin.properties.Delegates
import com.example.lanashop.databinding.ProductItemBinding


class ProductsAdapter (val adapterOnClick : (Product, Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList: List<Product> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val productItemBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.product_item, parent, false
        )
        return ProductsViewHolder(productItemBinding)
    }


    override fun getItemCount(): Int {
        return if (mList.isNullOrEmpty()) 0 else mList.size
    }

    private fun getItem(position: Int): Product {
        return mList[position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductsViewHolder).onBind(getItem(position),adapterOnClick)
    }

    inner class ProductsViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun onBind(
            product: Product,
            adapterOnClick: (Product, Int) -> Unit
        ) {

            (viewDataBinding as ProductItemBinding).product = product
            viewDataBinding.mbCount.text = product.count.toString()

            if (product.count==0){
                viewDataBinding.ivRemoveItem.alpha=0.5F
                viewDataBinding.ivRemoveItem.isEnabled= false
            }else{
                viewDataBinding.ivRemoveItem.alpha=1F
                viewDataBinding.ivRemoveItem.isEnabled= true
            }
            viewDataBinding.mbCount

            viewDataBinding.ivRemoveItem.setOnClickListener{
                adapterOnClick(product, 0)

            }
            viewDataBinding.ivAddItem.setOnClickListener{
                adapterOnClick(product, 1)
            }

        }
    }
}